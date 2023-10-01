package com.ChatterCampApplication.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.ChatterCampApplication.ChatterCampDb
import com.ChatterCampApplication.DataClass.RegisterTrainerDataClass
import com.ChatterCampApplication.activity.LoginActivity
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
    var trainerDataClass = RegisterTrainerDataClass()
    lateinit var trainerdata : ChatterCampDb
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

        binding = FragmentProfileBinding.inflate(layoutInflater)
        mainActivity?.binding?.btmBar?.visibility = View.VISIBLE
        trainerdata = ChatterCampDb.getDataBaseWorkshopDb(requireContext())
        return binding?.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.tvEmail?.text = mainActivity?.username.toString()

        if(mainActivity?.user == 0){
            binding?.llTrainerList?.visibility = View.GONE
            binding?.llAddWorkshop?.visibility = View.GONE
            binding?.llAddTrainers?.visibility = View.GONE
            binding?.imgProfileAdmin?.visibility = View.GONE
            binding?.imgProfileTrainer?.visibility = View.VISIBLE
            getEntityInfo()
        }else{
            binding?.tvName?.text = "ChatterCamp \nApplication"
            binding?.tvEmail?.text = "Admin"
            binding?.llEditProfile?.visibility =View.GONE
        }
        binding?.llAddTrainers?.setOnClickListener {
            mainActivity?.navController?.navigate(R.id.registerTrainerFragment)
            mainActivity?.binding?.btmBar?.visibility = View.GONE
        }

        binding?.llEditProfile?.setOnClickListener {
            mainActivity?.navController?.navigate(R.id.registerTrainerFragment)
            mainActivity?.binding?.btmBar?.visibility = View.GONE
        }

        binding?.llAddParticipants?.setOnClickListener {
            mainActivity?.navController?.navigate(R.id.registerParticipantFragment)
            mainActivity?.binding?.btmBar?.visibility = View.GONE
        }
        binding?.llAddWorkshop?.setOnClickListener {
            mainActivity?.navController?.navigate(R.id.workshopFragment)
            mainActivity?.binding?.btmBar?.visibility = View.GONE
        }
        binding?.llTrainerList?.setOnClickListener {
            mainActivity?.navController?.navigate(R.id.trainerFragment)
            mainActivity?.binding?.btmBar?.visibility = View.GONE
        }
        binding?.llParticipantList?.setOnClickListener {
            mainActivity?.navController?.navigate(R.id.showParticipantFragment)
            mainActivity?.binding?.btmBar?.visibility = View.GONE
        }
        binding?.llLogOut?.setOnClickListener {
            AlertDialog.Builder(mainActivity?:requireContext()).setTitle("LogOut")
                .setMessage("Are you sure to LogOut?")
                .setCancelable(true)
                .setPositiveButton("LogOut"){_,_->
                    val intent = Intent(mainActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("Cancel"){_,_->

                }
                .show()
        }
    }
    fun getEntityInfo() {
        class getEntityTrainerInfo : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                trainerDataClass = trainerdata.EventClickInterface().getTrainerByUsername(mainActivity?.username!!)
                return null
            }
            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                binding?.tvName?.text = trainerDataClass.Name
                binding?.tvEmail?.text = trainerDataClass.Email
            }
        }
        getEntityTrainerInfo().execute()

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