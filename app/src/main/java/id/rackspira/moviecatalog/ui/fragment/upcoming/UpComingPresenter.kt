package id.rackspira.moviecatalog.ui.fragment.upcoming

import id.rackspira.moviecatalog.BuildConfig
import id.rackspira.moviecatalog.model.BaseMovie
import id.rackspira.moviecatalog.service.RetrofitService
import id.rackspira.moviecatalog.util.Constanta
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UpComingPresenter: UpComingContract.Presenter {
    private val subscriptions = CompositeDisposable()
    private val api: RetrofitService = RetrofitService.create()
    private lateinit var view: UpComingContract.View

    override fun subscribe() {
    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: UpComingContract.View) {
        this.view = view
    }

    override fun loadUpComing() {
        val subscribe = api.getMovieUpComing(BuildConfig.ApiKey, Constanta().language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({baseMovie: BaseMovie? ->
                    view.showProgress(false)
                    if (baseMovie != null)
                        view.showUpComing(baseMovie.results)
                }, {t: Throwable? ->
                    view.showProgress(false)
                    view.showError(t.toString())
                })
        subscriptions.add(subscribe)
    }
}