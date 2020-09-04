package com.zaeroblitz.bwamov.sign.signup

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.zaeroblitz.bwamov.home.HomeScreenActivity
import com.zaeroblitz.bwamov.R
import com.zaeroblitz.bwamov.model.User
import com.zaeroblitz.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.activity_sign_up_photo_screen.*
import java.util.*

class SignUpPhotoScreenActivity : AppCompatActivity(), View.OnClickListener, PermissionListener{

    companion object{
        const val EXTRA_NAME = "name"
    }

    private val REQUEST_IMAGE_CAPTURE = 1
    private var statusAdd: Boolean = true
    private lateinit var filePath: Uri

    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var preferences: Preferences

    private var user: User? = null
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photo_screen)

        // Kelas Preference digunakan untuk SharedPrefrence
        preferences = Preferences(this)

        // Mendapatkan akses ke Firebase Storage
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference

        // Mendapatkan Path User dari firebase
        mDatabase = FirebaseDatabase.getInstance().getReference("User")

        user = intent.getParcelableExtra(EXTRA_NAME)

        // Mengganti nama sesuai dengan nama di SignUp
        val getName = "Welcome, \n ${intent.getStringExtra(EXTRA_NAME)}"
        tv_hello.text = getName

        btn_add_photo.setOnClickListener(this)
        btn_save_and_continue.setOnClickListener(this)
        btn_upload_later.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_add_photo -> {

                /*
                    Jika btn_add_photo diklik, maka statusAdd dari true -> false
                    btn_save_continue ditampilkan
                    btn_add_photo icon menjadi icon Upload
                    profileImage menjadi image user pic default
                */
                if (statusAdd) {
                    statusAdd = false
                    btn_save_and_continue.visibility = View.INVISIBLE
                    btn_add_photo.setImageResource(R.drawable.btn_upload)
                    profileImage.setImageResource(R.drawable.user_pic)
                } else {

                    /*
                        Karena status add menjadi false
                        maka library imagePicker akan dijalankan
                    */
                    ImagePicker.with(this)
                        .crop()
                        .start()
                    btn_save_and_continue.visibility = View.VISIBLE
                }
            }

            R.id.btn_save_and_continue -> {

                // Melakukan validasi apakah photo dari ImagePicker ada atau tidak
                if (filePath != null){
                    val progressDialog = ProgressDialog(this)
                    progressDialog.setTitle("Uploading...")
                    progressDialog.show()

                    // Membuat folder storage images dan membuat random UUID pada setiap filePath(photo) yang di upload
                    val ref = storageReference.child("images/" + UUID.randomUUID().toString())
                    ref.putFile(filePath)

                        // Jika upload photo berhasil
                        .addOnSuccessListener {
                            progressDialog.dismiss()
                            Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()

                            ref.downloadUrl.addOnSuccessListener {
                                saveToFirebase(it.toString())
                            }

                            finishAffinity()
                            val goHomeScreenActivity = Intent(this@SignUpPhotoScreenActivity, HomeScreenActivity::class.java)
                            startActivity(goHomeScreenActivity)
                        }

                        // Jika upload photo gagal
                        .addOnFailureListener{
                            progressDialog.dismiss()
                            Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
                        }

                        // Menampilkan progress presentasi upload photo
                        .addOnProgressListener {
                            taskSnapshot ->  val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                            progressDialog.setMessage("Upload ${progress.toInt()} %")
                        }
                }

                // Jika tidak ada photo yang di upload, maka btn Simpan dan Lanjutkan akan GONE
                else {
                    btn_save_and_continue.visibility = View.GONE
                }
            }

            // Upload nanti
            R.id.btn_upload_later -> {
                finishAffinity()
                val goHomeScreenActivity = Intent(this@SignUpPhotoScreenActivity, HomeScreenActivity::class.java)
                startActivity(goHomeScreenActivity)
            }
        }
    }

    private fun saveToFirebase(url: String) {

        mDatabase.child(user?.username!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user?.url = url
                mDatabase.child(user?.username!!).setValue(user)

                preferences.setValues(Preferences.USER_NAME, user?.nama.toString())
                preferences.setValues(Preferences.USER_USERNAME, user?.username.toString())
                preferences.setValues(Preferences.USER_EMAIL, user?.username.toString())
                preferences.setValues(Preferences.USER_URL, url)
                preferences.setValues(Preferences.USER_BALANCE, "500000")
                preferences.setValues(Preferences.USER_STATUS, "1")

                finishAffinity()
                val intent = Intent(this@SignUpPhotoScreenActivity, HomeScreenActivity::class.java)
                startActivity(intent)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUpPhotoScreenActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }


    // Permission Storage pada Android
    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
        Toast.makeText(this,"Anda tidak dapat menggunggah photo", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        btn_save_and_continue.visibility = View.GONE
        Toast.makeText(this,"Anda dapat menggunggah photo nanti", Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {}

   /*
        Mendapatkan photo dari ImagePicker
        dan ketika berhasil upload photo
        maka btn_add iconnya dirubah menjadi btn_remove
   */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            RESULT_OK -> {
                statusAdd = true

                filePath = data?.data!!

                Glide.with(this)
                    .load(filePath)
                    .centerCrop()
                    .apply(RequestOptions.circleCropTransform())
                    .into(profileImage)

                btn_save_and_continue.visibility = View.VISIBLE
                btn_add_photo.setImageResource(R.drawable.btn_remove)
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this,ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Task Canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
