package ru.stogram.sources.local

import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import ru.stogram.database.RealmDataBase
import ru.stogram.models.PostEntity
import ru.stogram.models.ReactionEntity
import ru.stogram.models.UserEntity
import ru.stogram.utils.randomBool
import kotlin.random.Random

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class ReactionsLocalDataSource(
    private val database: RealmDataBase
) : IReactionsLocalDataSource {

    override fun getAllReactionsAsFlow(): Flow<List<ReactionEntity>> {
        return database.realm.query<ReactionEntity>().asFlow().flatMapLatest { result ->
            val random = Random.nextInt(10, 99999)
            val data = result.list.toList()
            if(data.isEmpty() || random % 3 == 0) {
                createLocalData()
            } else {
                flowOf(data)
            }
        }
    }

    private fun createLocalData(): Flow<List<ReactionEntity>> {
        val currentUser = database.getCurrentUser()
        return database.realm.query<PostEntity>("user.id = $0", currentUser.id).asFlow().map {
            val allPosts = it.list.shuffled()
            println("-----> create real collections allPosts count: ${allPosts.size}")
            allPosts.mapNotNull { localPost ->
                val randomReaction = database.realm.writeBlocking {
                    copyToRealm(ReactionEntity.createRandom())
                }
                val randomUser = database.realm.writeBlocking {
                    copyToRealm(UserEntity.createRandomDetailed(randomBool))
                }
                database.realm.writeBlocking {
                    findLatest(randomReaction)?.apply {
                        this.post = findLatest(localPost)
                        this.from = findLatest(randomUser)
                    }
                }
            }
        }
    }
}

interface IReactionsLocalDataSource {
    fun getAllReactionsAsFlow(): Flow<List<ReactionEntity>>
}