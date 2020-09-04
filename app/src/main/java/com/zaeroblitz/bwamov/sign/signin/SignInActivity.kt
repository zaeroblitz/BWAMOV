package com.zaeroblitz.bwamov.sign.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.zaeroblitz.bwamov.home.HomeScreenActivity
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.onboarding.OnBoardingOne
import com.zaeroblitz.bwamov.model.User
import com.zaeroblitz.bwamov.sign.signup.SignUpActivity
import com.zaeroblitz.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var iUsername: String
    private lateinit var iPassword: String

    private lateinit var mDatabase: DatabaseReference
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Untuk mendapatkan path User dari Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference("User")

        // Kelas Preference digunakan untuk SharedPrefrence
        preferences = Preferences(this)

        // Untuk set bypass OnBoardingScreen jika user sudah pernah membuka aplikasi sebelumnya
        preferences.setValues(OnBoardingOne.ON_BOARDING_BYPASS, "1")

        // Untuk get bypass HomeScreen jika user sudah melakukan login
        if (preferences.getValues(Preferences.USER_STATUS).equals("1")){
            finishAffinity()

            val goHomeScreenActivity = Intent(this@SignInActivity, HomeScreenActivity::class.java)
            startActivity(goHomeScreenActivity)
        }

        btn_login.setOnClickListener(this)
        btn_sign_up.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_login -> {

                // Mendapatkan inputan data dari EditText
                iUsername = edt_username.text.toString()
                iPassword = edt_password.text.toString()

                when {
                    // Validasi ketika editText User kosong
                    iUsername.isEmpty() -> {
                        edt_username.error = "Silahkan masukkan username Anda"
                        edt_username.requestFocus()
                    }
                    // Validasi ketika editText User kosong
                    iPassword.isEmpty() -> {
                        edt_password.error = "Silahkan masukkan password Anda"
                        edt_password.requestFocus()
                    }
                    else -> {
                        pushLogin(iUsername, iPassword)
                    }
                }
            }

            R.id.btn_sign_up -> {
                val goSignUpActivity = Intent(this@SignInActivity, SignUpActivity::class.java)
                startActivity(goSignUpActivity)
            }
        }
    }

    private fun pushLogin(iUsername: String, iPassword: String) {

        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener {

            // Method ketika ada perubahan data
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // Membuat objek dari kelas model User dan mengirim data dari Firebase ke kelas tsb
                val user = dataSnapshot.getValue(User::class.java)

                // Melakukan validasi ketika username yang dimasukkan tidak ada didalam Firebase Realtime Database
                if (user == null) {
                    Toast.makeText(this@SignInActivity, "User tidak ditemukan", Toast.LENGTH_LONG).show()
                } else {
                    // Melakukan validasi password apakah sesuai atau tidak
                    if (user.password.equals(iPassword)) {

                        // Mengirim data ke SharedPreference
                        preferences.setValues(Preferences.USER_USERNAME, user.username.toString())
                        preferences.setValues(Preferences.USER_NAME, user.nama.toString())
                        preferences.setValues(Preferences.USER_EMAIL, user.email.toString())
                        preferences.setValues(Preferences.USER_BALANCE, user.saldo.toString())
                        preferences.setValues(Preferences.USER_URL, user.url.toString())

                        // Mengirim key-value untuk bypass
                        preferences.setValues(Preferences.USER_STATUS, "1")

                        // Pindah Activity
                        val goHomeScreenAct = Intent(this@SignInActivity, HomeScreenActivity::class.java)
                        startActivity(goHomeScreenAct)
                    } else {
                        // Menampilkan Toast jika password tidak sesuai
                        Toast.makeText(this@SignInActivity, "Password Anda salah", Toast.LENGTH_LONG).show()
                    }
                }
            }

            // Method ketika ada suatu error
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignInActivity, databaseError.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
