package com.ChatterCampApplication.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ChatterCampApplication.ChatterCampDb
import com.ChatterCampApplication.DataClass.SignInDataClass
import kotlinx.coroutines.launch

class SignInModel(application : Application) : AndroidViewModel(application) {

    private var signInDb : ChatterCampDb
    var list: LiveData<List<SignInDataClass>>

    init {
        signInDb = ChatterCampDb.getDataBaseSignIn(application)
        list = signInDb.EventClickInterface().getSignInInfo()
    }

    fun insertSignInData(signInEntity: SignInDataClass){
        viewModelScope.launch {
            signInDb.EventClickInterface().InsertSignInInfo(signInEntity)
        }
    }
}