package ru.stogram.sources.local

import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.*
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
        return flow {
            emit(database.getCurrentUser())
        }.flatMapLatest { currentUser ->
            database.realm.query<PostEntity>("user.id = $0", currentUser.id).asFlow().map {
                val allPosts: List<PostEntity> = it.list.shuffled()
                println("-----> create real collections allPosts count: ${allPosts.size}")
                allPosts.mapNotNull { localPost ->
                    database.realm.writeBlocking {
                        val randomReaction = copyToRealm(ReactionEntity.createRandom())
                        val randomUser = copyToRealm(UserEntity.createRandomDetailed(randomBool))
                        findLatest(randomReaction)?.apply {
                            this.post = findLatest(localPost)
                            this.from = findLatest(randomUser)
                        }
                    }
                }
            }
        }

    }
}

interface IReactionsLocalDataSource {
    fun getAllReactionsAsFlow(): Flow<List<ReactionEntity>>
}