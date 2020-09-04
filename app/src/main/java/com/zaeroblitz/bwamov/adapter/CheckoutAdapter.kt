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

class CheckoutAdapter(private var data: List<Checkout>, private val listener: (Checkout) -> Unit)
    : RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {

    private lateinit var contextAdapter: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_checkout, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val tvSeat: TextView = view.findViewById(R.id.tv_seat)
        private val tvPrice: TextView = view.findViewById(R.id.tv_price)

        fun bind(dataCheckout: Checkout, listener: (Checkout) -> Unit, context: Context){

            val localId = Locale("in","ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localId)
            tvPrice.text = formatRupiah.format(dataCheckout.harga?.toDouble())

            if (dataCheckout.kursi!!.startsWith("Total")){
                tvSeat.text = dataCheckout.kursi
                tvSeat.setCompoundDrawables(null, null, null, null)
            } else {
                val seat = "Seat No. ${dataCheckout.kursi}"
                tvSeat.text = seat
            }


            itemView.setOnClickListener{
                listener(dataCheckout)
            }
        }
    }

}
