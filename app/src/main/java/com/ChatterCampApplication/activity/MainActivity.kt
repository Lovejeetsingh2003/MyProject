package com.ChatterCampApplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.chattercampapplication.R
import com.example.chattercampapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var binding : ActivityMainBinding ?= null
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        navController = findNavController(R.id.navController2)

        binding?.btmBar?.setOnItemSelectedListener {
            when(it.itemId){
                R.id.btmHome->{
                    navController.navigate(R.id.homeFragment)
                }
                R.id.btmProfile ->{
                    navController.navigate(R.id.profileFragment)
                }
            }
            return@setOnItemSelectedListener true
        }
    }
}