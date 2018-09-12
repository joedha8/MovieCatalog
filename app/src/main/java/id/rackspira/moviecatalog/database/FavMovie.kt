package id.rackspira.moviecatalog.database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class FavMovie (
        @PrimaryKey open var _ID: Int = 0,
        open var json: String = ""
) : RealmObject(){
    fun copy(
            _ID: Int = this._ID,
            json: String = this.json
    ) = FavMovie(_ID, json)
}