package com.zaeroblitz.bwamov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.adapter.CheckoutAdapter
import com.zaeroblitz.bwamov.model.Checkout
import com.zaeroblitz.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val EXTRA_DATA_CHECKOUT = "data"
    }

    // Menampung data dari kelas model Checkout
    private var dataList = ArrayList<Checkout>()
    private var total: Int = 0
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        preferences = Preferences(this)

        // Mendapatkan data dari ChooseSeatActivity
        dataList = intent.getSerializableExtra(EXTRA_DATA_CHECKOUT) as ArrayList<Checkout>

        // Mendapatkan total harga
        for (a in dataList.indices){
            total += dataList[a].harga!!
        }

        dataList.add(Checkout("Total harus dibayar", total))

        // Setting Layout Manager untuk RecyclerView TotalSeatAndPrice
        rv_tickets.layoutManager = LinearLayoutManager(this)
        rv_tickets.adapter = CheckoutAdapter(dataList) {}

        btn_pay.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_pay -> {
                val goSuccessActivity = Intent(this, CheckoutSuccessActivity::class.java)
                startActivity(goSuccessActivity)
            }
            R.id.btn_cancel -> {

            }
        }
    }
}
