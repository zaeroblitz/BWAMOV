package com.zaeroblitz.bwamov.home.ticket

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.adapter.TicketAdapter
import com.zaeroblitz.bwamov.model.Checkout
import com.zaeroblitz.bwamov.model.Film
import kotlinx.android.synthetic.main.activity_ticket_purchased.*

class TicketPurchasedActivity : AppCompatActivity(), View.OnClickListener {

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

        btn_back.setOnClickListener(this)
        iv_qr_code.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_back -> {
                onBackPressed()
            }
            R.id.iv_qr_code -> {
                showDialog(getString(R.string.barcode_dialog))
            }
        }
    }

    private fun showDialog(title: String){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_qr)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvDesc = dialog.findViewById(R.id.tv_desc) as TextView
        tvDesc.text = title

        val btnClose = dialog.findViewById(R.id.btn_close) as Button
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}