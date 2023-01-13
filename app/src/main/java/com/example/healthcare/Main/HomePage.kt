package com.example.healthcare.Main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare.DataModel.HospitalsData
import com.example.healthcare.R
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomePage : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        getSupportActionBar()?.hide()

        val notification = findViewById<ImageView>(R.id.notificationBell)
        val search = findViewById<CardView>(R.id.searchbar)
        val report = findViewById<RelativeLayout>(R.id.patientReport)

        search.setOnClickListener {
            startActivity(Intent(this, HospitalsName::class.java))
        }

        notification.setOnClickListener {
           val intent = Intent(this,NotificationClass::class.java)
            startActivity(intent)
        }

        report.setOnClickListener {
            val intent = Intent(this,PatientRecords::class.java)
            startActivity(intent)
        }

    }

    }

