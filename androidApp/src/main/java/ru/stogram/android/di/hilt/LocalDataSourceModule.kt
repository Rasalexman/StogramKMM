package ru.stogram.android.di.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.stogram.database.RealmDataBase
import ru.stogram.sources.local.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {

    @Singleton
    @Provides
    fun provideIUserLocalDataSource(realmDataBase: RealmDataBase): IUserLocalDataSource {
        return UserLocalDataSource(realmDataBase)
    }

    @Singleton
    @Provides
    fun provideIUserStoriesLocalDataSource(realmDataBase: RealmDataBase): IUserStoriesLocalDataSource {
        return UserStoriesLocalDataSource(realmDataBase)
    }

    @Singleton
    @Provides
    fun provideIPostsLocalDataSource(realmDataBase: RealmDataBase): IPostsLocalDataSource {
        return PostsLocalDataSource(realmDataBase)
    }

    @Singleton
    @Provides
    fun provideIReactionsLocalDataSource(realmDataBase: RealmDataBase): IReactionsLocalDataSource {
        return ReactionsLocalDataSource(realmDataBase)
    }

    @Singleton
    @Provides
    fun provideICommentsLocalDataSource(realmDataBase: RealmDataBase): ICommentsLocalDataSource {
        return CommentsLocalDataSource(realmDataBase)
    }

    @Singleton
    @Provides
    fun provideISearchLocalDataSource(realmDataBase: RealmDataBase): ISearchLocalDataSource {
        return SearchLocalDataSource(realmDataBase)
    }

}
