package ru.stogram.android.di.hilt

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.stogram.android.mappers.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MappersModule {

    @Singleton
    @Provides
    fun provideIPostItemUIMapper(): IPostItemUIMapper {
        return PostItemUIMapper()
    }

    @Singleton
    @Provides
    fun provideICommentItemUIMapper(): ICommentItemUIMapper {
        return CommentItemUIMapper()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface MapperAbstractions {
        @Singleton
        @Binds
        fun provideIReactionItemUIMapper(instance: ReactionItemUIMapper): IReactionItemUIMapper
    }
}