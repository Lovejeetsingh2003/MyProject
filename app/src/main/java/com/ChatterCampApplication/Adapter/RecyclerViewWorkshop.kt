package com.ChatterCampApplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ChatterCampApplication.DataClass.WorkshopList
import com.ChatterCampApplication.Interface.ClickInterface
import com.example.chattercampapplication.R

class RecyclerViewWorkshop(var list: ArrayList<WorkshopList>, var clickInterface: ClickInterface) : RecyclerView.Adapter<RecyclerViewWorkshop.ViewHolder>() {
    class ViewHolder(var view : View) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById<TextView>(R.id.tvWorkshopName)
        var subject = view.findViewById<TextView>(R.id.tvSubject)
    }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         var view = LayoutInflater.from(parent.context).inflate(R.layout.workshop_list,parent,false)
         return ViewHolder(view)
     }

     override fun getItemCount(): Int {
         return list.size
     }

     override fun onBindViewHolder(holder: ViewHolder, position: Int) {

         holder.name.text = "${list[position].name}"
         holder.subject.text = "${list[position].subject}"
         holder.itemView.setOnClickListener {
             clickInterface.showData(list[position])
         }
     }
 }