package ru.stogram.sources.local

import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import ru.stogram.database.CFlow
import ru.stogram.database.wrap
import ru.stogram.models.PostEntity
import kotlin.random.Random

class PostsLocalDataSource : IPostsLocalDataSource {

    private val realm: Realm by lazy {
        val configuration = RealmConfiguration.Builder(schema = setOf(PostEntity::class)).deleteRealmIfMigrationNeeded()
            .schemaVersion(2).build()
        Realm.open(configuration)
    }

    private var allPosts: List<PostEntity> = emptyList()

    override fun getAllPostsAsFlow(): Flow<List<PostEntity>> {
        return realm.query(PostEntity::class).asFlow().map { it.list }.onEmpty {
            takeAllPosts()
        }
    }

    override fun getAllPostsAsCommonFlow(): CFlow<List<PostEntity>> {
        return getAllPostsAsFlow().wrap()
    }

    private fun takeAllPosts(): List<PostEntity> {
        if(allPosts.isEmpty()) {
            allPosts = realm.query(PostEntity::class).find()
            if(allPosts.isEmpty()) {
                allPosts = createPosts()
            }
        }
        return allPosts
    }

    private fun createPosts(): List<PostEntity> {
        val createData = mutableListOf<PostEntity>()
        val randomCount: Int = Random.nextInt(24, 48)
        repeat(randomCount) {
            val entity = PostEntity()
            realm.writeBlocking {
                copyToRealm(entity)
            }
            createData.add(PostEntity())
        }
        return createData
    }

}

interface IPostsLocalDataSource {
    fun getAllPostsAsCommonFlow(): CFlow<List<PostEntity>>
    fun getAllPostsAsFlow(): Flow<List<PostEntity>>
}