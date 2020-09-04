package com.zaeroblitz.bwamov.checkout

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.adapter.CheckoutAdapter
import com.zaeroblitz.bwamov.home.ticket.TicketPurchasedActivity
import com.zaeroblitz.bwamov.model.Checkout
import com.zaeroblitz.bwamov.model.Film
import com.zaeroblitz.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.activity_checkout.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

@Suppress("UNCHECKED_CAST")
class CheckoutActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val EXTRA_DATA_CHECKOUT = "data checkout"
        const val EXTRA_DATA_FILM = "data film"
    }

    // Menampung data dari kelas model Checkout
    private var dataList = ArrayList<Checkout>()
    private var total: Int = 0
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        preferences = Preferences(this)

        // Mendapatkan data berupa data Checkout dari ChooseSeatActivity
        dataList = intent.getSerializableExtra(EXTRA_DATA_CHECKOUT) as ArrayList<Checkout>

        // Mendapatkan total harga
        for (a in dataList.indices){
            total += dataList[a].harga!!
        }

        dataList.add(Checkout("Total harus dibayar", total))

        checkingBalance(preferences.getValues(Preferences.USER_BALANCE).toString())

        // Setting Layout Manager untuk RecyclerView TotalSeatAndPrice
        rv_tickets.layoutManager = LinearLayoutManager(this)
        rv_tickets.adapter = CheckoutAdapter(dataList) {}

        btn_pay.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_pay -> {
                // Mengambil data berupa data Film
                val data = intent.getParcelableExtra<Film>(EXTRA_DATA_FILM)

                val goSuccessActivity = Intent(this, CheckoutSuccessActivity::class.java)
                goSuccessActivity.putExtra(CheckoutSuccessActivity.EXTRA_DATA_TICKET, data)
                startActivity(goSuccessActivity)

                showNotification(data)

            }
            R.id.btn_cancel -> {
                finish()
            }
        }
    }

    private fun checkingBalance(balance: String) {
        if (balance.isNotEmpty()) {
            val localeID = Locale("in", "ID")
            val formatRupiah  = NumberFormat.getCurrencyInstance(localeID)
            tv_current_balance.text = formatRupiah.format(balance.toDouble())
            btn_pay.visibility = View.VISIBLE
            tv_balance_status.visibility = View.VISIBLE
        } else {
            val noBalance = "Rp. 0"
            val noEnoughBalance = "Saldo pada e-wallet kamu tidak mencukupi untuk melakukan transaksi"
            tv_current_balance.text = noBalance
            btn_pay.visibility = View.VISIBLE
            tv_balance_status.visibility = View.VISIBLE
            tv_balance_status.text = noEnoughBalance
        }
    }

    private fun showNotification(datas: Film){
        val NOTIFICATION_CHANNEL_ID = "channel_id"
        val context = this.applicationContext
        var notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelName = "BWA MOV Channel Notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val mChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance)
            notificationManager.createNotificationChannel(mChannel)
        }

        val mIntent = Intent(this, TicketPurchasedActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(TicketPurchasedActivity.EXTRA_DATA, datas)
        mIntent.putExtras(bundle)

        val pendingIntent = PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.logo_mov)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    this.resources,
                    R.drawable.logo_notification
                )
            )
            .setTicker("Notif BWA  Starting")
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setLights(Color.RED, 3000, 3000)
            .setDefaults(Notification.DEFAULT_SOUND)
            .setContentTitle("Success buy ticket")
            .setContentText("Tiket ${datas.judul} berhasil kamu dapatkan. Enjoy the movie.")

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(115, builder.build())
    }
}
