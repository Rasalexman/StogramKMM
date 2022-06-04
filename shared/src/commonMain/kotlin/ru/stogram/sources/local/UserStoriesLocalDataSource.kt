package ru.stogram.sources.local

import io.realm.Realm
import io.realm.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.stogram.database.CFlow
import ru.stogram.database.wrap
import ru.stogram.models.UserEntity

class UserStoriesLocalDataSource(
    private val realm: Realm
) : IUserStoriesLocalDataSource {

    override fun getAllStoriesAsFlow(): Flow<List<UserEntity>> {
        return realm.query<UserEntity>().asFlow().map { result ->
            result.list.ifEmpty {
                createLocalData()
            }
        }
    }

    override fun getStoriesAsCommonFlow(): CFlow<List<UserEntity>> {
        return getAllStoriesAsFlow().wrap()
    }

    private fun createLocalData(): List<UserEntity> {
        val createdData = UserEntity.createRandomList()
        createdData.forEach { entity ->
            realm.writeBlocking {
                copyToRealm(entity)
            }
        }
        return createdData
    }
}

interface IUserStoriesLocalDataSource {
    fun getAllStoriesAsFlow(): Flow<List<UserEntity>>
    fun getStoriesAsCommonFlow(): CFlow<List<UserEntity>>
}