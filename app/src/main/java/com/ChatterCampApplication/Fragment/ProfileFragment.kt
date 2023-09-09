package com.ChatterCampApplication.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.ChatterCampApplication.activity.MainActivity
import com.example.chattercampapplication.R
import com.example.chattercampapplication.databinding.FragmentProfileBinding
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    var binding : FragmentProfileBinding ?= null
    var mainActivity : MainActivity?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        mainActivity =activity as MainActivity
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        mainActivity?.binding?.btmBar?.visibility = View.VISIBLE
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.llAddTrainers?.setOnClickListener {
            mainActivity?.navController?.navigate(R.id.registerFragment)
            mainActivity?.binding?.btmBar?.visibility = View.GONE
        }
        binding?.llAddWorkshop?.setOnClickListener {
            mainActivity?.navController?.navigate(R.id.workshopFragment)
            mainActivity?.binding?.btmBar?.visibility = View.GONE
        }
        binding?.llLogOut?.setOnClickListener {
            AlertDialog.Builder(mainActivity?:requireContext()).setTitle("LogOut")
                .setMessage("Are you sure to LogOut?")
                .setCancelable(false)
                .setPositiveButton("LogOut"){_,_->
                 mainActivity?.navController?.navigate(R.id.loginFragment)
                }
                .setNegativeButton("Cancel"){_,_->

                }
                .show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}