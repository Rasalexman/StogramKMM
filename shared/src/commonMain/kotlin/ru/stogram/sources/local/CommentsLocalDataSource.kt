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
        return database.realm.query<CommentEntity>("postId = $0", postId).find().asFlow().map { result ->
            result.list.ifEmpty {
                createLocalData(postId)
            }
        }
    }

    private fun createLocalData(postId: String): List<CommentEntity> {
        val createdData = CommentEntity.createRandomList(postId)
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