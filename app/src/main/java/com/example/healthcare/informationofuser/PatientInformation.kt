package com.example.healthcare.informationofuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.healthcare.Main.HomePage
import com.example.healthcare.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_patient_information.*

class PatientInformation : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private  var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_information)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()



        proceed.setOnClickListener {
            sendData()
        }

    }

    private fun sendData() {

        val Gender = intent.getStringExtra("Gender")
        val Acc = intent.getStringExtra("acc")
        val Age = intent.getStringExtra("age")
        val Name = name.text.trim().toString()
        val MobileNumber = mobileNumber.text.trim().toString()
        val Disease = Disease.text.trim().toString()
        val Stage = Stage.text.trim().toString()
        val Mail = mAuth.currentUser?.email

        if (Name.isNotEmpty() && MobileNumber.isNotEmpty() && Disease.isNotEmpty() && Stage.isNotEmpty()){
            var model = DataModel(Name,Mail!!,Age!!,Gender!!,Acc!!,MobileNumber, Disease, Stage)

            db.collection("PatientRecord").add(model)
                .addOnSuccessListener(object : OnSuccessListener<DocumentReference>{
                    override fun onSuccess(p0: DocumentReference?) {
                        Toast.makeText(applicationContext,"Data Saved",Toast.LENGTH_LONG).show()
                        val intent = Intent(this@PatientInformation,HomePage::class.java)
                        startActivity(intent)
                        finish()
                    }

                }).addOnFailureListener {
                    Toast.makeText(applicationContext,"Failed",Toast.LENGTH_LONG).show()
                }

        }



    }
}