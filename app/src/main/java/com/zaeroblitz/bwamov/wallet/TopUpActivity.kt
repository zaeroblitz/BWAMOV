package com.zaeroblitz.bwamov.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import com.zaeroblitz.bwamov.R
import kotlinx.android.synthetic.main.activity_top_up.*

class TopUpActivity : AppCompatActivity(), View.OnClickListener {

    private var status: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_up)

        btn_top_up_now.setOnClickListener(this)
        btn_top_up_1.setOnClickListener(this)
        btn_top_up_2.setOnClickListener(this)
        btn_top_up_3.setOnClickListener(this)
        btn_top_up_4.setOnClickListener(this)
        btn_top_up_5.setOnClickListener(this)
        btn_top_up_6.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_top_up_now -> {
                val goSuccessTopUpActivity = Intent(this@TopUpActivity, SuccessTopUpActivity::class.java)
                startActivity(goSuccessTopUpActivity)
            }
            R.id.btn_top_up_1 -> {
                if (status){
                    selectedTopUp(btn_top_up_1)
                } else {
                    deselectedTopUp(btn_top_up_1)
                }
            }
            R.id.btn_top_up_2 -> {
                if (status){
                    selectedTopUp(btn_top_up_2)
                } else {
                    deselectedTopUp(btn_top_up_2)
                }
            }
            R.id.btn_top_up_3 -> {
                if (status){
                    selectedTopUp(btn_top_up_3)
                } else {
                    deselectedTopUp(btn_top_up_3)
                }
            }
            R.id.btn_top_up_4 -> {
                if (status){
                    selectedTopUp(btn_top_up_4)
                } else {
                    deselectedTopUp(btn_top_up_4)
                }
            }
            R.id.btn_top_up_5 -> {
                if (status){
                    selectedTopUp(btn_top_up_5)
                } else {
                    deselectedTopUp(btn_top_up_5)
                }
            }
            R.id.btn_top_up_6 -> {
                if (status){
                    selectedTopUp(btn_top_up_6)
                } else {
                    deselectedTopUp(btn_top_up_6)
                }
            }
        }
    }

    private fun selectedTopUp(button: Button){
        button.setBackgroundResource(R.drawable.shape_pink_rectangle)
        btn_top_up_now.visibility = View.VISIBLE
        btn_top_up_1.setBackgroundResource(R.drawable.ic_rectangle_empty)
        btn_top_up_2.setBackgroundResource(R.drawable.ic_rectangle_empty)
        btn_top_up_3.setBackgroundResource(R.drawable.ic_rectangle_empty)
        btn_top_up_4.setBackgroundResource(R.drawable.ic_rectangle_empty)
        btn_top_up_5.setBackgroundResource(R.drawable.ic_rectangle_empty)
        btn_top_up_6.setBackgroundResource(R.drawable.ic_rectangle_empty)
        button.setBackgroundResource(R.drawable.shape_pink_rectangle)
        status = true
    }

    private fun deselectedTopUp(button: Button){
        button.setBackgroundResource(R.drawable.ic_rectangle_empty)
        btn_top_up_now.visibility = View.VISIBLE
        status = false
    }
}