package com.ChatterCampApplication.Fragment

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ChatterCampApplication.Adapter.RecyclerViewWorkshop
import com.ChatterCampApplication.ChatterCampDb
import com.ChatterCampApplication.DataClass.WorkshopList
import com.ChatterCampApplication.Interface.ClickInterface
import com.ChatterCampApplication.Models.WorkshopModel
import com.ChatterCampApplication.activity.MainActivity
import com.example.chattercampapplication.R
import com.example.chattercampapplication.databinding.FragmentHomeBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), ClickInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var binding : FragmentHomeBinding ?=null
    var mainActivity: MainActivity?= null
    lateinit var listAdapter : RecyclerViewWorkshop
    var workshopList = arrayListOf<WorkshopList>()
    lateinit var workshopDb : ChatterCampDb
    lateinit var workshopModel: WorkshopModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
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
        binding = FragmentHomeBinding.inflate(layoutInflater)
        listAdapter  = RecyclerViewWorkshop(workshopList,this)
        binding?.rvWorkshopList?.layoutManager = LinearLayoutManager(mainActivity)
        binding?.rvWorkshopList?.adapter = listAdapter
        workshopDb = ChatterCampDb.getDataBaseWorkshopDb(requireContext())
        getworkshopList()
        return binding?.root
    }


    fun getworkshopList(){
        workshopList.clear()
        listAdapter.notifyDataSetChanged()
        class getWorkshopInfo : AsyncTask<Void, Void, Void>(){
            override fun onPreExecute() {
                super.onPreExecute()
                binding?.progress?.visibility = View.VISIBLE
            }
            override fun doInBackground(vararg params: Void?): Void? {
                workshopList.addAll(workshopDb.EventClickInterface().getWorkshopInfo())
                return null
            }
            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                binding?.progress?.visibility = View.GONE
            }
        }
        getWorkshopInfo().execute()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun showData(workshopEntity: WorkshopList) {
            var bundle = Bundle()
            bundle.putInt("id",workshopEntity.id)
            mainActivity?.navController?.navigate(R.id.workshopFragment,bundle)
    }
}




