package com.ChatterCampApplication.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ChatterCampApplication.ChatterCampDb
import com.ChatterCampApplication.DataClass.WorkshopList
import kotlinx.coroutines.launch

class WorkshopModel(application : Application) : AndroidViewModel(application) {

    private var workshopDb : ChatterCampDb
    var list: LiveData<List<WorkshopList>>

    init {
        workshopDb = ChatterCampDb.getDataBaseWorkshopDb(application)
        list = workshopDb.EventClickInterface().getWorkshopInfo()
    }

    fun insertWorkshopData(workshopEntity: WorkshopList){
        viewModelScope.launch {
            workshopDb.EventClickInterface().InsertWorkshopInfo(workshopEntity)
        }
    }
}