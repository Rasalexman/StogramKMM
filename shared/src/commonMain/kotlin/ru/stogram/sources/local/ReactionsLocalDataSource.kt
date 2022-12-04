package ru.stogram.sources.local

import com.rasalexman.sresult.data.dto.SResult
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.*
import ru.stogram.database.RealmDataBase
import ru.stogram.models.IUser
import ru.stogram.models.PostEntity
import ru.stogram.models.ReactionEntity
import ru.stogram.models.UserEntity
import ru.stogram.utils.getRandomReactionDateText
import ru.stogram.utils.getRandomString
import ru.stogram.utils.randomBool

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class ReactionsLocalDataSource(
    private val database: RealmDataBase
) : IReactionsLocalDataSource {

    private val updateState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override fun getAllReactionsAsFlow(): Flow<List<ReactionEntity>> {
        return updateState.flatMapLatest { database.getCurrentUserFlow() }
            .flatMapLatest { currentUser ->
                database.realm.query<ReactionEntity>("post.user.id = $0", currentUser.id).asFlow()
                    .map { result ->
                        val reactionsList: List<ReactionEntity> = result.list.toList()
                        reactionsList
                    }
            }
    }

    override fun createReaction(
        reactionType: String,
        postId: String,
        postComment: String
    ): SResult<Boolean> {
        val query = "type = $0 AND post.postId = $1"
        val alreadyAddedReaction =
            database.realm.query<ReactionEntity>(query, postId, reactionType).first().find()
        return if (alreadyAddedReaction == null) {
            val currentUser = database.getCurrentUser()
            val postEntity: PostEntity? =
                database.realm.query<PostEntity>("postId = $0", postId).first().find()
            postEntity?.let {
                val userReaction = ReactionEntity().apply {
                    id = getRandomString(100)
                    type = reactionType
                    comment = postComment
                    date = getRandomReactionDateText()
                }
                saveReaction(
                    reaction = userReaction,
                    user = currentUser,
                    post = postEntity
                )?.run {
                    SResult.Success(true)
                }
            } ?: SResult.AbstractFailure.Error("Database Reaction Error")
        } else {
            SResult.Success(true)
        }
    }

    override fun update() {
        val lastValue = !updateState.value
        updateState.value = lastValue
    }

    private fun createLocalData(currentUser: IUser): Flow<List<ReactionEntity>> {
        return database.realm.query<PostEntity>("user.id = $0", currentUser.id).asFlow().map {
            val allPosts: List<PostEntity> = it.list.shuffled()
            println("-----> create real collections allPosts count: ${allPosts.size}")
            allPosts.mapNotNull { localPost ->
                saveReaction(
                    reaction = ReactionEntity.createRandom(),
                    user = UserEntity.createRandomDetailed(randomBool),
                    post = localPost
                )
            }
        }
    }

    private fun saveReaction(
        reaction: ReactionEntity,
        user: UserEntity,
        post: PostEntity
    ): ReactionEntity? {
        return database.realm.writeBlocking {
            val randomReaction = copyToRealm(reaction)
            findLatest(randomReaction)?.apply {
                this.post = findLatest(post)
                this.from = findLatest(user) ?: copyToRealm(user)
            }
        }
    }
}

interface IReactionsLocalDataSource {
    fun getAllReactionsAsFlow(): Flow<List<ReactionEntity>>
    fun createReaction(
        reactionType: String,
        postId: String,
        postComment: String = ""
    ): SResult<Boolean>

    fun update()
}