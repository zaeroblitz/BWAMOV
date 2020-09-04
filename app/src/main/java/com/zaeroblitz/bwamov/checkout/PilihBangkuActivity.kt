package com.zaeroblitz.bwamov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.model.Checkout
import com.zaeroblitz.bwamov.model.Film
import kotlinx.android.synthetic.main.activity_pilih_bangku.*

class PilihBangkuActivity : AppCompatActivity() , View.OnClickListener{

    private var statusA1: Boolean = false
    private var statusA2: Boolean = false
    private var statusA3: Boolean = false
    private var statusA4: Boolean = false

    private var total: Int = 0

    private var dataCheckout = ArrayList<Checkout>()

    companion object{
        const val EXTRA_DATA_CHOOSE_SEAT = "data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_bangku)

        val data = intent.getParcelableExtra<Film>(EXTRA_DATA_CHOOSE_SEAT)
        tv_title_choose_seat.text = data?.judul

        seat_a1.setOnClickListener(this)
        seat_a2.setOnClickListener(this)
        seat_a3.setOnClickListener(this)
        seat_a4.setOnClickListener(this)
        btn_choose_seat.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            // Melakukan validasi pada setiap seat
            /*
                Jika seat tidak dipilih ->  status menjadi false
                                            icon seat menjadi empty(default)
                                            melakukan decrement pada jumlah tiket
            */
            /*
               Jika seat dipilih ->  status menjadi true
                                     icon seat menjadi selected
                                     melakukan increment pada jumlah tiket
                                     memanggil fungsi Checkout untuk mendapatkan nomor seat $ harga
           */
            R.id.seat_a1 -> {
                if (statusA1){
                    seat_a1.setImageResource(R.drawable.ic_rectangle_empty)
                    statusA1 = false
                    total -= 1
                    buyTotalTicket(total)
                } else {
                    seat_a1.setImageResource(R.drawable.ic_selected)
                    statusA1 = true
                    total += 1
                    buyTotalTicket(total)

                    val dataTicket = Checkout("A1", 25000)
                    dataCheckout.add(dataTicket)
                }
            }
            R.id.seat_a2 -> {
                if (statusA2){
                    seat_a2.setImageResource(R.drawable.ic_rectangle_empty)
                    statusA2 = false
                    total -= 1
                    buyTotalTicket(total)
                } else {
                    seat_a2.setImageResource(R.drawable.ic_selected)
                    statusA2 = true
                    total += 1
                    buyTotalTicket(total)

                    val dataTicket = Checkout("A2", 25000)
                    dataCheckout.add(dataTicket)
                }
            }
            R.id.seat_a3 -> {
                if (statusA3){
                    seat_a3.setImageResource(R.drawable.ic_rectangle_empty)
                    statusA3 = false
                    total -= 1
                    buyTotalTicket(total)
                } else {
                    seat_a3.setImageResource(R.drawable.ic_selected)
                    statusA3 = true
                    total += 1
                    buyTotalTicket(total)

                    val dataTicket = Checkout("A3", 25000)
                    dataCheckout.add(dataTicket)
                }
            }
            R.id.seat_a4 -> {
                if (statusA4){
                    seat_a4.setImageResource(R.drawable.ic_rectangle_empty)
                    statusA4 = false
                    total -= 1
                    buyTotalTicket(total)
                } else {
                    seat_a4.setImageResource(R.drawable.ic_selected)
                    statusA4 = true
                    total += 1
                    buyTotalTicket(total)

                    val data = Checkout("A4", 25000)
                    dataCheckout.add(data)
                }
            }
            R.id.btn_choose_seat -> {
                val data = intent.getParcelableExtra<Film>(EXTRA_DATA_CHOOSE_SEAT)
                val goCheckoutActivity = Intent(this, CheckoutActivity::class.java)
                goCheckoutActivity.putExtra(CheckoutActivity.EXTRA_DATA_CHECKOUT, dataCheckout)
                goCheckoutActivity.putExtra(CheckoutActivity.EXTRA_DATA_FILM, data)
                startActivity(goCheckoutActivity)
            }
        }
    }

    // Membuat fungsi untuk mengganti text pada btn_choose seat
    private fun buyTotalTicket(total: Int) {
        val buyTotalTicket = "Beli Tiket"
        val buyTotalTicketMultiple = "Beli Tiket ($total)"
        if (total == 0){
            btn_choose_seat.text = buyTotalTicket
            btn_choose_seat.visibility = View.INVISIBLE
        } else {
            btn_choose_seat.text = buyTotalTicketMultiple
            btn_choose_seat.visibility = View.VISIBLE
        }
    }
}
