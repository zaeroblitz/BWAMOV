package com.zaeroblitz.bwamov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.sign.signin.SignInActivity
import com.zaeroblitz.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.activity_on_boarding_one.*

class OnBoardingOne : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val ON_BOARDING_BYPASS = "on_boarding_bypass"
    }

    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_one)

        preferences = Preferences(this)

        // Untuk get bypass OnBoardingScreen jika user sudah pernah membuka aplikasi sebelumnya
        if (preferences.getValues(ON_BOARDING_BYPASS).equals("1")){
            finishAffinity()

            val goToSigInScreenAct = Intent(this@OnBoardingOne, SignInActivity::class.java)
            startActivity(goToSigInScreenAct)
        }

        btn_next.setOnClickListener(this)
        btn_skip.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_next -> {
                val goToOnBoardingTwo = Intent(this@OnBoardingOne, OnBoardingTwo::class.java)
                startActivity(goToOnBoardingTwo)
            }
            R.id.btn_skip -> {
                // Untuk set bypass OnBoardingScreen jika user sudah pernah membuka aplikasi sebelumnya
                preferences.setValues(ON_BOARDING_BYPASS, "1")
                finishAffinity()

                val goToSigInScreenAct = Intent(this@OnBoardingOne, SignInActivity::class.java)
                startActivity(goToSigInScreenAct)
            }
        }
    }
}
