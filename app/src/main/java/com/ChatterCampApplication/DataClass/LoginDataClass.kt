package com.ChatterCampApplication.DataClass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoginDataClass(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo()
    var Username : String ?= null,

    @ColumnInfo()
    var Password : String ?= null
)
