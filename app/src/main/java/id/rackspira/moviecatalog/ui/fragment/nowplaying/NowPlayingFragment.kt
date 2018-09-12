package id.rackspira.moviecatalog.ui.fragment.nowplaying

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import id.rackspira.moviecatalog.R
import id.rackspira.moviecatalog.di.component.DaggerFragmentComponent
import id.rackspira.moviecatalog.di.module.FragmentModule
import id.rackspira.moviecatalog.model.Result
import id.rackspira.moviecatalog.ui.adapter.ListMovie
import id.rackspira.moviecatalog.ui.fragment.DetailMovieFragment
import kotlinx.android.synthetic.main.fragment_now_playing.*
import javax.inject.Inject

class NowPlayingFragment : Fragment(), NowPlayingContract.View, ListMovie.OnItemClickListener {
    @Inject
    lateinit var presenter: NowPlayingContract.Presenter

    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_now_playing, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.show()
        (activity as AppCompatActivity).supportActionBar!!.title = "Now Playing"
        presenter.attach(this)
        presenter.subscribe()
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
    }

    private fun injectDependency() {
        val listComponent = DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule())
                .build()

        listComponent.inject(this)
    }

    private fun initView() {
        presenter.loadNowPlaying()
    }

    companion object {
        fun newInstance() = NowPlayingFragment()
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }
    }

    override fun showError(error: String) {
        Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show()
    }

    override fun showNowPlaying(results: List<Result>) {
        val adapter = ListMovie(activity!!, results.toMutableList(), this)
        rv_now_playing.layoutManager = LinearLayoutManager(activity)
        rv_now_playing.adapter = adapter
    }

    override fun onItemClicked(json: String) {
        changeFragment(DetailMovieFragment.newInstance(json))
    }

    private fun changeFragment(fragment:Fragment) {
        val activity = view!!.context as AppCompatActivity
        activity.supportFragmentManager.beginTransaction().replace(R.id.content_fragment, fragment).addToBackStack(null).commit()
    }
}
