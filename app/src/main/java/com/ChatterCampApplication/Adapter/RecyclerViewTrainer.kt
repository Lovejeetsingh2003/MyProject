package com.ChatterCampApplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ChatterCampApplication.DataClass.RegisterTrainerDataClass
import com.ChatterCampApplication.Interface.ClickInterfaceParticipant
import com.ChatterCampApplication.Interface.ClickInterfaceTrainer
import com.example.chattercampapplication.R

class RecyclerViewTrainer(var list: ArrayList<RegisterTrainerDataClass>, var clickInterfaceTrainer: ClickInterfaceTrainer) : RecyclerView.Adapter<RecyclerViewTrainer.ViewHolder>() {
    class ViewHolder(var view : View) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById<TextView>(R.id.tvTrainerName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.trainer_list,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = "${list[position].Name}"
        holder.itemView.setOnClickListener {
            clickInterfaceTrainer.showDataTrainer(list[position])
        }
    }
}
