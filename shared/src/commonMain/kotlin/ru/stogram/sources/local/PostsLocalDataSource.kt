package ru.stogram.sources.local

import io.realm.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.stogram.database.CFlow
import ru.stogram.database.RealmDataBase
import ru.stogram.database.wrap
import ru.stogram.models.PostEntity
import ru.stogram.models.UserEntity

class PostsLocalDataSource(
    private val database: RealmDataBase
) : IPostsLocalDataSource {

    override fun getAllPostsAsFlow(): Flow<List<PostEntity>> {
        return database.realm.query<PostEntity>().asFlow().map { result ->
            result.list.ifEmpty {
                createLocalData()
            }
        }
    }

    override fun findUserPostsFlow(user: UserEntity): Flow<List<PostEntity>> {
        return database.realm.query<PostEntity>("user.id = $0", user.id).asFlow().map { result ->
            result.list
        }
    }

    private fun createLocalData(): List<PostEntity> {
        val createdData = PostEntity.createRandomList()
        createdData.forEach { entity ->
            database.realm.writeBlocking {
                copyToRealm(entity)
            }
        }
        return createdData
    }
}

interface IPostsLocalDataSource {
    fun getAllPostsAsFlow(): Flow<List<PostEntity>>
    fun findUserPostsFlow(user: UserEntity): Flow<List<PostEntity>>
}