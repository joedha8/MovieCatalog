package id.rackspira.moviecatalog.ui.fragment.search

import id.rackspira.moviecatalog.model.Result
import id.rackspira.moviecatalog.base.BaseContract

class SearchContract {
    interface View: BaseContract.View{
        fun showProgress(show: Boolean)
        fun showError(error: String)
        fun showSearchResult(results: List<Result>)
    }

    interface Presenter: BaseContract.Presenter<View>{
        fun loadSearchMovie(find: String)
    }
}