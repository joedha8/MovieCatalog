package id.rackspira.moviecatalog.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.rackspira.moviecatalog.R
import id.rackspira.moviecatalog.database.FavMovie
import id.rackspira.moviecatalog.database.FavMovieModel
import id.rackspira.moviecatalog.model.Result
import id.rackspira.moviecatalog.util.Constanta
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_detail_movie.*

class DetailMovieFragment : Fragment() {

    private lateinit var json: String
    private lateinit var rootView: View
    private var isFav = false

    private var favMovieModel = FavMovieModel()
    var realm = Realm.getDefaultInstance()!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null){
            json = arguments!!.getString(KEY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_detail_movie, container, false)
        return rootView
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.hide()

        val result = Gson().fromJson(json, Result::class.java)
        isFav = favMovieModel.isFavMovie(realm, json)
        if (isFav) {
            iv_fav_btn.background = ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_pink_24dp)
        } else {
            iv_fav_btn.background = ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_grey_24dp)
        }

        val favMovie = FavMovie(
                _ID = 0,
                json = json
        )

        Picasso.get().load(Constanta().urlImageBackground+result.poster_path).fit().into(background)
        tv_title.text = result.title
        tv_popularity.text = "Popularity : "+result.popularity.toString()
        tv_rate.text = result.vote_average.toString()+" / 10"
        if (result.adult)
            tv_rating.visibility = View.VISIBLE
        else {
            tv_rating.visibility = View.GONE
            tv_rate.textAlignment = View.TEXT_ALIGNMENT_CENTER
        }
        tv_deskripsi.text = result.overview

        btn_back.setOnClickListener {
            changeFragment(FavoriteFragment.newInstance())
        }

        iv_fav_btn.setOnClickListener {
            isFav = favMovieModel.isFavMovie(realm, json)
            if (isFav) {
                favMovieModel.delFavMovie(realm, json)
                iv_fav_btn.setBackgroundResource(R.drawable.ic_favorite_grey_24dp)
            } else {
                if (favMovieModel.getFavMovie(realm).count() <= 0) {
                    favMovieModel.addFavMovie(realm, favMovie)
                } else {
                    val v = favMovieModel.getLastStudent(realm)
                    val newStudent = v.copy(v._ID + 1, favMovie.json)
                    favMovieModel.addFavMovie(realm, newStudent)
                }
                iv_fav_btn.setBackgroundResource(R.drawable.ic_favorite_pink_24dp)
            }
        }
    }

    companion object {
        private const val KEY = "movie"

        fun newInstance(json: String): DetailMovieFragment {
            val fragment = DetailMovieFragment()
            val args = Bundle()
            args.putString(KEY, json)
            fragment.arguments = args
            return fragment
        }
    }

    private fun changeFragment(fragment:Fragment) {
        val activity = view!!.context as AppCompatActivity
        activity.supportFragmentManager.beginTransaction().replace(R.id.content_fragment, fragment).addToBackStack(null).commit()
    }
}
