@file:Suppress("UNREACHABLE_CODE")

package com.ChatterCampApplication.Fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.ChatterCampApplication.DataClass.WorkshopList
import com.ChatterCampApplication.Models.WorkshopModel
import com.ChatterCampApplication.activity.MainActivity
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
    lateinit var workshopModel: WorkshopModel
    lateinit var navController: NavController
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWorkshopBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workshopModel = ViewModelProvider(this)[WorkshopModel::class.java]


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

        binding?.tvStartDate?.setOnClickListener {
            var datePicker = mainActivity?.let { it1 ->
                DatePickerDialog(
                    it1, { _, year, month, date ->
                        var simpleDateFormat = SimpleDateFormat("dd-MMM-yyyy")
                        var calendar = Calendar.getInstance()
                        calendar.set(year, month, date)
                        var selectedDate = simpleDateFormat.format(calendar.time)
                        binding?.tvStartDate?.text = "$selectedDate"
                    }, Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DATE)
                )
            }
            datePicker?.show()
        }

        binding?.tvEndDate?.setOnClickListener {
            var datePicker = mainActivity?.let { it1 ->
                DatePickerDialog(
                    it1, { _, year, month, date ->
                        var simpleDateFormat = SimpleDateFormat("dd-MMM-yyyy")
                        var calendar = Calendar.getInstance()
                        calendar.set(year, month, date)
                        var selectedDate = simpleDateFormat.format(calendar.time)
                        binding?.tvEndDate?.text = "$selectedDate"
                    }, Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DATE)
                )
            }
            datePicker?.show()
        }

        binding?.btnSaveWorkshop?.setOnClickListener {

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
            }
            else{
                workshopDataClass.name = binding?.etWorkshopName?.text.toString()
                workshopDataClass.subject = binding?.etSub?.text.toString()
                workshopDataClass.description = binding?.etDesc?.text.toString()
                workshopDataClass.end_Date = binding?.tvEndDate?.text.toString()
                workshopDataClass.start_Date = binding?.tvStartDate?.text.toString()
                workshopDataClass.start_Time = binding?.tvStartTime?.text.toString()
                workshopDataClass.end_Time = binding?.tvEndTime?.text.toString()
                workshopModel.insertWorkshopData(workshopDataClass)
                mainActivity?.navController?.popBackStack()
            }
        }
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