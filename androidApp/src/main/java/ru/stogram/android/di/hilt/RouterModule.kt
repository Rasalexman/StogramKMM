package ru.stogram.android.di.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.stogram.android.navigation.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RouterModule {

    @Singleton
    @Provides
    fun provideIAppRouter(): IAppRouter {
        return AppRouter()
    }

    @Provides
    fun provideIHostRouter(appRouter: IAppRouter): IHostRouter {
        return appRouter
    }

    @Provides
    fun provideIMainRouter(appRouter: IAppRouter): IMainRouter {
        return appRouter
    }

    @Provides
    fun provideIBackToHostRouter(appRouter: IAppRouter): IBackToHostRouter {
        return appRouter
    }
}