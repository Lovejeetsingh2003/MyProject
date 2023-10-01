package com.ChatterCampApplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ChatterCampApplication.DataClass.RegisterParticipantDataClass
import com.ChatterCampApplication.DataClass.RegisterTrainerDataClass
import com.ChatterCampApplication.DataClass.WorkshopList
import com.ChatterCampApplication.Interface.EventClickInterface
import com.example.chattercampapplication.R

@Database(entities =[WorkshopList::class, RegisterTrainerDataClass::class,RegisterParticipantDataClass::class],
    version = 2)
abstract class ChatterCampDb : RoomDatabase() {

    abstract fun EventClickInterface() : EventClickInterface

    companion object{
        private var RegisterParticipantDb : ChatterCampDb? = null
        private var RegisterTrainerDb : ChatterCampDb? = null
        private var workshopDb : ChatterCampDb? = null

        fun getDatabaseRegisterParticipant(context: Context) : ChatterCampDb {
            if(RegisterParticipantDb == null){
                RegisterParticipantDb = Room.databaseBuilder(context, ChatterCampDb::class.java,
                    context.resources.getString(R.string.app_name)).fallbackToDestructiveMigration().build()
            }
            return RegisterParticipantDb!!
        }

                fun getDataBaseRegisterTrainerDb(context: Context): ChatterCampDb {
            if(RegisterTrainerDb == null)
            {
                RegisterTrainerDb = Room.databaseBuilder(context, ChatterCampDb::class.java,
                    context.resources.getString(R.string.app_name)).fallbackToDestructiveMigration().build()
            }
            return RegisterTrainerDb!!
        }

        fun getDataBaseWorkshopDb(context: Context): ChatterCampDb {
            if (workshopDb == null) {
                workshopDb = Room.databaseBuilder(
                    context, ChatterCampDb::class.java,
                    context.resources.getString(R.string.app_name)
                ).fallbackToDestructiveMigration().build()
            }
            return workshopDb!!
        }

    }
}