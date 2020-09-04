package com.zaeroblitz.bwamov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.sign.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_on_boarding_two.*

class OnBoardingTwo : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_two)

        btn_next.setOnClickListener(this)
        btn_skip.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_next -> {
                val goToOnBoardingThree = Intent (this@OnBoardingTwo, OnBoardingThree::class.java)
                startActivity(goToOnBoardingThree)
            }
            R.id.btn_skip -> {
                val goToSignInScreenAct = Intent (this@OnBoardingTwo, SignInActivity::class.java)
                startActivity(goToSignInScreenAct)
            }
        }
    }
}
