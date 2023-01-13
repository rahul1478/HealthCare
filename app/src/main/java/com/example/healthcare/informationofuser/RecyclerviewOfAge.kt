package com.example.healthcare.informationofuser

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare.R
import com.google.firebase.firestore.DocumentSnapshot
import java.util.zip.Inflater

class RecyclerviewOfAge(val numbers:ArrayList<String>,var mlistner:OnDataItemClickListners):RecyclerView.Adapter<MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.numbercards,parent,false)
        return MyViewHolder(view,mlistner)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.text.text = numbers[position]
    }

    override fun getItemCount(): Int {
        return numbers.size
    }

    interface OnDataItemClickListners{

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListner(listners: OnDataItemClickListners){
        this.mlistner = listners
    }

}

class MyViewHolder(itemView: View,listners: RecyclerviewOfAge.OnDataItemClickListners):RecyclerView.ViewHolder(itemView) {

    val text = itemView.findViewById<TextView>(R.id.AgeNumber)

    init {
        listners.onItemClick(adapterPosition)
    }


}
