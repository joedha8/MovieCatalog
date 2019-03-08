package id.rackspira.moviecatalog.di.component

import dagger.Component
import id.rackspira.moviecatalog.di.module.ActivityModule
import id.rackspira.moviecatalog.ui.activity.MainActivity

@Component(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}