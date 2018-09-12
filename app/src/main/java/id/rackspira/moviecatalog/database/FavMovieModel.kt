package id.rackspira.moviecatalog.database

import io.realm.Realm

class FavMovieModel: FavMovieInterface {

    override fun addFavMovie(realm: Realm, favMovie: FavMovie): Boolean {
        return try {
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(favMovie)
            realm.commitTransaction()
            true
        } catch (e: Exception) {
            println(e)
            false
        }
    }

    override fun delFavMovie(realm: Realm, json: String): Boolean {
        return try {
            realm.beginTransaction()
            realm.where(FavMovie::class.java).equalTo("json", json).findFirst()!!.deleteFromRealm()
            realm.commitTransaction()
            true
        } catch (e: Exception) {
            println(e)
            false
        }
    }

    override fun getFavMovie(realm: Realm): List<FavMovie> {
        return realm.where(FavMovie::class.java).findAll()
    }

    override fun isFavMovie(realm: Realm, json: String): Boolean {
        return realm.where(FavMovie::class.java).equalTo("json", json).findFirst() != null
    }

    fun getLastStudent(realm: Realm): FavMovie {
        return realm.where(FavMovie::class.java).findAll().last()!!
    }
}