package com.ChatterCampApplication.Adapter

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ChatterCampApplication.DataClass.RegisterParticipantDataClass
import com.example.chattercampapplication.R
import com.example.payrollactivity.entity.AttendanceEntity

class RecyclerAddExistingParticipant(var list: ArrayList<RegisterParticipantDataClass>) : RecyclerView.Adapter<RecyclerAddExistingParticipant.ViewHolder>() {

    var isEnabled = false
    class ViewHolder(var view : View) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById<TextView>(R.id.tvParticipantName)
        var checkBox = view.findViewById<CheckBox>(R.id.checkboxAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.add_exixting_participant_list,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = "${list[position].participantName}"
        holder.checkBox.isChecked = list[position].isChecked ?: false
        holder.checkBox.isEnabled = isEnabled == true
        holder.checkBox.setOnCheckedChangeListener{compoundButton, isChecked->
            list[position].isChecked = isChecked
        }

    }
    fun enabledCheck(isEnabled:Boolean){
        this.isEnabled = isEnabled
        notifyDataSetChanged()
    }
}