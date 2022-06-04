package ru.stogram.database

import io.realm.Realm
import io.realm.RealmConfiguration
import ru.stogram.models.CommentEntity
import ru.stogram.models.PostEntity
import ru.stogram.models.ReactionEntity
import ru.stogram.models.UserEntity

class RealmDataBase {
    val realm: Realm by lazy {
        val configuration = RealmConfiguration.with(
            schema = setOf(
                PostEntity::class,
                UserEntity::class,
                ReactionEntity::class,
                CommentEntity::class
            ))
            //.deleteRealmIfMigrationNeeded().schemaVersion(1).build()
        Realm.open(configuration)
    }
}