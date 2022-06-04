package ru.stogram.android.di

import com.rasalexman.kodi.core.*
import ru.stogram.database.RealmDataBase
import ru.stogram.repository.*
import ru.stogram.sources.local.*

val databaseModule = kodiModule {
    bind<RealmDataBase>() with single { RealmDataBase() }
}

val ldsModule = kodiModule {
    bind<IUserLocalDataSource>() with provider { UserLocalDataSource(instance()) }
    bind<IUserStoriesLocalDataSource>() with provider { UserStoriesLocalDataSource(instance()) }
    bind<IPostsLocalDataSource>() with provider { PostsLocalDataSource(instance()) }
    bind<IReactionsLocalDataSource>() with provider { ReactionsLocalDataSource(instance()) }
    bind<ICommentsLocalDataSource>() with provider { CommentsLocalDataSource(instance()) }
}

val dataModule = kodiModule {
    bind<IUserRepository>() with single { UserRepository(instance()) }
    bind<IUserStoriesRepository>() with single { UserStoriesRepository(instance()) }
    bind<IPostsRepository>() with single { PostsRepository(instance()) }
    bind<IReactionsRepository>() with single { ReactionsRepository(instance()) }
    bind<ISearchRepository>() with single { SearchRepository() }
    bind<ICommentsRepository>() with single { CommentsRepository(instance()) }
}

