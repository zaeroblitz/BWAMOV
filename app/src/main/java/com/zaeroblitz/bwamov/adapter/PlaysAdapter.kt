package com.zaeroblitz.bwamov.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.model.Plays

class PlaysAdapter(private var data: List<Plays>, private val listener: (Plays) -> Unit)
    : RecyclerView.Adapter<PlaysAdapter.ViewHolder>() {

    private lateinit var contextAdapter: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_actors, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.findViewById(R.id.tv_actors_name)
        private val ivActorsAvatar: ImageView = view.findViewById(R.id.iv_actors_avatar)

        fun bind(dataPlays: Plays, listener: (Plays) -> Unit, context: Context){
            tvName.text = dataPlays.nama

            Glide.with(context)
                .load(dataPlays.url)
                .apply(RequestOptions.circleCropTransform())
                .into(ivActorsAvatar)

            itemView.setOnClickListener{
                listener(dataPlays)
            }
        }
    }

}
