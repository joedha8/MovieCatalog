package id.rackspira.moviecatalog.ui.fragment.search

import id.rackspira.moviecatalog.BuildConfig
import id.rackspira.moviecatalog.model.BaseSearch
import id.rackspira.moviecatalog.service.RetrofitService
import id.rackspira.moviecatalog.util.Constanta
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchPresenter: SearchContract.Presenter {
    private val subscriptions = CompositeDisposable()
    private val api: RetrofitService = RetrofitService.create()
    private lateinit var view: SearchContract.View

    override fun subscribe() {
    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: SearchContract.View) {
        this.view = view
    }

    override fun loadSearchMovie(find: String) {
        val subscribe = api.getSearchMovie(BuildConfig.ApiKey, Constanta().language, find)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ baseSearch: BaseSearch? ->
                    view.showProgress(false)
                    if (baseSearch != null)
                        view.showSearchResult(baseSearch.results)
                }, {t: Throwable? ->
                    view.showProgress(false)
                    view.showError(t.toString())
                })
        subscriptions.add(subscribe)
    }

}