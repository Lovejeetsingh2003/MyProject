package com.ChatterCampApplication.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ChatterCampApplication.DataClass.LoginDataClass
import com.ChatterCampApplication.Models.LoginModel
import com.ChatterCampApplication.activity.LoginActivity
import com.ChatterCampApplication.activity.MainActivity
import com.example.chattercampapplication.databinding.FragmentLoginBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var binding : FragmentLoginBinding ?= null
    var loginActivity: LoginActivity?= null
    var loginDataClass= LoginDataClass()
     lateinit var loginViewModel: LoginModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginActivity = activity as LoginActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = ViewModelProvider(this)[LoginModel::class.java]

        binding?.btnLogin?.setOnClickListener {
            if(binding?.etUsername?.text.toString().isEmpty())
            {
                binding?.etUsername?.error = "Enter your Username"
            }
            else if(binding?.etPassword?.text.toString().isEmpty())
            {
                binding?.etPassword?.error = "Enter your Password"
            }
            else{
                loginDataClass.Username = binding?.etUsername?.text.toString()
                loginDataClass.Password = binding?.etPassword?.text.toString()
                loginViewModel.insertLoginData(loginDataClass)

                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)

                binding?.etUsername?.text?.clear()
                binding?.etPassword?.text?.clear()
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
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}