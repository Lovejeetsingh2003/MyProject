package com.ChatterCampApplication.activity

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.ChatterCampApplication.ChatterCampDb
import com.ChatterCampApplication.DataClass.RegisterTrainerDataClass
import com.ChatterCampApplication.DataClass.WorkshopList
import com.example.chattercampapplication.R
import com.example.chattercampapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var binding : ActivityMainBinding ?= null
    var user : Int ?= null
    var username : String ?= null
    lateinit var trainerdata : ChatterCampDb
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        navController = findNavController(R.id.navController2)
        trainerdata = ChatterCampDb.getDataBaseWorkshopDb(this)

        username = intent.getStringExtra("username").toString()
        println("username $username")
        if(username == "Admin") {
            user = 1
        } else {
            user = 0
        }

//        class getTrainerData : AsyncTask<Void, Void, Void>() {
//            override fun doInBackground(vararg params: Void?): Void? {
//                if(user == 0){
//
//                }
//                return null
//            }
//
//            override fun onPostExecute(result: Void?) {
//                super.onPostExecute(result)
//            }
//        }
//        getTrainerData().execute()
        binding?.btmBar?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.btmHome -> {
                    navController.navigate(R.id.homeFragment)
                }

                R.id.btmProfile -> {
                    navController.navigate(R.id.profileFragment)
                }
            }
            return@setOnItemSelectedListener true
        }
    }
}