package com.ChatterCampApplication.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ChatterCampApplication.Adapter.RecyclerViewParticipant
import com.ChatterCampApplication.Adapter.RecyclerViewTrainer
import com.ChatterCampApplication.ChatterCampDb
import com.ChatterCampApplication.DataClass.RegisterParticipantDataClass
import com.ChatterCampApplication.DataClass.RegisterTrainerDataClass
import com.ChatterCampApplication.Interface.ClickInterfaceParticipant
import com.ChatterCampApplication.activity.MainActivity
import com.example.chattercampapplication.R
import com.example.chattercampapplication.databinding.FragmentShowParticipantBinding
import javax.crypto.ShortBufferException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShowParticipantFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowParticipantFragment : Fragment(),ClickInterfaceParticipant {

    var binding : FragmentShowParticipantBinding ?= null
    var mainActivity: MainActivity ?= null
    lateinit var listAdapter : RecyclerViewParticipant
    var registerParticipantList = arrayListOf<RegisterParticipantDataClass>()
    lateinit var registerParticipantDb : ChatterCampDb

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        mainActivity = activity as  MainActivity
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
        binding = FragmentShowParticipantBinding.inflate(layoutInflater)

        listAdapter  = RecyclerViewParticipant(registerParticipantList,this)
        binding?.lvParticipant?.layoutManager = LinearLayoutManager(mainActivity)
        binding?.lvParticipant?.adapter = listAdapter
        registerParticipantDb = ChatterCampDb.getDatabaseRegisterParticipant(requireContext())
        getParticipantList()
        mainActivity?.let {
            registerParticipantDb.EventClickInterface().getRegisterParticipantInfo().observe(it){
                registerParticipantList.clear()
                registerParticipantList.addAll(it)
                listAdapter.notifyDataSetChanged()
            }
        }
        binding?.tvBack?.setOnClickListener {
            mainActivity?.navController?.popBackStack()
        }
        return binding?.root

    }
    fun getParticipantList(){
        registerParticipantList.clear()
        listAdapter.notifyDataSetChanged()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShowParticipantFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShowParticipantFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun showDataParticipant(registerParticipantEntity: RegisterParticipantDataClass) {
        var bundle = Bundle()
        bundle.putInt("participantId",registerParticipantEntity.participantId)
        mainActivity?.navController?.navigate(R.id.registerParticipantFragment,bundle)
    }
}