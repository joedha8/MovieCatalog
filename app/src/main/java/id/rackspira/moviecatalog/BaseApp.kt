package id.rackspira.moviecatalog

import android.app.Application
import id.rackspira.moviecatalog.di.component.ApplicationComponent
import id.rackspira.moviecatalog.di.component.DaggerApplicationComponent

import id.rackspira.moviecatalog.di.module.ApplicationModule
import io.realm.Realm
import io.realm.RealmConfiguration

class BaseApp: Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        var c = RealmConfiguration.Builder()
        c.name("favmovie")
        c.deleteRealmIfMigrationNeeded()
        Realm.setDefaultConfiguration(c.build())

        instance = this
        setup()

        if (BuildConfig.DEBUG) {
            // Maybe TimberPlant etc.
        }
    }

    fun setup() {
        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this)).build()
        component.inject(this)
    }

    fun getApplicationComponent(): ApplicationComponent {
        return component
    }

    companion object {
        lateinit var instance: BaseApp private set
    }
}