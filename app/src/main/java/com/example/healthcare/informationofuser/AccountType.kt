package com.example.healthcare.informationofuser

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.healthcare.R
import com.google.android.material.card.MaterialCardView
import kotlin.math.sign

class AccountType : AppCompatActivity() {

    private  var  value1 = false
    private var value2 = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_type)
        getSupportActionBar()?.hide()

        val card1 = findViewById<RelativeLayout>(R.id.docterId)
        val card2 = findViewById<RelativeLayout>(R.id.patientId)
        val btn = findViewById<Button>(R.id.btn)

       card1.setOnClickListener {
           card1.setBackgroundColor(Color.parseColor("#ADD8E6"))
           card2.setBackgroundColor(Color.parseColor("#FFFFFF"))
          value1 = true
           value2 = false
       }

        card2.setOnClickListener {
            card2.setBackgroundColor(Color.parseColor("#ADD8E6"))
            card1.setBackgroundColor(Color.parseColor("#FFFFFF"))
            value2 = true
            value1 = false
        }
        btn.setOnClickListener {
            var type:String = String()
            if (value1 == true){
                    type = "Docter"
            }else
                if(value2 == true){
                   type = "Patient"
                }else {
                    Toast.makeText(applicationContext,"Please Select an Options",Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
            val intent = Intent(this,GenderType::class.java)
            intent.putExtra("Type",type)
            startActivity(intent)
        }
    }
}