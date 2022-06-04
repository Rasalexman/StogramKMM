package ru.stogram.sources.local

import io.realm.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.stogram.database.CFlow
import ru.stogram.database.RealmDataBase
import ru.stogram.database.wrap
import ru.stogram.models.UserEntity

class UserStoriesLocalDataSource(
    private val database: RealmDataBase
) : IUserStoriesLocalDataSource {

    override fun getAllStoriesAsFlow(): Flow<List<UserEntity>> {
        return database.realm.query<UserEntity>().asFlow().map { result ->
            result.list.ifEmpty {
                createLocalData()
            }
        }
    }

    private fun createLocalData(): List<UserEntity> {
        val createdData = UserEntity.createRandomList()
        createdData.forEach { entity ->
            database.realm.writeBlocking {
                copyToRealm(entity)
            }
        }
        return createdData
    }
}

interface IUserStoriesLocalDataSource {
    fun getAllStoriesAsFlow(): Flow<List<UserEntity>>
}