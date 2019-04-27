package com.devarthur.br


import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth


import kotlinx.android.synthetic.main.activity_main.* // Kotlin syntetic reference

//https://www.youtube.com/watch?v=RYyri2W3Tho&list=PL0dzCUj1L5JE-jiBHjxlmXEkQkum_M3R-&index=2


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSignUp.setOnClickListener {

            performRegister()

        }

        btnOpenLogin.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnSelectButton.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)


        }

    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            val bitmapDrawable = BitmapDrawable(bitmap)
            btnSelectButton.setBackgroundDrawable(bitmapDrawable)
        }
    }

    //Creates a new user for firebase auth database. Returns error for invalid usernames or e-mails
    private fun performRegister() {

        val email = edt_useremail.text.toString()
        val password = edt_userPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields for registration", Toast.LENGTH_SHORT).show()

            return
        }

        Toast.makeText(this, "Sending information", Toast.LENGTH_SHORT).show()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                //if successul
                Log.d("Main", "User created with uid: ${it.result.user.uid}")

                uploadImageToFirebase()

            }.addOnFailureListener {
                Log.d("Main", "Failed to create user : ${it.message}")
            }

    }

    //Sends the image selected from the user to our firebase server
    private fun uploadImageToFirebase(){

    }
}
