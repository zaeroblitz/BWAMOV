package com.zaeroblitz.bwamov.home.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.adapter.ComingSoonAdapter
import com.zaeroblitz.bwamov.adapter.NowPlayingAdapter
import com.zaeroblitz.bwamov.model.Film
import com.zaeroblitz.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment() {

    private lateinit var preferences: Preferences
    private lateinit var mDatabase: DatabaseReference

    // Menampung data dari kelas model Film
    private var dataList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // SharedPrefernces
        preferences = Preferences(activity!!.applicationContext)

        // Mendapatkan path Film dari FirebaseDatabase
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        tv_name.text = preferences.getValues(Preferences.USER_NAME)

        //currency(preferences.getValues(Preferences.USER_BALANCE)!!.toDouble(), tv_balance)
        if (!preferences.getValues(Preferences.USER_BALANCE).equals("")){
            currency(preferences.getValues(Preferences.USER_BALANCE)!!.toDouble(), tv_balance)
        }

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


        // Setting LayoutManager RecyclerView dari Now Playing dan Coming Soon
        rv_now_playing.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_coming_soon.layoutManager = LinearLayoutManager(context)

        getData()


    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                // Agar tidak terjadi duplikasi data
                dataList.clear()

                // Mendapatkan data dari setiap film
                for (getDataSnapshot in snapshot.children){
                    val film = getDataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                rv_now_playing.adapter = NowPlayingAdapter(dataList){
                    val goToDetailScreenActivity = Intent(context, MovieDetailScreenActivity::class.java)
                    goToDetailScreenActivity.putExtra(MovieDetailScreenActivity.EXTRA_DATA, it)
                    startActivity(goToDetailScreenActivity)
                }

                rv_coming_soon.adapter = ComingSoonAdapter(dataList){
                    val goToDetailScreenActivity = Intent(context, MovieDetailScreenActivity::class.java)
                    goToDetailScreenActivity.putExtra(MovieDetailScreenActivity.EXTRA_DATA, it)
                    startActivity(goToDetailScreenActivity)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun currency(balance: Double, textView: TextView){
        val localID = Locale("in","ID")
        val currencyFormat = NumberFormat.getCurrencyInstance(localID)
        textView.text = currencyFormat.format(balance)
    }
}