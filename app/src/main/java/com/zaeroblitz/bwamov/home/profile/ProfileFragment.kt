package com.zaeroblitz.bwamov.home.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

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

        Glide.with(context!!)
            .load(preferences.getValues(Preferences.USER_URL))
            .apply(RequestOptions.circleCropTransform())
            .into(iv_profile)
    }
}