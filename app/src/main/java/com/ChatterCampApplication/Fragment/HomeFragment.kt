package com.ChatterCampApplication.Fragment

import android.app.AlertDialog
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ChatterCampApplication.Adapter.RecyclerViewWorkshop
import com.ChatterCampApplication.ChatterCampDb
import com.ChatterCampApplication.DataClass.HomeData
import com.ChatterCampApplication.DataClass.WorkshopList
import com.ChatterCampApplication.Interface.ClickInterfaceHome
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
class HomeFragment : Fragment(), ClickInterfaceHome {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var binding : FragmentHomeBinding ?=null
    var mainActivity: MainActivity?= null
    lateinit var listAdapter : RecyclerViewWorkshop
    var workshopList = arrayListOf<WorkshopList>()
    var shownWorkshopList = arrayListOf<WorkshopList>()
    var homeDataBase = HomeData()
    lateinit var workshopDb : ChatterCampDb
    lateinit var navController: NavController
    var dayList = ArrayList<String>()
    var day : String = ""

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
        dayList.addAll(requireContext().resources.getStringArray(R.array.day))
        mainActivity?.binding?.btmBar?.visibility = View.VISIBLE
        listAdapter  = RecyclerViewWorkshop(shownWorkshopList,this)
        binding?.rvWorkshopList?.layoutManager = LinearLayoutManager(mainActivity)
        binding?.rvWorkshopList?.adapter = listAdapter
        workshopDb = ChatterCampDb.getDataBaseWorkshopDb(requireContext())

        getWorkshopList()
        mainActivity?.let {
            workshopDb.EventClickInterface().getWorkshopInfo().observe(it) {
                workshopList.clear()
                workshopList.addAll(it)
                shownWorkshopList.clear()
                shownWorkshopList.addAll(it)
                listAdapter.notifyDataSetChanged()
            }
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnShow?.setOnClickListener {
            var dayIndex = dayList.indexOfFirst { element-> element.equals(homeDataBase.day, true) }
            binding?.spinnerDay?.setSelection(dayIndex, true)
            day = binding?.spinnerDay?.selectedItem as String
            if(day == "DAY 1"){
                binding?.tvAgendaText?.text = "Camp Kickoff and Orientation\n"
            }else if(day == "DAY 2")
            {
                binding?.tvAgendaText?.text = "Skill Development and Activities\n"
            }else{
                binding?.tvAgendaText?.text = "Camp Culmination and Celebration"
            }
            getWorkshopList()
        }
        binding?.btnMealInfo?.setOnClickListener {
            mainActivity?.navController?.navigate(R.id.mealDataFragment)
            mainActivity?.binding?.btmBar?.visibility = View.GONE
        }

    }
    private fun getWorkshopList(){
        var filterDate = workshopList.filter { element-> element.day.equals(day, ignoreCase = true) }
        shownWorkshopList.clear()
        shownWorkshopList.addAll(filterDate as ArrayList<WorkshopList>)
        listAdapter.notifyDataSetChanged()
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





