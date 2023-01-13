package com.example.healthcare.Main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare.DataModel.HospitalsData
import com.example.healthcare.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase

class RecyclerAdapterForHosiptals(
    options: FirestoreRecyclerOptions<HospitalsData>,
    var listener : OnDataItemClickListner
)

    :FirestoreRecyclerAdapter<HospitalsData,RecyclerAdapterForHosiptals.dataViewHolder>(options) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): dataViewHolder {
        val viewHolder = dataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.infohospitals, parent, false)
        )

        viewHolder.itemView.setOnClickListener {
            listener.onItemClick(snapshots.getSnapshot(viewHolder.adapterPosition),viewHolder.adapterPosition)
        }

//        viewHolder.itemView.setOnClickListener {
//            listner.onItemClick(snapshots.getSnapshot(viewHolder.adapterPosition).id)
//        }


        return viewHolder
    }

    override fun onBindViewHolder(holder: dataViewHolder, position: Int, model: HospitalsData) {

        val hospitalName = holder.itemView.findViewById<TextView>(R.id.nameHospital)
        val mail = holder.itemView.findViewById<TextView>(R.id.mail)
        val number = holder.itemView.findViewById<TextView>(R.id.number)
        val address = holder.itemView.findViewById<TextView>(R.id.address)
        val pincode = holder.itemView.findViewById<TextView>(R.id.pincode)



        hospitalName.setText("Hospital Name:" + "  " + model.hospital)
        mail.setText("Mail        :" + "  " + model.mail)
        number.setText("Mobile Number  :"+"  "+model.number)
        address.setText("Address  :"+"  "+model.address)
        pincode.setText("Pincode  :"+"  "+model.pincode)


        val auth = Firebase.auth
        val currentUser = auth.currentUser!!.uid
//        var isclick = model.student.contains(currentUser)



    }



    class dataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



    }
    interface OnDataItemClickListner{
        fun onItemClick(documetSnapshot: DocumentSnapshot,position: Int)

    }

    public fun setOnItemClickListener(listener: OnDataItemClickListner){
        this.listener = listener

    }

}

