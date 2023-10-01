package com.ChatterCampApplication.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ChatterCampApplication.ChatterCampDb
import com.ChatterCampApplication.DataClass.RegisterTrainerDataClass
import kotlinx.coroutines.launch

class RegisterTrainerModel(application : Application) : AndroidViewModel(application) {

    private var registerTrainer : ChatterCampDb
    var list: LiveData<List<RegisterTrainerDataClass>>

    init {
        registerTrainer = ChatterCampDb.getDataBaseRegisterTrainerDb(application)
        list = registerTrainer.EventClickInterface().getRegisterTrainerInfo()
    }

    fun insertRegisterTrainerData(signInEntity: RegisterTrainerDataClass){
        viewModelScope.launch {
            registerTrainer.EventClickInterface().InsertRegisterTrainerInfo(signInEntity)
        }
    }
}