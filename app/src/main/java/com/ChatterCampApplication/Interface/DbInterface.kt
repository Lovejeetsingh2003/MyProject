package com.ChatterCampApplication.Interface

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ChatterCampApplication.DataClass.RegisterParticipantDataClass
import com.ChatterCampApplication.DataClass.RegisterTrainerDataClass
import com.ChatterCampApplication.DataClass.WorkshopList

@Dao
interface EventClickInterface{
    @Insert
    suspend fun InsertRegisterTrainerInfo(signInEntity: RegisterTrainerDataClass)

    @Query("SELECT * FROM RegisterTrainerDataClass")
    fun getRegisterTrainerInfo() : LiveData<List<RegisterTrainerDataClass>>

    @Query("Select * From RegisterTrainerDataClass WHERE id = :id")
    fun getTrainerById(id:Int) : RegisterTrainerDataClass


    @Query("Select * From RegisterTrainerDataClass")
    fun getTrainer() : List<RegisterTrainerDataClass>

    @Query("Select * From RegisterTrainerDataClass WHERE Email= :email and Password = :password")
    fun loginTrainer(email: String, password: String) : RegisterTrainerDataClass

    @Insert
    fun InsertWorkshopInfo(workshopEntity: WorkshopList)

    @Query("SELECT * FROM WorkshopList")
    fun getWorkshopInfo() : LiveData<List<WorkshopList>>

    @Query("Select * From WorkshopList WHERE id = :id")
    fun getWorkshopById(id:Int) : WorkshopList

    @Query("SELECT * FROM RegisterTrainerDataClass where  Email = :email")
    fun getTrainerByUsername(email : String) :  RegisterTrainerDataClass

    @Insert
    suspend fun InsertRegisterParticipant(registerParticipantEntity: RegisterParticipantDataClass)

    @Update
    fun updateTrainerData(registerTrainerDataClass: RegisterTrainerDataClass)

    @Query("SELECT * FROM RegisterParticipantDataClass")
    fun getRegisterParticipantInfo() : LiveData<List<RegisterParticipantDataClass>>

    @Delete
    infix  fun DeleteWorkshopData(workshop : WorkshopList)

    @Update
    fun UpdateWorkshopData(workshop: WorkshopList)

    @Delete
    infix  fun DeleteTrainerData(registerTrainerDataClass : RegisterTrainerDataClass)

    @Delete
    infix  fun DeleteParticipantData(registerParticipantEntity : RegisterParticipantDataClass)

    @Query("Select * From RegisterParticipantDataClass WHERE participantId = :id")
    fun getParticipantById(id:Int) : RegisterParticipantDataClass

//    @Insert
//    fun addAttendanceList(attendanceList : List<AttendanceEntity>)

}