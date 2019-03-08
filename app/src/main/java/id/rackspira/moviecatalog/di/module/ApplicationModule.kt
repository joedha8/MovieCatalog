package id.rackspira.moviecatalog.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import id.rackspira.moviecatalog.BaseApp
import id.rackspira.moviecatalog.di.scope.PerApplication
import javax.inject.Singleton

@Module
class ApplicationModule(private val baseApp: BaseApp) {

    @Provides
    @Singleton
    @PerApplication
    fun provideApplication(): Application {
        return baseApp
    }
}