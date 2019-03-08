package id.rackspira.moviecatalog.ui.fragment.upcoming

import id.rackspira.moviecatalog.model.Result
import id.rackspira.moviecatalog.base.BaseContract

class UpComingContract {
    interface View: BaseContract.View{
        fun showProgress(show: Boolean)
        fun showError(error: String)
        fun showUpComing(results: List<Result>)
    }

    interface Presenter: BaseContract.Presenter<View>{
        fun loadUpComing()
    }
}