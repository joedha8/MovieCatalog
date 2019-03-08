package id.rackspira.moviecatalog.database

import io.realm.Realm

interface FavMovieInterface {
    fun addFavMovie(realm: Realm, favMovie: FavMovie): Boolean
    fun delFavMovie(realm: Realm, json: String): Boolean
    fun getFavMovie(realm: Realm): List<FavMovie>
    fun isFavMovie(realm: Realm, json: String): Boolean
}