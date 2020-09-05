package com.zaeroblitz.bwamov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.home.HomeScreenActivity
import com.zaeroblitz.bwamov.home.ticket.TicketPurchasedActivity
import com.zaeroblitz.bwamov.model.Film
import kotlinx.android.synthetic.main.activity_checkout_success.*

class CheckoutSuccessActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val EXTRA_DATA_TICKET = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_success)

        btn_home.setOnClickListener(this)
        btn_view_ticket.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_home -> {
                val goHomeScreenActivity = Intent(this@CheckoutSuccessActivity, HomeScreenActivity::class.java)
                startActivity(goHomeScreenActivity)
            }
            R.id.btn_view_ticket -> {
                val data = intent.getParcelableExtra<Film>(EXTRA_DATA_TICKET)
                val mIntent = Intent(this, TicketPurchasedActivity::class.java)
                mIntent.putExtra(TicketPurchasedActivity.EXTRA_DATA, data)
                startActivity(mIntent)
            }
        }
    }
}
