package com.zaeroblitz.bwamov.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.model.Checkout
import java.text.NumberFormat
import java.util.*

class TicketAdapter(private var data: List<Checkout>, private val listener: (Checkout) -> Unit)
    : RecyclerView.Adapter<TicketAdapter.ViewHolder>() {

    private lateinit var contextAdapter: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_checkout_purchased, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val tvSeat: TextView = view.findViewById(R.id.tv_seat)

        fun bind(dataCheckout: Checkout, listener: (Checkout) -> Unit, context: Context){

            val seat = "Seat No. ${dataCheckout.kursi}"
            tvSeat.text = seat

            itemView.setOnClickListener{
                listener(dataCheckout)
            }
        }
    }

}
