package com.example.healthcare.informationofuser

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.withStyledAttributes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare.Main.HomePage
import com.example.healthcare.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_age_number.*
import kotlinx.android.synthetic.main.activity_gender_type.*
import kotlinx.android.synthetic.main.numbercards.*

class AgeNumber : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age_number)

        val Age = findViewById<EditText>(R.id.age)
        val btn = findViewById<Button>(R.id.sendInformation)

        btn.setOnClickListener {
            val Gender = intent.getStringExtra("Gender")
            val Acc = intent.getStringExtra("acc")
            val age = Age.text.trim().toString()

            val intent = Intent(this,PatientInformation::class.java)
            intent.putExtra("Gender",Gender)
            intent.putExtra("acc",Acc)
            intent.putExtra("age",age)
            startActivity(intent)
            finish()


        }
    }




}