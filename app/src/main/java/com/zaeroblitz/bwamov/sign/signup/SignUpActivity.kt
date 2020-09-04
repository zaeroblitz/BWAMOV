package com.zaeroblitz.bwamov.sign.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.database.*
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.model.User
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var sUsername: String
    private lateinit var sPassword: String
    private lateinit var sName: String
    private lateinit var sEmail: String

    private lateinit var mDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Mendapatkan path User dari Firebase
        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = mFirebaseInstance.getReference("User")

        btn_next_sign_up_photo.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_next_sign_up_photo){

            // Mendapatkan inputan data dari EditText
            sUsername = edt_username.text.toString()
            sPassword = edt_password.text.toString()
            sName = edt_name.text.toString()
            sEmail = edt_email.text.toString()

            when {

                // Validasi ketika editText User kosong
                sUsername.isEmpty() -> {
                    edt_username.error = "Silahkan isi username Anda"
                    edt_username.requestFocus()
                }

                // Validasi ketika editText Password kosong
                sPassword.isEmpty() -> {
                    edt_password.error = "Silahkan isi password Anda"
                    edt_password.requestFocus()
                }

                // Validasi ketika editText Nama kosong
                sName.isEmpty() -> {
                    edt_name.error = "Silahkan isi nama Anda"
                    edt_name.requestFocus()
                }

                // Validasi ketika editText Email kosong
                sEmail.isEmpty() -> {
                    edt_email.error = "Silahkan isi email Anda"
                    edt_email.requestFocus()
                }
                else -> {
                    saveUsername(sUsername, sPassword, sName, sEmail)
                }
            }
        }
    }

    private fun saveUsername(sUsername: String, sPassword: String, sName: String, sEmail: String) {

        /*
            Membuat objek dari kelas model User
            dan mengirim(set) data dari editText ke properties yang ada di kelas model User
        */
        val user = User()
        user.username = sUsername
        user.password = sPassword
        user.nama = sName
        user.email = sEmail

        // Melakukan validasi pada salah satu properties (username) jika properties tsb ada
        checkingUser(sUsername, user)
    }

    private fun checkingUser(sUsername: String, data: User) {
        mDatabase.child(sUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // Membuat objek dari kelas model User dan mengirim data dari Firebase ke kelas tsb
                val user = dataSnapshot.getValue(User::class.java)

                /*
                    Melakukan validasi apakah sudah ada data user sebelumnya
                    jika belum makan akan melakukan write(set) data ke Firebase
                 */
                if (user == null) {
                    mDatabase.child(sUsername).setValue(data)

                    val goSignUpPhotoScreenActivity = Intent(this@SignUpActivity, SignUpPhotoScreenActivity::class.java)

                    // Mengirim nama ke SignUpPhotoActivity
                    goSignUpPhotoScreenActivity.putExtra(SignUpPhotoScreenActivity.EXTRA_NAME, data.nama)
                    startActivity(goSignUpPhotoScreenActivity)

                } else {

                    // Jika sudah terdapat data user
                    Toast.makeText(this@SignUpActivity, "User sudah digunakan", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUpActivity, databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}
