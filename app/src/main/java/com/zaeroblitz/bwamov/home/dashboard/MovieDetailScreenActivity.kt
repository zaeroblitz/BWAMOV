package com.zaeroblitz.bwamov.home.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.checkout.PilihBangkuActivity
import com.zaeroblitz.bwamov.adapter.PlaysAdapter
import com.zaeroblitz.bwamov.model.Film
import com.zaeroblitz.bwamov.model.Plays
import kotlinx.android.synthetic.main.activity_movie_detail_screen.*

class MovieDetailScreenActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var mDatabase: DatabaseReference

    // Untuk menampung data dari kelas model Plays
    private var dataList = ArrayList<Plays>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail_screen)

        // Mendapatkan data dari setiap Film
        val data = intent.getParcelableExtra<Film>(EXTRA_DATA)

        tv_title.text = data?.judul
        tv_genre.text = data?.genre
        tv_rating.text = data?.rating
        tv_description.text = data?.desc

        Glide.with(this)
            .load(data?.poster)
            .centerCrop()
            .into(iv_poster_film)

        // Untuk mendapatkan Path Play(actor) yang ada di setiap Film
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")
            .child(data?.judul.toString())
            .child("play")

        // Setting LayoutManager pada RecyclerView Actors
        rv_actors.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getData()

        btn_choose_seat.setOnClickListener{
            val goPilihBangkuActivity = Intent(this@MovieDetailScreenActivity, PilihBangkuActivity::class.java)
            goPilihBangkuActivity.putExtra(PilihBangkuActivity.EXTRA_DATA_CHOOSE_SEAT, data)
            startActivity(goPilihBangkuActivity)
        }
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                // Agar tidak terjadi duplikasi data
                dataList.clear()

                // Mendapatkan dari dari setiap Actor
                for (getDataSnapshot in snapshot.children){
                    val actors = getDataSnapshot.getValue(Plays::class.java)
                    dataList.add(actors!!)

                    rv_actors.adapter = PlaysAdapter(dataList){}
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MovieDetailScreenActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}
