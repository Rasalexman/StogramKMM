package ru.stogram.android.di.hilt

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.stogram.android.mappers.*
import ru.stogram.android.mappers.domain.IPostDomainToUIMapper
import ru.stogram.android.mappers.domain.IUserDomainToUIMapper
import ru.stogram.android.mappers.domain.PostDomainToUIMapper
import ru.stogram.android.mappers.domain.UserDomainToUIMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MappersModule {

    @Singleton
    @Provides
    fun provideIPostItemUIMapper(userUIMapper: IUserUIMapper): IPostItemUIMapper {
        return PostItemUIMapper(userUIMapper)
    }

    @Singleton
    @Provides
    fun provideIPostDomainToUIMapper(userUIMapper: IUserDomainToUIMapper): IPostDomainToUIMapper {
        return PostDomainToUIMapper(userUIMapper)
    }

    @Provides
    fun provideIUserDomainToUIMapper(): IUserDomainToUIMapper {
        return UserDomainToUIMapper()
    }

    @Singleton
    @Provides
    fun provideICommentItemUIMapper(): ICommentItemUIMapper {
        return CommentItemUIMapper()
    }

    @Provides
    fun provideIUserUIMapper(): IUserUIMapper {
        return UserUIMapper()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface MapperAbstractions {
        @Singleton
        @Binds
        fun provideIReactionItemUIMapper(instance: ReactionItemUIMapper): IReactionItemUIMapper
    }
}