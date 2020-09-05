package com.zaeroblitz.bwamov.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.home.HomeScreenActivity
import kotlinx.android.synthetic.main.activity_checkout_success.*
import kotlinx.android.synthetic.main.activity_success_top_up.*
import kotlinx.android.synthetic.main.activity_success_top_up.btn_home

class SuccessTopUpActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_top_up)

        btn_view_transaction.setOnClickListener(this)
        btn_home.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_view_transaction -> {
                val goMyWalletActivity = Intent(this@SuccessTopUpActivity, MyWalletActivity::class.java)
                startActivity(goMyWalletActivity)
            }
            R.id.btn_home -> {
                val goHomeScreenActivity = Intent(this@SuccessTopUpActivity, HomeScreenActivity::class.java)
                startActivity(goHomeScreenActivity)
            }
        }
    }
}