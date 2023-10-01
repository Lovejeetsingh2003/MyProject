@file:Suppress("UNREACHABLE_CODE")

package com.ChatterCampApplication.Fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.security.identity.DocTypeNotSupportedException
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.ChatterCampApplication.ChatterCampDb
import com.ChatterCampApplication.DataClass.RegisterTrainerDataClass
import com.ChatterCampApplication.DataClass.WorkshopList
import com.ChatterCampApplication.Models.WorkshopModel
import com.ChatterCampApplication.activity.MainActivity
import com.example.chattercampapplication.R
import com.example.chattercampapplication.databinding.FragmentWorkshopBinding
import java.text.SimpleDateFormat
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WorkshopFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WorkshopFragment : Fragment() {
    var binding : FragmentWorkshopBinding ?=null
    var mainActivity : MainActivity?= null
    var workshopDataClass = WorkshopList()
    lateinit var chatterCampDb : ChatterCampDb
    lateinit var workshopModel: WorkshopModel
    lateinit var navController: NavController
    var dayList = ArrayList<String>()
    var isUpdate = false
    var trainerList = ArrayList<RegisterTrainerDataClass>()
    lateinit var trainerListAdapter: ArrayAdapter<RegisterTrainerDataClass>
    private var workshopId = -1

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        chatterCampDb = ChatterCampDb.getDataBaseWorkshopDb(requireContext())
        arguments?.let {
            workshopId = it.getInt("id")
            if(workshopId > -1){
                getEntityInfo()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWorkshopBinding.inflate(layoutInflater)
        dayList.addAll(requireContext().resources.getStringArray(R.array.day))
        getTrainerList()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workshopModel = ViewModelProvider(this)[WorkshopModel::class.java]

        binding?.btnParticipantList?.visibility = View.GONE

        if (mainActivity?.user == 0)
        {
            binding?.tlBar?.visibility = View.GONE
            binding?.btnDelete?.visibility = View.GONE
            binding?.btnSaveWorkshop?.visibility = View.GONE
        }

        binding?.tvStartTime?.setOnClickListener {
            var timePicker = TimePickerDialog(
                mainActivity, { _, hours, minutes ->
                    var simpleTimeFormat = SimpleDateFormat("hh-mm-aa")
                    var calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR, hours)
                    calendar.set(Calendar.MINUTE, minutes)
                    var selectedTime = simpleTimeFormat.format(calendar.time)
                    binding?.tvStartTime?.text = selectedTime
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE), false
            )
            timePicker.show()
        }

        binding?.btnDelete?.setOnClickListener {
            DeleteWorkshopData()
        }
        binding?.tvEndTime?.setOnClickListener {
            var timePicker = TimePickerDialog(
                mainActivity, { _, hours, minutes ->
                    var simpleTimeFormat = SimpleDateFormat("hh-mm-aa")
                    var calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR, hours)
                    calendar.set(Calendar.MINUTE, minutes)
                    var selectedTime = simpleTimeFormat.format(calendar.time)
                    binding?.tvEndTime?.text = selectedTime
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE), false
            )
            timePicker.show()
        }
        binding?.btnParticipantList?.setOnClickListener{
          mainActivity?.navController?.navigate(R.id.participantFragment)
        }

        binding?.btnSaveWorkshop?.setOnClickListener {
            var dayIndex = dayList.indexOfFirst { element-> element.equals(workshopDataClass.day, true) }
            binding?.spinnerDay?.setSelection(dayIndex, true)
            var day = binding?.spinnerDay?.selectedItem as String

            var trainerIndex = trainerList.indexOfFirst { element-> element.equals(workshopDataClass.trainerName) }
            binding?.spinnerTrainer?.setSelection(trainerIndex, true)
            var trainer = binding?.spinnerDay?.selectedItem as String


            if(binding?.etWorkshopName?.text.toString().isEmpty())
            {
                binding?.etWorkshopName?.error = "Enter your Name"
            }
            else if(binding?.etSub?.text.toString().isEmpty())
            {
                binding?.etSub?.error = "Enter Subject"
            }
            else if (binding?.etDesc?.text.toString().isEmpty())
            {
                binding?.etDesc?.error = "Select Description"
            } else {
                if(workshopId > -1)
                {
                    if(!isUpdate) {
                        isUpdate = true
                        binding?.etWorkshopName?.isEnabled = true
                        binding?.etSub?.isEnabled = true
                        binding?.spinnerTrainer?.isEnabled = true
                        binding?.spinnerDay?.isEnabled = true
                        binding?.llTiming?.isEnabled = true
                        binding?.etDesc?.isEnabled =true
                        binding?.btnSaveWorkshop?.text = "Save Workshop"
                        binding?.btnDelete?.visibility =View.GONE
                        binding?.etWorkshopName?.requestFocus()
                    }else {
                        workshopDataClass.id = workshopDataClass.id
                        workshopDataClass.name = binding?.etWorkshopName?.text.toString()
                        workshopDataClass.subject =  binding?.etSub?.text.toString()
                        workshopDataClass.trainerName = trainer
                        workshopDataClass.day = day
                        workshopDataClass.start_Time = binding?.tvStartTime?.text.toString()
                        workshopDataClass.end_Time = binding?.tvEndTime?.text.toString()
                        workshopDataClass.description = binding?.etDesc?.text.toString()


                        class UpdateApp : AsyncTask<Void, Void, Void>() {

                            override fun doInBackground(vararg params: Void?): Void? {
                                chatterCampDb.EventClickInterface().UpdateWorkshopData(workshopDataClass)
                                return null
                            }

                            override fun onPostExecute(result: Void?) {
                                super.onPostExecute(result)
                                mainActivity?.navController?.popBackStack()
                            }

                        }
                        UpdateApp().execute()
                    }
                }else {
                    workshopDataClass.name = binding?.etWorkshopName?.text.toString()
                    workshopDataClass.subject = binding?.etSub?.text.toString()
                    workshopDataClass.trainerName = trainer
                    workshopDataClass.day = day
                    workshopDataClass.start_Time = binding?.tvStartTime?.text.toString()
                    workshopDataClass.end_Time = binding?.tvEndTime?.text.toString()
                    workshopDataClass.description=binding?.etDesc?.text?.toString()

                        var workshop = WorkshopList(
                            name = binding?.etWorkshopName?.text.toString(),
                            day = day,
                            subject = binding?.etSub?.text.toString(),
                            start_Time = binding?.tvStartTime?.text.toString(),
                            end_Time = binding?.tvEndTime?.text?.toString(),
                            description = binding?.etDesc?.text.toString(),
                            trainerName = trainer
                        )

                        class InsertWorkshop : AsyncTask<Void, Void, Void>() {
                            override fun doInBackground(vararg params: Void?): Void? {
                               chatterCampDb.EventClickInterface().InsertWorkshopInfo(workshop)
                                return null
                            }

                            override fun onPostExecute(result: Void?) {
                                super.onPostExecute(result)
                                mainActivity?.navController?.popBackStack()
                            }
                        }
                        InsertWorkshop().execute()
                    }
            }
        }
    }
    fun getTrainerList(){
        trainerList.clear()
        class getTrainerClass : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                trainerList.addAll(chatterCampDb.EventClickInterface().getTrainer())
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                trainerListAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, trainerList)
                binding?.spinnerTrainer?.adapter = trainerListAdapter

            }
        }
        getTrainerClass().execute()
    }

    fun getEntityInfo() {
        class getEntityClassWorkshop : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                workshopDataClass = chatterCampDb.EventClickInterface().getWorkshopById(workshopId)
                return null
            }
            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                binding?.etWorkshopName?.setText(workshopDataClass.name)
                binding?.tvWorkshopName?.text = workshopDataClass.name
                binding?.etSub?.setText(workshopDataClass.subject)
                binding?.tvStartTime?.text = workshopDataClass.start_Time
                binding?.tvEndTime?.text = workshopDataClass.end_Time
                binding?.etDesc?.setText(workshopDataClass.description)
                binding?.btnParticipantList?.visibility = View.VISIBLE
                binding?.btnSaveWorkshop?.text = "Edit Workshop"
                binding?.btnDelete?.visibility = View.VISIBLE
                var day = dayList.indexOfFirst { element-> element.equals(workshopDataClass.day, true) }
                binding?.spinnerDay?.setSelection(day, true)
                var trainerIndex = trainerList.indexOfFirst { element-> element.equals(workshopDataClass.trainerName) }
                binding?.spinnerTrainer?.setSelection(trainerIndex, true)

                binding?.etWorkshopName?.isEnabled = false
                binding?.etDesc?.isEnabled = false
                binding?.etSub?.isEnabled = false
                binding?.spinnerTrainer?.isEnabled = false
                binding?.tvStartTime?.isEnabled = false
                binding?.tvEndTime?.isEnabled = false
                binding?.spinnerDay?.isEnabled = false

                mainActivity?.binding?.btmBar?.visibility = View.GONE
            }
        }
        getEntityClassWorkshop().execute()

    }
    fun DeleteWorkshopData() {
        android.app.AlertDialog.Builder(mainActivity)
            .setTitle(mainActivity?.resources?.getString(R.string.delete))
            .setMessage(mainActivity?.resources?.getString(R.string.do_you_want_to_delete))
            .setPositiveButton(mainActivity?.resources?.getString(R.string.yes)) { _, _ ->
                class DeleteEntityClass : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg params: Void?): Void? {
                        workshopDataClass = chatterCampDb.EventClickInterface().getWorkshopById(workshopId)
                        chatterCampDb.EventClickInterface().DeleteWorkshopData(workshopDataClass)
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
         * @return A new instance of fragment WorkshopFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WorkshopFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}

