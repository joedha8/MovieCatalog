package id.rackspira.moviecatalog.di.component

import dagger.Component
import id.rackspira.moviecatalog.BaseApp
import id.rackspira.moviecatalog.di.module.ApplicationModule

@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(application: BaseApp)

}