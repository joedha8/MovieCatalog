package id.rackspira.moviecatalog.ui.fragment.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.widget.Toast
import id.rackspira.moviecatalog.R
import id.rackspira.moviecatalog.di.component.DaggerFragmentComponent
import id.rackspira.moviecatalog.di.module.FragmentModule
import id.rackspira.moviecatalog.model.Result
import id.rackspira.moviecatalog.ui.adapter.ListMovie
import id.rackspira.moviecatalog.ui.fragment.DetailMovieFragment
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject


class SearchFragment : Fragment(), SearchContract.View, ListMovie.OnItemClickListener {
    @Inject
    lateinit var presenter: SearchContract.Presenter

    private lateinit var rootView: View

    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependency()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        rootView = inflater.inflate(R.layout.fragment_search, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.show()
        (activity as AppCompatActivity).supportActionBar!!.title = "Search Movie"
        presenter.attach(this)
        presenter.subscribe()
        progress_bar_search.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.menu_search, menu)
        val searchItem = menu!!.findItem(R.id.action_search)
        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }
        if (searchView != null) {
            searchView!!.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))

            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    Log.i("onQueryTextChange", newText)
                    progress_bar_search.visibility = View.VISIBLE
                    presenter.loadSearchMovie(newText)
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    Log.i("onQueryTextSubmit", query)
                    progress_bar_search.visibility = View.VISIBLE
                    presenter.loadSearchMovie(query)
                    return true
                }
            }
            searchView!!.setOnQueryTextListener(queryTextListener)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId){
            R.id.action_search -> {
                return false
            }
        }

        searchView!!.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
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

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            progress_bar_search.visibility = View.VISIBLE
        } else {
            progress_bar_search.visibility = View.GONE
        }
    }

    override fun showError(error: String) {
        Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show()
    }

    override fun showSearchResult(results: List<Result>) {
        val adapter = ListMovie(activity!!, results.toMutableList(), this)
        rv_search.layoutManager = LinearLayoutManager(activity)
        rv_search.adapter = adapter
    }

    override fun onItemClicked(json: String) {
        changeFragment(DetailMovieFragment.newInstance(json))
    }

    private fun changeFragment(fragment:Fragment) {
        val activity = view!!.context as AppCompatActivity
        activity.supportFragmentManager.beginTransaction().replace(R.id.content_fragment, fragment).addToBackStack(null).commit()
    }
}
