package com.zaeroblitz.bwamov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.sign.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_on_boarding_three.*

class OnBoardingThree : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_three)

        btn_start.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_start) {
            finishAffinity()
            val goToSignInScreenAct = Intent (this@OnBoardingThree, SignInActivity::class.java)
            startActivity(goToSignInScreenAct)
        }
    }
}
