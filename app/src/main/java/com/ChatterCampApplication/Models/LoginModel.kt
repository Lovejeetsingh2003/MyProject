package com.ChatterCampApplication.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ChatterCampApplication.ChatterCampDb
import com.ChatterCampApplication.DataClass.LoginDataClass
import kotlinx.coroutines.launch

class LoginModel(application :Application) : AndroidViewModel(application) {

    private var loginDb : ChatterCampDb
    var list: LiveData<List<LoginDataClass>>

    init {
        loginDb = ChatterCampDb.getDatabaseLogin(application)
        list = loginDb.EventClickInterface().getLoginInfo()
    }

    fun insertLoginData(LoginEntity: LoginDataClass){
        viewModelScope.launch {
            loginDb.EventClickInterface().InsertLoginInfo(LoginEntity)
        }
    }
}