package ru.stogram.android.di

import com.rasalexman.kodi.core.*
import ru.stogram.database.RealmDataBase
import ru.stogram.repository.*
import ru.stogram.sources.local.*

val databaseModule = kodiModule {
    bind<RealmDataBase>() with single { RealmDataBase() }
}

val ldsModule = kodiModule {
    bind<IUserStoriesLocalDataSource>() with provider { UserStoriesLocalDataSource(instance()) }
    bind<IPostsLocalDataSource>() with provider { PostsLocalDataSource(instance()) }
    bind<IReactionsLocalDataSource>() with provider { ReactionsLocalDataSource(instance()) }
}

val dataModule = kodiModule {
    bind<IUserStoriesRepository>() with single { UserStoriesRepository(instance()) }
    bind<IPostsRepository>() with single { PostsRepository(instance()) }
    bind<IReactionsRepository>() with single { ReactionsRepository(instance()) }
    bind<ISearchRepository>() with single { SearchRepository() }
}

