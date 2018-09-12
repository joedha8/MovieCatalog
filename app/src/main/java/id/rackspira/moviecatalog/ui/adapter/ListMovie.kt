package id.rackspira.moviecatalog.ui.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.rackspira.moviecatalog.R
import id.rackspira.moviecatalog.util.Constanta
import id.rackspira.moviecatalog.model.Result

class ListMovie(private val context: Context, private val list: MutableList<Result>,
                fragment: Fragment): RecyclerView.Adapter<ListMovie.ViewHolder>() {

    private val listener: ListMovie.OnItemClickListener

    init {
        listener = fragment as ListMovie.OnItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ListMovie.ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = list[position]

        Picasso.get().load(Constanta().urlImage+result.poster_path).fit().into(holder.imagePoster)
        holder.title.text = result.title
        holder.descreption.text = result.overview
        holder.realeseDate.text = result.release_date

        holder.layout.setOnClickListener {
            listener.onItemClicked(Gson().toJson(result))
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var layout = itemView.findViewById<ConstraintLayout>(R.id.item_layout)!!
        val title = itemView.findViewById<TextView>(R.id.tv_title_list)!!
        val descreption = itemView.findViewById<TextView>(R.id.tv_deskripsi_list)!!
        val realeseDate = itemView.findViewById<TextView>(R.id.tv_realese_date_list)!!
        val imagePoster = itemView.findViewById<ImageView>(R.id.iv_poster_list)!!
    }

    interface OnItemClickListener{
        fun onItemClicked(json: String)
    }
}