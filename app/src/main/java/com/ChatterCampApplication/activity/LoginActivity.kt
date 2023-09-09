package com.ChatterCampApplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.chattercampapplication.R
import com.example.chattercampapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    var navController: NavController ?= null
    var binding : ActivityLoginBinding ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        navController = findNavController(R.id.navController)
    }
}