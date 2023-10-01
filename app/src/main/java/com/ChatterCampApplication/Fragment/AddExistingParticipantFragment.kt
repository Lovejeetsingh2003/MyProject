package com.ChatterCampApplication.Fragment

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ChatterCampApplication.Adapter.RecyclerAddExistingParticipant
import com.ChatterCampApplication.Adapter.RecyclerViewParticipant
import com.ChatterCampApplication.ChatterCampDb
import com.ChatterCampApplication.DataClass.RegisterParticipantDataClass
import com.ChatterCampApplication.DataClass.WorkshopList
import com.ChatterCampApplication.Interface.EventClickInterface
import com.ChatterCampApplication.Models.RegisterParticipantModel
import com.ChatterCampApplication.activity.MainActivity
import com.example.chattercampapplication.R
import com.example.chattercampapplication.databinding.FragmentAddExistingParticipantBinding
import com.example.payrollactivity.entity.AttendanceEntity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddExistingParticipantFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddExistingParticipantFragment : Fragment() {
    var binding : FragmentAddExistingParticipantBinding ?= null
    lateinit var mainActivity : MainActivity
    lateinit var listAdapter : RecyclerAddExistingParticipant
    var registerParticipantList = arrayListOf<RegisterParticipantDataClass>()
    lateinit var registerParticipantDb : ChatterCampDb
    lateinit var registerParticipantModel: RegisterParticipantModel
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        mainActivity = activity as MainActivity
        super.onCreate(savedInstanceState)
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
        binding = FragmentAddExistingParticipantBinding.inflate(layoutInflater)
        listAdapter  = RecyclerAddExistingParticipant(registerParticipantList)
        binding?.clExistingParticipant?.layoutManager = LinearLayoutManager(mainActivity)
        binding?.clExistingParticipant?.adapter = listAdapter
        registerParticipantDb = ChatterCampDb.getDatabaseRegisterParticipant(requireContext())
        getParticipantList()
        mainActivity?.let {
            registerParticipantDb.EventClickInterface().getRegisterParticipantInfo().observe(it){
                registerParticipantList.clear()
                registerParticipantList.addAll(it)
                listAdapter.notifyDataSetChanged()
                listAdapter.enabledCheck(true)
            }
        }
        binding?.tvBack?.setOnClickListener {
            mainActivity.navController.popBackStack()
        }
        binding?.btnAddParticipants?.setOnClickListener {
            var list = listAdapter.list as List<RegisterParticipantDataClass>
            var addParticipant = mutableListOf<AttendanceEntity>()
            class MarkAttendance : AsyncTask<Void, Void, Void>() {
                override fun doInBackground(vararg p0: Void?): Void? {
                    getParticipantAttendanceList()
                    return null
                }

                override fun onPostExecute(result: Void?) {
                    super.onPostExecute(result)
                    mainActivity.navController.popBackStack()
                    listAdapter.notifyDataSetChanged()
                }
            }
            MarkAttendance().execute()
        }
        return binding?.root
    }
    fun getParticipantList(){
        registerParticipantList.clear()
        listAdapter.notifyDataSetChanged()
    }

    private fun getParticipantAttendanceList(){
        var filterDate = registerParticipantList.filter { element-> element.isChecked?.equals(true) == true }
        registerParticipantList.clear()
        registerParticipantList.addAll(filterDate as ArrayList<RegisterParticipantDataClass>)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddExistingParticipantFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddExistingParticipantFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}