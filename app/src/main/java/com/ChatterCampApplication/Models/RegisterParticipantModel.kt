package com.ChatterCampApplication.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ChatterCampApplication.ChatterCampDb
import com.ChatterCampApplication.DataClass.RegisterParticipantDataClass
import com.ChatterCampApplication.DataClass.RegisterTrainerDataClass
import kotlinx.coroutines.launch

class RegisterParticipantModel(application : Application) : AndroidViewModel(application) {

    private var registerParticipant: ChatterCampDb
    var list: LiveData<List<RegisterParticipantDataClass>>

    init {
        registerParticipant = ChatterCampDb.getDatabaseRegisterParticipant(application)
        list = registerParticipant.EventClickInterface().getRegisterParticipantInfo()
    }

    fun insertRegisterParticipantData(registerParticipantEntity: RegisterParticipantDataClass) {
        viewModelScope.launch {
            registerParticipant.EventClickInterface().InsertRegisterParticipant(registerParticipantEntity)
        }
    }
}