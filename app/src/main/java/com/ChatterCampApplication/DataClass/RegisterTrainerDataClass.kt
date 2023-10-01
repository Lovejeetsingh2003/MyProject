package com.ChatterCampApplication.DataClass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RegisterTrainerDataClass(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo()
    var Name : String ?= null,

    @ColumnInfo()
    var Email : String ?= null,

    @ColumnInfo()
    var Password : String ?= null,

    @ColumnInfo()
    var Gender : String ?=null,

    @ColumnInfo
    var Dob : String ?=null

)
{
    override fun toString(): String {
        return "$Name"
    }
}