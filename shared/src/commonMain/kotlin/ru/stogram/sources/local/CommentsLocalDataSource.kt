package ru.stogram.sources.local

import io.realm.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.stogram.database.RealmDataBase
import ru.stogram.models.CommentEntity

class CommentsLocalDataSource(
    private val database: RealmDataBase
) : ICommentsLocalDataSource {

    override fun getAllCommentsAsFlow(postId: String): Flow<List<CommentEntity>> {
        return database.realm.query<CommentEntity>().asFlow().map { result ->
            result.list.ifEmpty {
                createLocalData()
            }
        }
    }

    private fun createLocalData(): List<CommentEntity> {
        val createdData = CommentEntity.createRandomList()
        createdData.forEach { entity ->
            database.realm.writeBlocking {
                copyToRealm(entity)
            }
        }
        return createdData
    }

}

interface ICommentsLocalDataSource {
    fun getAllCommentsAsFlow(postId: String): Flow<List<CommentEntity>>
}