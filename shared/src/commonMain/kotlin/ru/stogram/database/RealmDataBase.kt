package ru.stogram.database

import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.stogram.models.PostEntity

class RealmDataBase {

    private val realm: Realm by lazy {
        val configuration = RealmConfiguration.Builder(schema = setOf(PostEntity::class)).deleteRealmIfMigrationNeeded()
            .schemaVersion(3).build()
        Realm.open(configuration)
    }

    fun getAllPosts(): List<PostEntity> {
        return realm.query(PostEntity::class).find()
    }

    fun getAllPostsAsFlow(): Flow<List<PostEntity>> {
        return realm.query(PostEntity::class).asFlow().map { it.list }
    }

    fun getAllPostsAsCommonFlow(): CFlow<List<PostEntity>> {
        return getAllPostsAsFlow().wrap()
    }

    fun addPost(post: PostEntity) {
        realm.writeBlocking {
            copyToRealm(post)
        }
    }

    fun deletePost(id: String) {
        realm.writeBlocking {
            query<PostEntity>(PostEntity::class,"id = $0", id)
                .first()
                .find()
                ?.let { delete(it) }
                ?: throw IllegalStateException("Post not found.")
        }
    }

    fun clearAllPosts() {
        realm.writeBlocking {
            delete(query<PostEntity>(PostEntity::class))
        }
    }
}