package ru.stogram.android.di.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.stogram.repository.*
import ru.stogram.sources.local.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideIUserRepository(localDataSource: IUserLocalDataSource): IUserRepository =
        UserRepository(localDataSource)

    @Singleton
    @Provides
    fun provideIUserStoriesRepository(localDataSource: IUserStoriesLocalDataSource): IUserStoriesRepository =
        UserStoriesRepository(localDataSource)

    @Singleton
    @Provides
    fun provideIPostsRepository(localDataSource: IPostsLocalDataSource): IPostsRepository =
        PostsRepository(localDataSource)

    @Singleton
    @Provides
    fun provideIReactionsRepository(localDataSource: IReactionsLocalDataSource): IReactionsRepository =
        ReactionsRepository(localDataSource)

    @Singleton
    @Provides
    fun provideISearchRepository(localDataSource: ISearchLocalDataSource): ISearchRepository =
        SearchRepository(localDataSource)

    @Singleton
    @Provides
    fun provideICommentsRepository(localDataSource: ICommentsLocalDataSource): ICommentsRepository =
        CommentsRepository(localDataSource)

}