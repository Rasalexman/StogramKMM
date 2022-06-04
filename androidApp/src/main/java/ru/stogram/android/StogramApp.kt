package ru.stogram.android

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.kodi.generated.modules.viewmodels.viewModelsModule
import com.rasalexman.kodi.core.import
import com.rasalexman.kodi.core.kodi
import ru.stogram.android.di.dataModule
import ru.stogram.android.di.databaseModule
import ru.stogram.android.di.ldsModule
import timber.log.Timber

class StogramApp : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        kodi {
            import(databaseModule)
            import(ldsModule)
            import(dataModule)
            import(viewModelsModule)
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .build()
    }
}