package com.ChatterCampApplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ChatterCampApplication.DataClass.RegisterParticipantDataClass
import com.ChatterCampApplication.DataClass.WorkshopList
import com.ChatterCampApplication.Interface.ClickInterfaceParticipant
import com.example.chattercampapplication.R

class RecyclerViewParticipant (var list: ArrayList<RegisterParticipantDataClass>, var clickInterfaceParticipant: ClickInterfaceParticipant) : RecyclerView.Adapter<RecyclerViewParticipant.ViewHolder>() {
    class ViewHolder(var view : View) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById<TextView>(R.id.tvParticipantName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.participant_list,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.name.text = "${list[position].participantName}"
        holder.itemView.setOnClickListener {
            clickInterfaceParticipant.showDataParticipant(list[position])
        }
    }
}