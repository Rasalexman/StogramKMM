package ru.stogram.sources.local

import io.realm.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.stogram.database.RealmDataBase
import ru.stogram.models.ReactionEntity

class ReactionsLocalDataSource(
    private val database: RealmDataBase
) : IReactionsLocalDataSource {

    override fun getAllReactionsAsFlow(): Flow<List<ReactionEntity>> {
        return database.realm.query<ReactionEntity>().asFlow().map { result ->
            result.list.ifEmpty {
                createLocalData()
            }
        }
    }

    private fun createLocalData(): List<ReactionEntity> {
        val createdData = ReactionEntity.createRandomList()
        createdData.forEach { entity ->
            database.realm.writeBlocking {
                copyToRealm(entity)
            }
        }
        return createdData
    }

}

interface IReactionsLocalDataSource {
    fun getAllReactionsAsFlow(): Flow<List<ReactionEntity>>
}