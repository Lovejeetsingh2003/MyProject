package com.example.payrollactivity.entity

import android.icu.util.Calendar
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ChatterCampApplication.DataClass.RegisterParticipantDataClass
import java.text.SimpleDateFormat

@Entity(foreignKeys = [ForeignKey(entity = RegisterParticipantDataClass::class,
    parentColumns = ["id"],
    childColumns = ["participantId"])])

data class AttendanceEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,

    @ColumnInfo
    var participantId : Int = 0,
)
