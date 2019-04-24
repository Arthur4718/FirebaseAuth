package com.devarthur.br


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnSignIn.setOnClickListener {

            performSignIn()


        }


        btnGobackToLogin.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)


              finish()
        }

    }

    private fun performSignIn() {

        Toast.makeText(this, "Login in...", Toast.LENGTH_SHORT).show()

        val email = edtUserEmail.text.toString()
        val pw = edtUserPassword.text.toString()

        if(email.isEmpty() || pw.isEmpty()){
            Toast.makeText(this, "Please fill both all the fields for login", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pw)
            .addOnCompleteListener {
                if(!it.isSuccessful) return@addOnCompleteListener
                Log.d("Login", "User logged with uid: ${it.result.user.uid}")

            }.addOnFailureListener {
                Log.d("Login", "Failed to log user : ${it.message}")
            }
    }
}
