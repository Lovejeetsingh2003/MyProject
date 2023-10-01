package com.ChatterCampApplication.Fragment

import android.app.DatePickerDialog
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ChatterCampApplication.ChatterCampDb
import com.ChatterCampApplication.DataClass.RegisterParticipantDataClass
import com.ChatterCampApplication.DataClass.RegisterTrainerDataClass
import com.ChatterCampApplication.Models.RegisterParticipantModel
import com.ChatterCampApplication.Models.RegisterTrainerModel
import com.ChatterCampApplication.activity.MainActivity
import com.example.chattercampapplication.R
import com.example.chattercampapplication.databinding.FragmentRegisterParticipantBinding
import com.example.chattercampapplication.databinding.FragmentTrainerRegisterBinding
import java.text.SimpleDateFormat
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterParticipantFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterParticipantFragment : Fragment() {

    var binding: FragmentRegisterParticipantBinding ?=null
    var mainActivity: MainActivity?= null
    var participantDataClass = RegisterParticipantDataClass()
    var genderList = ArrayList<String>()
    lateinit var chatterCampDb : ChatterCampDb
    lateinit var registerParticipantModel: RegisterParticipantModel
    private var participantId = -1
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        chatterCampDb = ChatterCampDb.getDataBaseWorkshopDb(requireContext())
        arguments?.let {
            participantId = it.getInt("id")
            if(participantId > -1){
                getEntityParticipantInfo()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterParticipantBinding.inflate(layoutInflater)
        genderList.addAll(requireContext().resources.getStringArray(R.array.Gender))
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerParticipantModel = ViewModelProvider(this)[RegisterParticipantModel::class.java]

        binding?.tvParticipantDate?.setOnClickListener {
            var datePicker = mainActivity?.let { it1 ->
                DatePickerDialog(
                    it1, { _, year, month, date ->
                        var simpleDateFormat = SimpleDateFormat("dd-MMM-yyyy")
                        var calendar = Calendar.getInstance()
                        calendar.set(year, month, date)
                        var selectedDate = simpleDateFormat.format(calendar.time)
                        binding?.tvParticipantDate?.text = "$selectedDate"
                    }, Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DATE)
                )
            }
            datePicker?.show()
        }

        binding?.btnCreate?.setOnClickListener {
            var genderIndex = genderList.indexOfFirst { element-> element.equals(participantDataClass.participantGender, true) }
            binding?.spinner1?.setSelection(genderIndex, true)
            var gender = binding?.spinner1?.selectedItem as String
            if(binding?.etParticipantEmail?.text.toString().isEmpty())
            {
                binding?.etParticipantName?.error = "Enter Your Email"
            }
            else if(binding?.etParticipantName?.text.toString().isEmpty())
            {
                binding?.etParticipantEmail?.error = "Enter Your Name"
            }
            else{
                    mainActivity?.navController?.popBackStack()
                participantDataClass.participantEmail = binding?.etParticipantEmail?.text.toString()
                participantDataClass.participantName = binding?.etParticipantName?.text.toString()
                participantDataClass.participantDob = binding?.tvParticipantDate?.text.toString()
                participantDataClass.participantGender = gender
                registerParticipantModel.insertRegisterParticipantData(participantDataClass)
                Toast.makeText(requireContext(),"Participant Created", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun getEntityParticipantInfo() {
        class getEntityParticipantInfo : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                participantDataClass = chatterCampDb.EventClickInterface().getParticipantById(participantId)
                return null
            }
            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                binding?.etParticipantName?.setText(participantDataClass.participantName)
                binding?.etParticipantEmail?.setText(participantDataClass.participantEmail)
                binding?.tvDob?.text = participantDataClass.participantDob
                binding?.btnCreate?.text = "Participant Delete"
                binding?.tvCreate?.text = "Participant Information"
                var gender = genderList.indexOfFirst { element-> element.equals(participantDataClass.participantGender, true) }
                binding?.spinner1?.setSelection(gender, true)

                binding?.btnCreate?.setOnClickListener {
                    DeleteTrainerData()
                }
                binding?.etParticipantName?.isEnabled = false
                binding?.etParticipantEmail?.isEnabled = false
                binding?.tvDob?.isEnabled = false
                binding?.spinner1?.isEnabled = false

                mainActivity?.binding?.btmBar?.visibility = View.GONE
            }
        }
        getEntityParticipantInfo().execute()
    }

    fun DeleteTrainerData() {
        android.app.AlertDialog.Builder(mainActivity)
            .setTitle(mainActivity?.resources?.getString(R.string.delete))
            .setMessage(mainActivity?.resources?.getString(R.string.do_you_want_to_delete))
            .setPositiveButton(mainActivity?.resources?.getString(R.string.yes)) { _, _ ->
                class DeleteEntityClass : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg params: Void?): Void? {
                        participantDataClass = chatterCampDb.EventClickInterface().getParticipantById(participantId)
                        chatterCampDb.EventClickInterface().DeleteParticipantData(participantDataClass)
                        return null
                    }

                    override fun onPostExecute(result: Void?) {
                        super.onPostExecute(result)
                        mainActivity?.navController?.popBackStack()
                    }
                }
                DeleteEntityClass().execute()
            }
            .setNegativeButton(mainActivity?.resources?.getString(R.string.no)){ _, _->

            }
            .show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterParticipantFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterParticipantFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}