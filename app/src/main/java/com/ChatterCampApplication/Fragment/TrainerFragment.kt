package com.ChatterCampApplication.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.ChatterCampApplication.Adapter.RecyclerViewParticipant
import com.ChatterCampApplication.Adapter.RecyclerViewTrainer
import com.ChatterCampApplication.ChatterCampDb
import com.ChatterCampApplication.DataClass.RegisterParticipantDataClass
import com.ChatterCampApplication.DataClass.RegisterTrainerDataClass
import com.ChatterCampApplication.Interface.ClickInterfaceTrainer
import com.ChatterCampApplication.Models.RegisterParticipantModel
import com.ChatterCampApplication.Models.RegisterTrainerModel
import com.ChatterCampApplication.activity.MainActivity
import com.example.chattercampapplication.R
import com.example.chattercampapplication.databinding.FragmentTrainerBinding
import java.util.jar.Manifest

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TrainerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrainerFragment : Fragment(),ClickInterfaceTrainer {
    var binding : FragmentTrainerBinding ?=null
    var mainActivity: MainActivity ?=null
    lateinit var listAdapter : RecyclerViewTrainer
    var registerTrainerList = arrayListOf<RegisterTrainerDataClass>()
    lateinit var registerTrainerDb : ChatterCampDb
    lateinit var registerTrainerModel: RegisterTrainerModel
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity= activity as MainActivity
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
        binding = FragmentTrainerBinding.inflate(layoutInflater)
        listAdapter  = RecyclerViewTrainer(registerTrainerList,this)
        binding?.lvTrainer?.layoutManager = LinearLayoutManager(mainActivity)
        binding?.lvTrainer?.adapter = listAdapter
        registerTrainerDb = ChatterCampDb.getDataBaseRegisterTrainerDb(requireContext())
        getTrainerList()
        mainActivity?.let {
            registerTrainerDb.EventClickInterface().getRegisterTrainerInfo().observe(it){
                registerTrainerList.clear()
                registerTrainerList.addAll(it)
                listAdapter.notifyDataSetChanged()
            }
        }
        return binding?.root
    }

    fun getTrainerList(){
        registerTrainerList.clear()
        listAdapter.notifyDataSetChanged()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.tvBack?.setOnClickListener {
            mainActivity?.navController?.popBackStack()
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TrainerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TrainerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun showDataTrainer(registerTrainerEntity: RegisterTrainerDataClass) {
        var bundle = Bundle()
        bundle.putInt("id",registerTrainerEntity.id)
        mainActivity?.navController?.navigate(R.id.registerTrainerFragment,bundle)
    }
}