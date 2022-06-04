package ru.stogram.android.di

import com.rasalexman.kodi.core.*
import ru.stogram.database.RealmDataBase
import ru.stogram.repository.IPostsRepository
import ru.stogram.repository.IUserStoriesRepository
import ru.stogram.repository.PostsRepository
import ru.stogram.repository.UserStoriesRepository
import ru.stogram.sources.local.IPostsLocalDataSource
import ru.stogram.sources.local.IUserStoriesLocalDataSource
import ru.stogram.sources.local.PostsLocalDataSource
import ru.stogram.sources.local.UserStoriesLocalDataSource

val databaseModule = kodiModule {
    bind<RealmDataBase>() with provider { RealmDataBase() }
}

val ldsModule = kodiModule {
    bind<IUserStoriesLocalDataSource>() with provider { UserStoriesLocalDataSource(instance()) }
    bind<IPostsLocalDataSource>() with provider { PostsLocalDataSource(instance()) }
}

val dataModule = kodiModule {
    bind<IUserStoriesRepository>() with single { UserStoriesRepository(instance()) }
    bind<IPostsRepository>() with single { PostsRepository(instance()) }
}

