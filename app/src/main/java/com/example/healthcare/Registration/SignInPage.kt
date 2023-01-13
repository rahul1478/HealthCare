package com.example.healthcare.Registration

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.healthcare.R
import com.example.healthcare.Registration.Data.recordsData
import com.example.healthcare.informationofuser.AccountType
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignInPage : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_page)

        getSupportActionBar()?.hide()

        mAuth = FirebaseAuth.getInstance()

        val username = findViewById<EditText>(R.id.signIn_emailId)
        val password = findViewById<EditText>(R.id.signIn_pass)
        val button = findViewById<Button>(R.id.signIn_btn)
        val btn = findViewById<SignInButton>(R.id.g_signIn)
        val text = findViewById<TextView>(R.id.txt1)
        text.setOnClickListener {
            val intent = Intent(this,SignUpPage::class.java)
            startActivity(intent)
            finish()
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1004599105316-n2k69esqg9mj6d1vfpd6t6kv014egffk.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        btn.setOnClickListener {
            resultLauncher.launch(Intent(googleSignInClient.signInIntent))
        }

        button.setOnClickListener{
            login()
        }

    }

    private fun login() {

        val username = findViewById<EditText>(R.id.signIn_emailId)
        val password = findViewById<EditText>(R.id.signIn_pass)
        if (username.text.isEmpty()){
            username.setError("Please Enter Username")
            return
        }else
            if (password.text.isEmpty()){
                password.setError("Please Enter Password")
                return
            }

        mAuth.signInWithEmailAndPassword(username.text.toString(),password.text.toString())
            .addOnCompleteListener {

                if(it.isSuccessful){
                    Toast.makeText(this,"You are Login",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,AccountType::class.java)
                    startActivity(intent)

                }


            }
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignIn", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SignIn", "Google sign in failed", e)
                }
            }else{
                Log.w("SignIn", exception.toString())
                Toast.makeText(this,"Failed", Toast.LENGTH_LONG).show()
            }
        }

    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignIn", "signInWithCredential:success")
                    Toast.makeText(applicationContext,"YOU ARE SUCCESSFULLY LOGIN", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, AccountType::class.java)
                    startActivity(intent)
                    finish()
                } else {

                    Log.d("SignIn", "signInWithCredential:failure", task.exception)

                }
            }
    }
}