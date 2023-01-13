package com.example.healthcare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.healthcare.Registration.SignInPage
import com.example.healthcare.Registration.SignUpPage
import com.example.healthcare.informationofuser.AccountType
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()?.hide()

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        Handler().postDelayed({
            if (user != null){
                val intent = Intent(this, SignInPage::class.java)
                startActivity(intent)
                finish()

            }else{
                val intent1 = Intent(this, SignUpPage::class.java)
                startActivity(intent1)
                finish()

            }

        },1000)
    }




}