package com.zaeroblitz.bwamov.home.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zaeroblitz.bwamov.wallet.MyWalletActivity
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.iv_profile
import kotlinx.android.synthetic.main.fragment_profile.tv_name

class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(context!!)

        tv_name.text = preferences.getValues(Preferences.USER_NAME)
        tv_email.text = preferences.getValues(Preferences.USER_EMAIL)

        if (preferences.getValues(Preferences.USER_URL) == "") {
            Glide.with(this)
                .load(R.drawable.user_pic)
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile)
        } else {
            Glide.with(this)
                .load(preferences.getValues(Preferences.USER_URL))
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile)
        }

        tv_my_wallet.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.tv_my_wallet -> {
                val goToMyWalletActivity = Intent(activity, MyWalletActivity::class.java)
                startActivity(goToMyWalletActivity)
            }
        }
    }
}