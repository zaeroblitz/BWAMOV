package com.zaeroblitz.bwamov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.home.HomeScreenActivity
import kotlinx.android.synthetic.main.activity_checkout_success.*

class CheckoutSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_success)

        btn_home.setOnClickListener {
            finishAffinity()

            val goHomeScreenActivity = Intent(this@CheckoutSuccessActivity, HomeScreenActivity::class.java)
            startActivity(goHomeScreenActivity)
        }
    }
}
