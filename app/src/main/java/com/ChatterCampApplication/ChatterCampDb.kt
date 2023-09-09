package com.ChatterCampApplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ChatterCampApplication.DataClass.LoginDataClass
import com.ChatterCampApplication.DataClass.SignInDataClass
import com.ChatterCampApplication.DataClass.WorkshopList
import com.ChatterCampApplication.Interface.EventClickInterface
import com.example.chattercampapplication.R

@Database(entities =[WorkshopList::class, LoginDataClass::class, SignInDataClass::class],
    version = 3)
abstract class ChatterCampDb : RoomDatabase() {

    abstract fun EventClickInterface() : EventClickInterface

    companion object{
        private var loginDb : ChatterCampDb? = null
        private var signInDb: ChatterCampDb? = null
        private var workshopDb : ChatterCampDb? = null
        fun getDatabaseLogin(context: Context) : ChatterCampDb {
            if(loginDb == null){
                loginDb = Room.databaseBuilder(context, ChatterCampDb::class.java,
                    context.resources.getString(R.string.app_name)).fallbackToDestructiveMigration().build()
            }
            return loginDb!!
        }

                fun getDataBaseSignIn(context: Context): ChatterCampDb {
            if(signInDb == null)
            {
                signInDb = Room.databaseBuilder(context, ChatterCampDb::class.java,
                    context.resources.getString(R.string.app_name)).fallbackToDestructiveMigration().build()
            }
            return signInDb!!
        }

        fun getDataBaseWorkshopDb(context: Context): ChatterCampDb {
            if(workshopDb == null)
            {
                workshopDb = Room.databaseBuilder(context, ChatterCampDb::class.java,
                    context.resources.getString(R.string.app_name)).fallbackToDestructiveMigration().build()
            }
            return workshopDb!!
        }

    }
}