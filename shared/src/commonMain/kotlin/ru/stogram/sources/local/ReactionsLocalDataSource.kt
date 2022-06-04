package ru.stogram.sources.local

import io.realm.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import ru.stogram.database.RealmDataBase
import ru.stogram.models.PostEntity
import ru.stogram.models.ReactionEntity
import ru.stogram.utils.randomCount

class ReactionsLocalDataSource(
    private val database: RealmDataBase
) : IReactionsLocalDataSource {

    override fun getAllReactionsAsFlow(): Flow<List<ReactionEntity>> {
        return database.realm.query<ReactionEntity>().asFlow().flatMapLatest { result ->
            val data = result.list
            if(data.isEmpty() || data.size % 3 == 0) {
                createLocalData()
            } else {
                flowOf(data)
            }
        }
    }

    private fun createLocalData(): Flow<List<ReactionEntity>> {
        val currentUser = database.getCurrentUser()
        return database.realm.query<PostEntity>("user.id = $0", currentUser.id).asFlow().map {
            val allPosts = it.list
            allPosts.mapNotNull { post ->
                val randomReaction = ReactionEntity.createRandom()
                database.realm.writeBlocking {
                    copyToRealm(randomReaction)
                    findLatest(randomReaction)?.post = findLatest(post)
                    findLatest(randomReaction)
                }
            }
        }
    }
}

interface IReactionsLocalDataSource {
    fun getAllReactionsAsFlow(): Flow<List<ReactionEntity>>
}