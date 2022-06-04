package ru.stogram.database

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import io.realm.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.stogram.models.PostEntity
import ru.stogram.models.UserEntity

class RealmDataBase {
    val realm: Realm by lazy {
        val configuration = RealmConfiguration.with(schema = setOf(PostEntity::class, UserEntity::class))
            //.deleteRealmIfMigrationNeeded().schemaVersion(1).build()
        Realm.open(configuration)
    }
}