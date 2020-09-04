package com.zaeroblitz.bwamov.home.ticket

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.adapter.ComingSoonAdapter
import com.zaeroblitz.bwamov.model.Film
import com.zaeroblitz.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.fragment_ticket.*

class TicketFragment : Fragment() {

    private lateinit var preferences: Preferences
    private lateinit var mDatabase: DatabaseReference
    private val dataList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(context!!)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        rv_tickets.layoutManager = LinearLayoutManager(context)
        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (getDataSnapshot in snapshot.children){
                    val film = getDataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                rv_tickets.adapter = ComingSoonAdapter(dataList){
                    val intent = Intent(context, TicketPurchasedActivity::class.java)
                    intent.putExtra(TicketPurchasedActivity.EXTRA_DATA, it)
                    startActivity(intent)
                }

                val totalMovies = "${dataList.size} Movies"
                tv_total_movies.text = totalMovies
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}