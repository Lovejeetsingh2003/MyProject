package com.ChatterCampApplication.Interface

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ChatterCampApplication.DataClass.LoginDataClass
import com.ChatterCampApplication.DataClass.SignInDataClass
import com.ChatterCampApplication.DataClass.WorkshopList

@Dao
interface EventClickInterface{
    @Insert
    suspend fun InsertLoginInfo(LoginEntity: LoginDataClass)

    @Query("SELECT * FROM LoginDataClass")
    fun getLoginInfo() : LiveData<List<LoginDataClass>>

    @Insert
    suspend fun InsertSignInInfo(signInEntity: SignInDataClass)

    @Query("SELECT * FROM SignInDataClass")
    fun getSignInInfo() : LiveData<List<SignInDataClass>>

    @Insert
    suspend fun InsertWorkshopInfo(workshopEntity: WorkshopList)

    @Query("SELECT * FROM WorkshopList")
    fun getWorkshopInfo() : LiveData<List<WorkshopList>>
}