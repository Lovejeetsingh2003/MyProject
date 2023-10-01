package com.ChatterCampApplication.DataClass

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class HomeData(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo()
    var agenda : String ?= null,

    @ColumnInfo()
    var day : String ?= null,
)
