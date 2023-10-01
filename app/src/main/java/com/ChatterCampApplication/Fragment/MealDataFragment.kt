package com.ChatterCampApplication.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.ChatterCampApplication.ChatterCampDb
import com.ChatterCampApplication.DataClass.HomeData
import com.ChatterCampApplication.DataClass.MealInfo
import com.ChatterCampApplication.activity.MainActivity
import com.example.chattercampapplication.R
import com.example.chattercampapplication.databinding.FragmentMealDataBinding
import java.time.DayOfWeek

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MealDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealDataFragment : Fragment() {

    var binding : FragmentMealDataBinding ?= null
    var mainActivity : MainActivity ?= null
    var dayList = ArrayList<String>()
    var mealInfo = MealInfo()
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
        binding = FragmentMealDataBinding.inflate(layoutInflater)
        dayList.addAll(requireContext().resources.getStringArray(R.array.day))
        return binding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnShow?.setOnClickListener {
            var dayIndex = dayList.indexOfFirst { element -> element.equals(mealInfo.day, true) }
            binding?.spinnerDay?.setSelection(dayIndex, false)
            var day = binding?.spinnerDay?.selectedItem as String
            if (day == "DAY 1") {
                binding?.tvBreakfastVeg?.text = "Whole Wheat Toast"
                binding?.tvNonVegBreakfast?.text = "Mini Chicken Sausages"
                binding?.tvVegLunch?.text = "Veggie Wrap with Hummus"
                binding?.tvNonVegLunch?.text = "Grilled Chicken Tenders"
                binding?.tvVegDinner?.text = "Vegetable Stir-Fry with Tofu"
                binding?.tvNonVegDinner?.text = "Beef Tacos with Salsa"
                binding?.imgNonVegBreakfast2?.visibility = View.GONE
                binding?.imgNonVegBreakfast3?.visibility = View.GONE
                binding?.imgVegLunch2?.visibility = View.GONE
                binding?.imgVegLunch3?.visibility = View.GONE
                binding?.imgVegDinner2?.visibility = View.GONE
                binding?.imgVegDinner3?.visibility = View.GONE
                binding?.imgVegBreakfast2?.visibility = View.GONE
                binding?.imgVegBreakfast3?.visibility = View.GONE
                binding?.imgNonVegLunch2?.visibility = View.GONE
                binding?.imgNonVegLunch3?.visibility = View.GONE
                binding?.imgNonVegDinner2?.visibility = View.GONE
                binding?.imgNonVegDinner3?.visibility = View.GONE

                binding?.imgVegBreakfast?.visibility = View.VISIBLE
                binding?.imgNonVegBreakfast?.visibility = View.VISIBLE
                binding?.imgVegLunch?.visibility = View.VISIBLE
                binding?.imgNonVegLunch?.visibility = View.VISIBLE
                binding?.imgVegDinner?.visibility = View.VISIBLE
                binding?.imgNonVegDinner?.visibility = View.VISIBLE


            } else if (day == "DAY 2") {
                binding?.tvBreakfastVeg?.text = "Peanut Butter Wheat Bread"
                binding?.tvNonVegBreakfast?.text = "Cheese Breakfast Quesadilla"
                binding?.tvVegLunch?.text = "Macaroni and Cheese"
                binding?.tvNonVegLunch?.text = "Tuna Salad Sandwich"
                binding?.tvVegDinner?.text = "Cheese Stuffed Pasta Shells"
                binding?.tvNonVegDinner?.text = "Grilled Salmon Fillet"
                binding?.imgNonVegBreakfast?.visibility = View.GONE
                binding?.imgNonVegBreakfast3?.visibility = View.GONE
                binding?.imgVegLunch?.visibility = View.GONE
                binding?.imgVegLunch3?.visibility = View.GONE
                binding?.imgVegDinner?.visibility = View.GONE
                binding?.imgVegDinner3?.visibility = View.GONE
                binding?.imgVegBreakfast?.visibility = View.GONE
                binding?.imgVegBreakfast3?.visibility = View.GONE
                binding?.imgNonVegLunch?.visibility = View.GONE
                binding?.imgNonVegLunch3?.visibility = View.GONE
                binding?.imgNonVegDinner?.visibility = View.GONE
                binding?.imgNonVegDinner3?.visibility = View.GONE

                binding?.imgVegBreakfast2?.visibility = View.VISIBLE
                binding?.imgNonVegBreakfast2?.visibility = View.VISIBLE
                binding?.imgVegLunch2?.visibility = View.VISIBLE
                binding?.imgNonVegLunch2?.visibility = View.VISIBLE
                binding?.imgVegDinner2?.visibility = View.VISIBLE
                binding?.imgNonVegDinner2?.visibility = View.VISIBLE
            } else {
                binding?.tvBreakfastVeg?.text = " Greek Yogurt Parfait"
                binding?.tvNonVegBreakfast?.text = "Salsa and Sour Cream"
                binding?.tvVegLunch?.text = "Cheese and Veggie Quesadilla"
                binding?.tvNonVegLunch?.text = "Mini Meatball Sub"
                binding?.tvVegDinner?.text = " Vegetarian Pizza"
                binding?.tvNonVegDinner?.text = "Baked Chicken Tenders"
                binding?.imgNonVegBreakfast2?.visibility = View.GONE
                binding?.imgNonVegBreakfast?.visibility = View.GONE
                binding?.imgVegLunch2?.visibility = View.GONE
                binding?.imgVegLunch?.visibility = View.GONE
                binding?.imgVegDinner2?.visibility = View.GONE
                binding?.imgVegDinner?.visibility = View.GONE
                binding?.imgVegBreakfast2?.visibility = View.GONE
                binding?.imgVegBreakfast?.visibility = View.GONE
                binding?.imgNonVegLunch2?.visibility = View.GONE
                binding?.imgNonVegLunch?.visibility = View.GONE
                binding?.imgNonVegDinner2?.visibility = View.GONE
                binding?.imgNonVegDinner?.visibility = View.GONE

                binding?.imgVegBreakfast3?.visibility = View.VISIBLE
                binding?.imgNonVegBreakfast3?.visibility = View.VISIBLE
                binding?.imgVegLunch3?.visibility = View.VISIBLE
                binding?.imgNonVegLunch3?.visibility = View.VISIBLE
                binding?.imgVegDinner3?.visibility = View.VISIBLE
                binding?.imgNonVegDinner3?.visibility = View.VISIBLE
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MealDataFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}