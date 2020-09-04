package com.zaeroblitz.bwamov.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.model.Film

class NowPlayingAdapter(private var data: List<Film>, private val listener: (Film) -> Unit)
    : RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {

    private lateinit var contextAdapter: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_now_playing, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tv_title)
        private val tvGenre: TextView = view.findViewById(R.id.tv_genre)
        private val tvRating: TextView = view.findViewById(R.id.tv_rating)
        private val ivPoster: ImageView = view.findViewById(R.id.iv_poster_film_now)

        fun bind(dataFilm: Film, listener: (Film) -> Unit, context: Context){
            tvTitle.text = dataFilm.judul
            tvGenre.text = dataFilm.genre
            tvRating.text = dataFilm.rating

            Glide.with(context)
                .load(dataFilm.poster)
                .centerCrop()
                .into(ivPoster)

            itemView.setOnClickListener{
                listener(dataFilm)
            }
        }
    }

}
