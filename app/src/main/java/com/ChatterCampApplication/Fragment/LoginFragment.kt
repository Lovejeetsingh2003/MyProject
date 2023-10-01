package com.ChatterCampApplication.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.BuildCompat
import androidx.fragment.app.Fragment
import com.ChatterCampApplication.ChatterCampDb
import com.ChatterCampApplication.DataClass.RegisterTrainerDataClass
import com.ChatterCampApplication.activity.LoginActivity
import com.ChatterCampApplication.activity.MainActivity
import com.example.chattercampapplication.databinding.FragmentLoginBinding
import pl.droidsonroids.gif.BuildConfig

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
     var signInDataClass = RegisterTrainerDataClass()
    lateinit var chatterCampDb : ChatterCampDb


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginActivity = activity as LoginActivity
        chatterCampDb=ChatterCampDb.getDataBaseRegisterTrainerDb(loginActivity!!)
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
       // chatterCampDb = ChatterCampDb.getDataBaseRegisterTrainerDb(requireContext())
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // if(BuildConfig.DEBUG){
            binding?.etUsername?.setText("Admin")
            binding?.etPassword?.setText("1234")
        //}
        binding?.btnLogin?.setOnClickListener {
            if(binding?.etUsername?.text.toString().isEmpty())
            {
                binding?.etUsername?.error = "Enter your Username"
            }
            else if(binding?.etPassword?.text.toString().isEmpty())
            {
                binding?.etPassword?.error = "Enter your Password"
            }
            else {
                var username = binding?.etUsername?.text.toString()
                var password = binding?.etPassword?.text.toString()
                    if (username == "Admin" && password == "1234") {
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.putExtra("username",binding?.etUsername?.text?.toString())
                        startActivity(intent)
                        Toast.makeText(requireContext(), "Welcome", Toast.LENGTH_SHORT).show()
                        binding?.etUsername?.text?.clear()
                        binding?.etPassword?.text?.clear()
                    } else  {
                        class LoginClass :AsyncTask<Void,Void,Void>(){
                            override fun onPreExecute() {
                                super.onPreExecute()
                                binding?.llProgress?.visibility = View.VISIBLE
                            }
                            override fun doInBackground(vararg p0: Void?): Void? {
                                signInDataClass =
                                    chatterCampDb.EventClickInterface().loginTrainer(username,password)?:RegisterTrainerDataClass()
                                return null
                            }

                            override fun onPostExecute(result: Void?) {
                                super.onPostExecute(result)
                                binding?.llProgress?.visibility = View.GONE
                                if(signInDataClass.id != 0){
                                    val intent = Intent(requireContext(), MainActivity::class.java)
                                    intent.putExtra("username",binding?.etUsername?.text?.toString())
                                    startActivity(intent)
                                    Toast.makeText(requireContext(), "Welcome", Toast.LENGTH_SHORT).show()
                                    binding?.etUsername?.text?.clear()
                                    binding?.etPassword?.text?.clear()

                                }else{
                                    Toast.makeText(
                                        requireContext(),
                                        "Username and Password are wrong",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                        //apne yeh code hi nahi run kiya tha
                        //isliye yeh null tha
                        LoginClass().execute()

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