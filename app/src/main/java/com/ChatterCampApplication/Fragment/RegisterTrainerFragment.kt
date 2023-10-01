package com.ChatterCampApplication.Fragment

import android.app.DatePickerDialog
import android.health.connect.datatypes.units.Length
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModelProvider
import com.ChatterCampApplication.ChatterCampDb
import com.ChatterCampApplication.DataClass.RegisterTrainerDataClass
import com.ChatterCampApplication.Models.RegisterTrainerModel
import com.ChatterCampApplication.activity.MainActivity
import com.example.chattercampapplication.R
import com.example.chattercampapplication.databinding.FragmentTrainerRegisterBinding
import java.text.SimpleDateFormat
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterTrainerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterTrainerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var binding: FragmentTrainerRegisterBinding ?= null
    var mainActivity: MainActivity?= null
    var trainerDataClass = RegisterTrainerDataClass()
    var genderList = ArrayList<String>()
    lateinit var chatterCampDb : ChatterCampDb
    lateinit var signInModel: RegisterTrainerModel
    private var trainerId = -1
    var isUpdate = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        chatterCampDb = ChatterCampDb.getDataBaseWorkshopDb(requireContext())
        arguments?.let {
            trainerId = it.getInt("id")
            if(trainerId > -1){
                getEntityInfo()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTrainerRegisterBinding.inflate(layoutInflater)
        genderList.addAll(requireContext().resources.getStringArray(R.array.Gender))
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInModel = ViewModelProvider(this)[RegisterTrainerModel::class.java]

        binding?.tvDate?.setOnClickListener {
            var datePicker = mainActivity?.let { it1 ->
                DatePickerDialog(
                    it1, { _, year, month, date ->
                        var simpleDateFormat = SimpleDateFormat("dd-MMM-yyyy")
                        var calendar = Calendar.getInstance()
                        calendar.set(year, month, date)
                        var selectedDate = simpleDateFormat.format(calendar.time)
                        binding?.tvDate?.text = "$selectedDate"
                    }, Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DATE)
                )
            }
            datePicker?.show()
        }

        binding?.btnCreate?.setOnClickListener {
            var genderIndex = genderList.indexOfFirst { element-> element.equals(trainerDataClass.Gender, true) }
            binding?.spinner1?.setSelection(genderIndex, true)
            var gender = binding?.spinner1?.selectedItem as String
            println("Gender : $gender")
            if(binding?.etEmail?.text.toString().isEmpty())
            {
                binding?.etEmail?.error = "Enter Your Email"
            }
            else if(binding?.etName?.text.toString().isEmpty())
            {
                binding?.etName?.error = "Enter Your Name"
            }
            else if(binding?.etPassword?.text.toString().isEmpty())
            {
                binding?.etPassword?.error = "Enter Your Password"
            }
            else if(binding?.etConfirmPassword?.text.toString().isEmpty())
            {
                binding?.etConfirmPassword?.error = "Confirm Password"
            }
            else{
                if(trainerId > -1)
                {
                    if(!isUpdate) {
                        isUpdate = true
                        binding?.etEmail?.isEnabled = true
                        binding?.etName?.isEnabled = true
                        binding?.tvDate?.isEnabled = true
                        binding?.spinner1?.isEnabled = true
                        binding?.etPassword?.visibility = View.GONE
                        binding?.etConfirmPassword?.visibility = View.GONE
                        binding?.btnCreate?.visibility = View.VISIBLE
                        binding?.btnCreate?.text = "Update"

                    }else {
                        trainerDataClass.id = trainerDataClass.id
                        trainerDataClass.Name = binding?.etName?.text.toString()
                        trainerDataClass.Gender = gender
                        trainerDataClass.Dob = binding?.tvDate?.text.toString()


                        class UpdateApp : AsyncTask<Void, Void, Void>() {

                            override fun doInBackground(vararg params: Void?): Void? {
                                chatterCampDb.EventClickInterface().updateTrainerData(trainerDataClass)
                                Toast.makeText(requireContext(),"Profile Updated",Toast.LENGTH_SHORT).show()
                                return null
                            }

                            override fun onPostExecute(result: Void?) {
                                super.onPostExecute(result)
                                mainActivity?.navController?.popBackStack()
                            }

                        }
                        UpdateApp().execute()
                    }
                }
                else{
                    var trainer =RegisterTrainerDataClass (
                        Name = binding?.etName?.text.toString(),
                        Email = binding?.etEmail?.text.toString(),
                        Dob = binding?.tvDate?.text.toString(),
                        Gender = gender,
                        Password = binding?.etPassword?.text.toString()

                    )
                    class InsertClient : AsyncTask<Void, Void, Void>() {
                        val password = binding?.etPassword?.text.toString()
                        val confirm = binding?.etConfirmPassword?.text.toString()
                        override fun doInBackground(vararg params: Void?): Void? {
                            if (password == confirm) {
                                signInModel.insertRegisterTrainerData(trainer)
//                                Toast.makeText(requireContext(),"Trainer Added",Toast.LENGTH_SHORT).show()
                            }else{
                                binding?.etConfirmPassword?.error = "Confirm Password"
                            }
                            return null
                        }

                        override fun onPostExecute(result: Void?) {
                            super.onPostExecute(result)
                            mainActivity?.navController?.popBackStack()

                        }
                    }
                    InsertClient().execute()
                }
            }
        }
    }

    fun getEntityInfo() {
        class getEntityTrainerInfo : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                trainerDataClass = chatterCampDb.EventClickInterface().getTrainerById(trainerId)
                return null
            }
            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                binding?.etName?.setText(trainerDataClass.Name)
                binding?.etEmail?.setText(trainerDataClass.Email)
                binding?.tvDate?.text = trainerDataClass.Dob
                binding?.etPassword?.visibility = View.GONE
                binding?.etConfirmPassword?.visibility = View.GONE
                binding?.tvCreate?.text = "Trainer Information"
                binding?.btnCreate?.visibility = View.GONE

                binding?.btnDeleteTraienr?.visibility = View.VISIBLE
                binding?.btnDeleteTraienr?.setOnClickListener {
                    DeleteTrainerData()
                }
                var gender = genderList.indexOfFirst { element-> element.equals(trainerDataClass.Gender, true) }
                binding?.spinner1?.setSelection(gender, true)

                binding?.etName?.isEnabled = false
                binding?.etEmail?.isEnabled = false
                binding?.tvDate?.isEnabled = false
                binding?.spinner1?.isEnabled = false

                mainActivity?.binding?.btmBar?.visibility = View.GONE
            }
        }
        getEntityTrainerInfo().execute()

    }


    fun DeleteTrainerData() {
        android.app.AlertDialog.Builder(mainActivity)
            .setTitle(mainActivity?.resources?.getString(R.string.delete))
            .setMessage(mainActivity?.resources?.getString(R.string.do_you_want_to_delete))
            .setPositiveButton(mainActivity?.resources?.getString(R.string.yes)) { _, _ ->
                class DeleteEntityClass : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg params: Void?): Void? {
                        trainerDataClass = chatterCampDb.EventClickInterface().getTrainerById(trainerId)
                        chatterCampDb.EventClickInterface().DeleteTrainerData(trainerDataClass)
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
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterTrainerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}