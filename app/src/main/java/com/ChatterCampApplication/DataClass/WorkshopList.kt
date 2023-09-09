package com.ChatterCampApplication.DataClass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class WorkshopList(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo()
    var name: String ?= null,

    @ColumnInfo()
    var subject: String ?= null,

    @ColumnInfo()
    var start_Time : String ?= null,


    @ColumnInfo()
    var end_Time : String ?= null,

    @ColumnInfo()
    var start_Date : String ?= null,

    @ColumnInfo()
    var end_Date : String ?= null,

    @ColumnInfo()
    var description : String ?= null,
)