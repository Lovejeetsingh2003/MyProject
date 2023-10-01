package com.ChatterCampApplication.DataClass

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class MealInfo(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo()
    var day : String ?= null,
//    @ColumnInfo()
//    var breakfastVeg : String ?= null,
//    @ColumnInfo()
//    var breakfastNonVeg : String ?= null,
//    @ColumnInfo()
//    var lunchVeg : String ?= null,
//    @ColumnInfo()
//    var lunchNonVeg : String ?= null,
//    @ColumnInfo()
//    var dinnerVeg : String ?= null,
//    @ColumnInfo()
//    var dinnerNonVeg : String ?= null
    )
