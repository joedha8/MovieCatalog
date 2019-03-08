package id.rackspira.moviecatalog.service

import id.rackspira.moviecatalog.util.Constanta
import id.rackspira.moviecatalog.model.BaseMovie
import id.rackspira.moviecatalog.model.BaseSearch
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("3/movie/now_playing")
    fun getMovieNowPlaying(@Query("api_key") key: String,
                           @Query("language") language: String): Observable<BaseMovie>

    @GET("3/movie/upcoming")
    fun getMovieUpComing(@Query("api_key") key: String,
                         @Query("language") language: String): Observable<BaseMovie>

    @GET("3/search/movie")
    fun getSearchMovie(@Query("api_key") key: String,
                       @Query("language") language: String,
                       @Query("query") query: String): Observable<BaseSearch>

    companion object Factory {
        fun create(): RetrofitService {
            val retrofit = retrofit2.Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constanta().baseUrl)
                    .build()

            return retrofit.create(RetrofitService::class.java)
        }
    }
}