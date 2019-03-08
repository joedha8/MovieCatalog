package id.rackspira.moviecatalog.di.component

import dagger.Component
import id.rackspira.moviecatalog.di.module.FragmentModule
import id.rackspira.moviecatalog.ui.fragment.upcoming.UpComingFragment
import id.rackspira.moviecatalog.ui.fragment.nowplaying.NowPlayingFragment
import id.rackspira.moviecatalog.ui.fragment.search.SearchFragment

@Component(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

    fun inject(nowPlayingFragment: NowPlayingFragment)

    fun inject(upComingFragment: UpComingFragment)

    fun inject(searchFragment: SearchFragment)

}