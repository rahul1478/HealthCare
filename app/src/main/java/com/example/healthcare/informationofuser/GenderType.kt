package com.example.healthcare.informationofuser

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.healthcare.R

class GenderType : AppCompatActivity() {
    private var value1 = false
    private var value2 = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender_type)

        getSupportActionBar()?.hide()



        val card1 = findViewById<CardView>(R.id.maleID)
        val card2 = findViewById<CardView>(R.id.femaleID)
        val btn = findViewById<Button>(R.id.btn2)

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
                type = "Male"
            }else
                if(value2 == true){
                    type = "Female"
                }else {
                    Toast.makeText(this,"Please Select an Options",Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
            val account = intent.getStringExtra("Type")

            val intent = Intent(this,AgeNumber::class.java)
            intent.putExtra("Gender",type)
            intent.putExtra("acc",account)
            startActivity(intent)
            finish()
        }

    }
}