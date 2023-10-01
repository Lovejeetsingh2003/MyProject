package com.ChatterCampApplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ChatterCampApplication.DataClass.WorkshopList
import com.ChatterCampApplication.Interface.ClickInterfaceHome
import com.example.chattercampapplication.R

class RecyclerViewWorkshop(var list: ArrayList<WorkshopList>, var eventClickInterface: ClickInterfaceHome) : RecyclerView.Adapter<RecyclerViewWorkshop.ViewHolder>() {
    class ViewHolder(var view : View) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById<TextView>(R.id.tvWorkshopName)
        var subject = view.findViewById<TextView>(R.id.tvWorkshopSubject)
        var start_Time = view.findViewById<TextView>(R.id.tvStartTimeList)
        var end_Time = view.findViewById<TextView>(R.id.tvEndTimeList)
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
         holder.start_Time.text = "${list[position].start_Time}"
         holder.end_Time.text = "${list[position].end_Time}"
         holder.itemView.setOnClickListener {
             eventClickInterface.showData(list[position])
         }

     }
 }