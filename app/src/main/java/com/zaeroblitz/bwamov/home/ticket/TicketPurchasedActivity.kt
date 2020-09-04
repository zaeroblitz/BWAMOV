package com.zaeroblitz.bwamov.home.ticket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.adapter.TicketAdapter
import com.zaeroblitz.bwamov.model.Checkout
import com.zaeroblitz.bwamov.model.Film
import kotlinx.android.synthetic.main.activity_ticket_purchased.*

class TicketPurchasedActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_DATA = "data"
    }

    private val dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_purchased)

        val data =  intent.getParcelableExtra<Film>(EXTRA_DATA)
        tv_title.text = data?.judul
        tv_genre.text = data?.genre
        tv_rating.text = data?.rating

        Glide.with(this)
            .load(data?.poster)
            .centerCrop()
            .into(iv_poster)

        rv_tickets.layoutManager = LinearLayoutManager(this)
        dataList.add(Checkout("C1", 25000))
        dataList.add(Checkout("C2", 25000))

        rv_tickets.adapter = TicketAdapter(dataList){

        }
    }
}