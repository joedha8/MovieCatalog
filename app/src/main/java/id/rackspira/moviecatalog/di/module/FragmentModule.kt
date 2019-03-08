package id.rackspira.moviecatalog.di.module

import dagger.Module
import dagger.Provides
import id.rackspira.moviecatalog.service.RetrofitService
import id.rackspira.moviecatalog.ui.fragment.nowplaying.NowPlayingContract
import id.rackspira.moviecatalog.ui.fragment.nowplaying.NowPlayingPresenter
import id.rackspira.moviecatalog.ui.fragment.search.SearchContract
import id.rackspira.moviecatalog.ui.fragment.search.SearchPresenter
import id.rackspira.moviecatalog.ui.fragment.upcoming.UpComingContract
import id.rackspira.moviecatalog.ui.fragment.upcoming.UpComingPresenter

@Module
class FragmentModule {

    @Provides
    fun provideNowPlayingPresenter(): NowPlayingContract.Presenter {
        return NowPlayingPresenter()
    }

    @Provides
    fun provideUpComingPresenter(): UpComingContract.Presenter {
        return UpComingPresenter()
    }

    @Provides
    fun provideSearchPresenter(): SearchContract.Presenter {
        return SearchPresenter()
    }

    @Provides
    fun provideApiService(): RetrofitService {
        return RetrofitService.create()
    }
}