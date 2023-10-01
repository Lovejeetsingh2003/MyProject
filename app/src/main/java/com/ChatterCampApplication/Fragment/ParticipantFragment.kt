package com.ChatterCampApplication.Fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.ChatterCampApplication.Adapter.RecyclerAddExistingParticipant
import com.ChatterCampApplication.Adapter.RecyclerViewParticipant
import com.ChatterCampApplication.ChatterCampDb
import com.ChatterCampApplication.DataClass.RegisterParticipantDataClass
import com.ChatterCampApplication.Interface.ClickInterfaceParticipant
import com.ChatterCampApplication.Models.RegisterParticipantModel
import com.ChatterCampApplication.activity.MainActivity
import com.example.chattercampapplication.R
import com.example.chattercampapplication.databinding.FragmentParticipantBinding
import com.example.payrollactivity.entity.AttendanceEntity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ParticipantFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ParticipantFragment : Fragment(), ClickInterfaceParticipant {
    var binding : FragmentParticipantBinding ?= null
    var mainActivity: MainActivity ?= null
    lateinit var chatterCampDb: ChatterCampDb
    lateinit var recyclerAddExistingParticipant:  RecyclerAddExistingParticipant
    var registerParticipantDataClass = ArrayList<RegisterParticipantDataClass>()

        // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentParticipantBinding.inflate(layoutInflater)
        return binding?.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnAddParticipants?.setOnClickListener {
            AlertDialog.Builder(mainActivity?:requireContext()).setTitle("Add Participants")
                .setMessage("You Want To Add Participants?")
                .setCancelable(false)
                .setPositiveButton("Add Existing Participants"){_,_->
                    mainActivity?.navController?.navigate(R.id.addExistingParticipantFragment)
                }
                .setNegativeButton("Add New Participants"){_,_->
                    mainActivity?.navController?.navigate(R.id.registerParticipantFragment)
                }
                .setNeutralButton("Cancel"){_,_->

                }
                .show()
        }
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
         * @return A new instance of fragment ParticipantFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ParticipantFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun showDataParticipant(registerParticipantEntity: RegisterParticipantDataClass) {
        var bundle = Bundle()
        bundle.putInt("id",registerParticipantEntity.participantId)
        mainActivity?.navController?.navigate(R.id.participantFragment,bundle)
    }
}