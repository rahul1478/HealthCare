package com.example.healthcare.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class HospitalsName : AppCompatActivity(), RecyclerAdapterForHosiptals.OnDataItemClickListner {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var adapter:RecyclerAdapterForHosiptals
    private  var  db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospitals_name)
        setUpRecyclerViewHolder()
    }

    private fun setUpRecyclerViewHolder() {

        val dataCollection: CollectionReference = db.collection( "Hospital Record")
//        val query = dataCollection.orderBy("class",Query.Direction.DESCENDING)
        var recyclerViewOptions = FirestoreRecyclerOptions.Builder<HospitalsData>().setQuery(dataCollection,
            HospitalsData::class.java)
            .setLifecycleOwner(this).build()

        adapter = RecyclerAdapterForHosiptals(recyclerViewOptions,this)
        val rycl = findViewById<RecyclerView>(R.id.recycleForHospitals)



        rycl.adapter = adapter
        rycl.layoutManager = LinearLayoutManager(this)




    }

    override fun onItemClick(documetSnapshot: DocumentSnapshot, position: Int) {

    }


}