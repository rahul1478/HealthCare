package com.example.healthcare.Registration.Data

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.healthcare.R
import com.google.firebase.firestore.core.View
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_records_data.*


class recordsData : AppCompatActivity() {
    val PDF : Int = 0

    lateinit var uri : Uri
    lateinit var mStorage : StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_records_data)

        val pdfBtn = findViewById<Button>(R.id.pdf)
       pdfBtn.setOnClickListener {
           val intent = Intent()
           intent.type = "application/pdf"
           intent.action = Intent.ACTION_GET_CONTENT
           startActivityForResult(Intent.createChooser(intent, "Select PDF"), PDF)
       }

        mStorage = FirebaseStorage.getInstance().getReference("Uploads")


        val key = findViewById<TextView>(R.id.publick_key)

        val x = intent.getStringExtra("key")
        val y = intent.getStringExtra("keys")

        key.setText("publick key : "+x)
        private_key.setText("private_key :"+y )

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val uriTxt = findViewById<TextView>(R.id.uritext)
        if (resultCode == RESULT_OK) {
            if (requestCode == PDF) {
                uri = data?.data!!
                uriTxt.text = uri.toString()
                upload()

            }
            super.onActivityResult(requestCode, resultCode, data)
        }

    }
    private fun upload() {
        var mReference = mStorage.child(uri.lastPathSegment!!)
        try {
            mReference.putFile(uri).addOnSuccessListener {
                    taskSnapshot: UploadTask.TaskSnapshot? -> var url = taskSnapshot!!.uploadSessionUri
//                val dwnTxt = findViewById<TextView>(R.id.text)
//                dwnTxt.text = url.toString()
                Toast.makeText(this, "Successfully Uploaded :)", Toast.LENGTH_LONG).show()
            }
        }catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }


    }
}