package ru.stogram.sources.local

import io.realm.Realm
import io.realm.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.stogram.database.CFlow
import ru.stogram.database.wrap
import ru.stogram.models.PostEntity

class PostsLocalDataSource(
    private val realm: Realm
) : IPostsLocalDataSource {

    override fun getAllPostsAsFlow(): Flow<List<PostEntity>> {
        return realm.query<PostEntity>().asFlow().map { result ->
            result.list.ifEmpty {
                createLocalData()
            }
        }
    }

    override fun getAllPostsAsCommonFlow(): CFlow<List<PostEntity>> {
        return getAllPostsAsFlow().wrap()
    }

    override fun clearAllPosts() {
        realm.writeBlocking {
            delete(query<PostEntity>())
        }
    }

    private fun createLocalData(): List<PostEntity> {
        val createdData = PostEntity.createRandomList()
        createdData.forEach { entity ->
            realm.writeBlocking {
                copyToRealm(entity)
            }
        }
        return createdData
    }
}

interface IPostsLocalDataSource {
    fun getAllPostsAsCommonFlow(): CFlow<List<PostEntity>>
    fun getAllPostsAsFlow(): Flow<List<PostEntity>>
    fun clearAllPosts()
}