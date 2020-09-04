package com.zaeroblitz.bwamov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.zaeroblitz.bwamov.onboarding.OnBoardingOne

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        var handler = Handler()
        handler.postDelayed({
            val goToOnBoardingScreen = Intent(this@SplashScreenActivity, OnBoardingOne::class.java)
            startActivity(goToOnBoardingScreen)
            finish()
        }, 2000)
    }
}
