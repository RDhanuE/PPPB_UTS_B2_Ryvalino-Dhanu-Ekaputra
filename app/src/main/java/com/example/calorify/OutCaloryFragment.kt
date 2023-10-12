package com.example.calorify

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OutCaloryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OutCaloryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
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
        val view = inflater.inflate(R.layout.fragment_out_calory, container, false)
        val dataOut = Bundle()
        val timePicker = view?.findViewById<TimePicker>(R.id.start_time_timepicker)
        var hour = 0
        var minutes = 0

        timePicker?.setOnTimeChangedListener { timePicker, hourOfDay, minute ->
            hour = hourOfDay
            minutes = minute
        }
        var amPm = "AM"
        if(hour > 12) {
            hour = hour - 12
            amPm = "PM"
        } else {
            amPm = "AM"
        }



        val buttonBack = view?.findViewById<AppCompatButton>(R.id.go_back_button)
        buttonBack?.setOnClickListener{
            activity?.finish()
        }
        val buttonCount = view?.findViewById<AppCompatButton>(R.id.count_button)
        buttonCount?.setOnClickListener {
            val WO = view?.findViewById<EditText>(R.id.input_nama_workout)?.text.toString()
            val duration = view?.findViewById<EditText>(R.id.Duration)?.text.toString()
            val kaloriOut = view?.findViewById<EditText>(R.id.out_kalori)?.text.toString()
            if (WO.isEmpty() || duration.isEmpty() || kaloriOut.isEmpty()){
                Toast.makeText(requireContext(), "Please fill in the form", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("Count", "Pressed")
                dataOut.putString("WONAME", WO)
                dataOut.putString("KALORIOUT", kaloriOut)
                dataOut.putInt("HOURSTART", hour)
                dataOut.putInt("MINUTESTART", minutes)
                dataOut.putString("DURATION", duration)
                dataOut.putString("AMPM", amPm)
                val intent = Intent().apply { putExtras(dataOut) }
                activity?.setResult(1, intent)
                activity?.finish()
            }
        }

        return view
    }

    interface DataCallback {
        fun sendDataOut(data: Bundle)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OutCaloryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OutCaloryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}