package id.rackspira.moviecatalog.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import id.rackspira.moviecatalog.R
import id.rackspira.moviecatalog.database.FavMovie
import id.rackspira.moviecatalog.database.FavMovieModel
import id.rackspira.moviecatalog.model.Result
import id.rackspira.moviecatalog.ui.adapter.ListMovie
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment(), ListMovie.OnItemClickListener {
    private lateinit var rootView: View
    private var favMovieModel = FavMovieModel()
    var realm = Realm.getDefaultInstance()!!
    private var myList: MutableList<Result> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_favorite, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.show()
        (activity as AppCompatActivity).supportActionBar!!.title = "Favorite Movie"

        val hasil: List<FavMovie> = favMovieModel.getFavMovie(realm)

        if (hasil.isNotEmpty()) {
            for (i in 0 until hasil.size){
                val result = Gson().fromJson(hasil[i].json, Result::class.java)
                myList.add(result)
            }

            Log.d("####", ""+myList)

            val adapter = ListMovie(activity!!, myList, this)
            rv_fav_movie.layoutManager = LinearLayoutManager(activity)
            rv_fav_movie.adapter = adapter
        }
    }

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    override fun onItemClicked(json: String) {
        changeFragment(DetailMovieFragment.newInstance(json))
    }

    private fun changeFragment(fragment:Fragment) {
        val activity = view!!.context as AppCompatActivity
        activity.supportFragmentManager.beginTransaction().replace(R.id.content_fragment, fragment).addToBackStack(null).commit()
    }
}
