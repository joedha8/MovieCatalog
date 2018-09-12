package id.rackspira.moviecatalog.ui.fragment.nowplaying

import id.rackspira.moviecatalog.model.Result
import id.rackspira.moviecatalog.ui.base.BaseContract

class NowPlayingContract {
    interface View: BaseContract.View{
        fun showProgress(show: Boolean)
        fun showError(error: String)
        fun showNowPlaying(results: List<Result>)
    }

    interface Presenter: BaseContract.Presenter<View>{
        fun loadNowPlaying()
    }
}