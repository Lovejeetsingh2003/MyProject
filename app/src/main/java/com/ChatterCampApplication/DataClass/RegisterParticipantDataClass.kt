package com.ChatterCampApplication.DataClass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RegisterParticipantDataClass(

    @PrimaryKey(autoGenerate = true)
    var participantId: Int = 0,

    @ColumnInfo()
    var participantAttendance : String ?= null,


    @ColumnInfo()
    var participantName : String ?= null,

    @ColumnInfo()
    var participantEmail : String ?= null,


    @ColumnInfo()
    var participantGender : String ?=null,

    @ColumnInfo()
    var isChecked : Boolean ?=false,

    @ColumnInfo
    var participantDob : String ?=null
)
