package com.example.healthcare.Registration

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.example.healthcare.KeysGeneration.SecurityUtil
import com.example.healthcare.PublicKey
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpPage : AppCompatActivity() {


    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAuth1:FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()

        setContentView(R.layout.activity_sign_up_page)

        mAuth = FirebaseAuth.getInstance()
        mAuth1 = Firebase.auth


        val username = findViewById<EditText>(R.id.signUp_emailId)
        val password = findViewById<EditText>(R.id.signUp_pass)
        val btn = findViewById<SignInButton>(R.id.g_signUp)
        val text = findViewById<TextView>(R.id.text2)
        val button = findViewById<Button>(R.id.signUp_btn)

        button.setOnClickListener {
            register()
        }


        text.setOnClickListener {
            val intent = Intent(this,SignInPage::class.java)
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

    }

    private fun register() {
        val username = findViewById<EditText>(R.id.signUp_emailId)
        val password = findViewById<EditText>(R.id.signUp_pass)

        if (username.text.isEmpty()){
            username.setError("Please Enter Username")
            return
        }else
            if (password.text.isEmpty()){
                password.setError("Please Enter Password")
                return
            }


        mAuth.createUserWithEmailAndPassword(username.toString().trim(),password.toString().trim())
            .addOnCompleteListener {

                val user  = mAuth.currentUser
                user?.sendEmailVerification()
                    ?.addOnCompleteListener {task ->
                        if(task.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "User Registered",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
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
//    @RequiresApi(Build.VERSION_CODES.M)
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
//                    generatekey()
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
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    fun generatekey(){
//
//
//        SecurityUtil.getKeyPair()
//
//
//        val publicKey = SecurityUtil.getPublicKey()
//        Log.wtf("PUBLIC KEY", publicKey)
//
//
//
//        val privateKey = SecurityUtil.getPrivateKey()
//        Log.wtf("PRIVATE KEY", privateKey.toString())
//        Toast.makeText(applicationContext,"$privateKey", Toast.LENGTH_SHORT).show()
//
//        val intent = Intent(this, recordsData::class.java)
//        intent.putExtra("key","$publicKey")
//        intent.putExtra("keys","$privateKey")
//        startActivity(intent)
//
//        val model = PublicKey("$publicKey")
//
//        val db = Firebase.firestore
//
//        db.collection("publickey").add(model)
//            .addOnCompleteListener {
//                Toast.makeText(this,"Key saved",Toast.LENGTH_LONG).show()
//            }
//
//            .addOnFailureListener {
//                Toast.makeText(this,"Authenticiation error",Toast.LENGTH_LONG).show()
//            }
//
//
//    }

}
