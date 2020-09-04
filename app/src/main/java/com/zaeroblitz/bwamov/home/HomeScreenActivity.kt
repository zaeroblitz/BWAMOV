package com.zaeroblitz.bwamov.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.home.dashboard.DashboardFragment
import com.zaeroblitz.bwamov.home.profile.ProfileFragment
import com.zaeroblitz.bwamov.home.ticket.TicketFragment
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreenActivity : AppCompatActivity(), View.OnClickListener {

    private val dashboardFragment = DashboardFragment()
    private val ticketFragment = TicketFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        // Set parent fragment
        setFragment(dashboardFragment)

        menu_dashboard.setOnClickListener(this)
        menu_ticket.setOnClickListener(this)
        menu_profile.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            /*
                   Mengganti fragment sesuai dengan icon yg dipih
            */
            R.id.menu_dashboard -> {
                changeFragment(dashboardFragment)
                changeBottomIcon(menu_dashboard, R.drawable.ic_home_active)
                changeBottomIcon(menu_ticket, R.drawable.ic_ticket)
                changeBottomIcon(menu_profile, R.drawable.ic_profile)
            }

            R.id.menu_ticket -> {
                changeFragment(ticketFragment)
                changeBottomIcon(menu_dashboard, R.drawable.ic_home)
                changeBottomIcon(menu_ticket, R.drawable.ic_ticket_active)
                changeBottomIcon(menu_profile, R.drawable.ic_profile)
            }

            R.id.menu_profile -> {
                changeFragment(profileFragment)
                changeBottomIcon(menu_dashboard, R.drawable.ic_home)
                changeBottomIcon(menu_ticket, R.drawable.ic_ticket)
                changeBottomIcon(menu_profile, R.drawable.ic_profile_active)
            }
        }
    }

    // Method untuk set Parent Fragment
    private fun setFragment(fragment: Fragment){
        val fragmentManager =  supportFragmentManager
        fragmentManager
            .beginTransaction()
            .add(R.id.layout_frame, fragment)
            .commit()
    }

    // Method untuk mengganti fragment
    private fun changeFragment(fragment: Fragment){
        val fragmentManager =  supportFragmentManager
        fragmentManager
            .beginTransaction()
            .replace(R.id.layout_frame, fragment)
            .commit()
    }

    // Method untuk mengganti bottom icon
    private fun changeBottomIcon(imageView: ImageView, newImageResource: Int){
        imageView.setImageResource(newImageResource)
    }

}
