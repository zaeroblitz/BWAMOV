package com.zaeroblitz.bwamov.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.adapter.WalletAdapter
import com.zaeroblitz.bwamov.model.Wallet
import kotlinx.android.synthetic.main.activity_my_wallet.*

class MyWalletActivity : AppCompatActivity() {

    private val dataWallet = ArrayList<Wallet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet)

        dataWallet.add(
            Wallet("Avengers Return", "Sabtu, 12 Januari, 2020", 25000.0, "0")
        )

        dataWallet.add(
            Wallet("Top Up", "Sabtu, 12 Januari, 2020", 50000.0, "1")
        )

        dataWallet.add(
            Wallet("Avengers Return", "Sabtu, 12 Januari, 2020", 25000.0, "0")
        )

        rv_transaction.layoutManager = LinearLayoutManager(this)
        rv_transaction.adapter = WalletAdapter(dataWallet){

        }

        btn_top_up.setOnClickListener {
            val goTopUpActivity = Intent(this@MyWalletActivity, TopUpActivity::class.java)
            startActivity(goTopUpActivity)
        }
    }

}
