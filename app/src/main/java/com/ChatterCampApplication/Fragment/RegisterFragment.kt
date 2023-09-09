package com.ChatterCampApplication.Fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ChatterCampApplication.DataClass.SignInDataClass
import com.ChatterCampApplication.Models.SignInModel
import com.ChatterCampApplication.activity.MainActivity
import com.example.chattercampapplication.databinding.FragmentRegisterBinding
import java.text.SimpleDateFormat
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var binding: FragmentRegisterBinding ?= null
    var mainActivity: MainActivity?= null
    var signInDataClass = SignInDataClass()
    var genderList = ArrayList<String>()
    lateinit var signInModel: SignInModel
    var genderIndex = String()
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
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInModel = ViewModelProvider(this)[SignInModel::class.java]

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
            if(binding?.etEmail?.text.toString().isEmpty())
            {
                binding?.etEmail?.error = "Enter Your Name"
            }
            else if(binding?.etName?.text.toString().isEmpty())
            {
                binding?.etName?.error = "Enter Your Email"
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
                val password = binding?.etPassword?.text.toString()
                val confirm = binding?.etConfirmPassword?.text.toString()
                signInDataClass.Email = binding?.etEmail?.text.toString()
                signInDataClass.Password = binding?.etPassword?.text.toString()
                signInDataClass.Name = binding?.etName?.text.toString()
                signInDataClass.Dob = binding?.tvDate?.text.toString()
                var profession = binding?.RgProfession?.toString()
//                signInDataClass.Profrssion
                var genderIndex = genderList.indexOfFirst { element-> element.equals(signInDataClass.Gender, true) }
                signInDataClass.Gender = binding?.spinner1?.setSelection(genderIndex, true).toString()
                signInModel.insertSignInData(signInDataClass)
                if (password == confirm) {
                    mainActivity?.navController?.popBackStack()
                }
                else{
                    binding?.etConfirmPassword?.error = "Confirm Password"
                }
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
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}