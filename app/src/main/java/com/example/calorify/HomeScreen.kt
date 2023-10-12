package com.example.calorify

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.calorify.databinding.ActivityHomeScreenBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeScreen : AppCompatActivity() {
    private lateinit var binding: ActivityHomeScreenBinding

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
        Log.d("Launcher", "Entering launcher")
        val dataInput = result.data?.extras

        with(binding) {
            val Kalori = listKalori
            var calLeft = caloriesLeft.text.toString().toInt()
            if (result.resultCode == 0) {
                Log.d("IN", "IN")
                var calories = dataInput?.getString("KALORIIN")?.toInt()
                calLeft += calories ?: 0
                val inCaloriesTextView = TextView(this@HomeScreen)

                val hourEat = dataInput?.getInt("HOUREAT") ?: 0
                val minuteEat = dataInput?.getInt("MINUTEEAT") ?: 0
                val amPm = dataInput?.getString("AMPM") ?: "AM"

                val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
                val formattedTime = timeFormat.format(Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hourEat)
                    set(Calendar.MINUTE, minuteEat)
                }.time)

                inCaloriesTextView.text = "Makanan = ${dataInput?.getString("FOODNAME")}\n" +
                        "Sesi = ${dataInput?.getString("JENISIN")}\n" +
                        "Pukul $formattedTime\n" +
                        "Kalori IN = ${dataInput?.getString("KALORIIN")}"
                inCaloriesTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16F)
                inCaloriesTextView.setTextColor(resources.getColor(R.color.white))
                inCaloriesTextView.setBackgroundColor(resources.getColor(R.color.blue))
                inCaloriesTextView.setTypeface(null, Typeface.BOLD)
                inCaloriesTextView.setPadding(16, 16, 16, 16)
                inCaloriesTextView.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                Kalori.addView(inCaloriesTextView)
            } else if (result.resultCode == 1) {
                Log.d("OUT", "OUT")
                var calories = dataInput?.getString("KALORIOUT")?.toInt()
                calLeft -= calories ?: 0
                if (calLeft < 0) {
                    calLeft = 0
                }
                val outCaloriesTextView = TextView(this@HomeScreen)

                val hourStart = dataInput?.getInt("HOURSTART") ?: 0
                val minuteStart = dataInput?.getInt("MINUTESTART") ?: 0
                val amPm = dataInput?.getString("AMPM") ?: "AM"

                val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
                val formattedStartTime = timeFormat.format(Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hourStart)
                    set(Calendar.MINUTE, minuteStart)
                }.time)

                outCaloriesTextView.text = "Kegiatan = ${dataInput?.getString("WONAME")}\n" +
                        "Pukul Mulai $formattedStartTime\n" +
                        "Durasi = ${dataInput?.getString("DURATION")} menit\n" +
                        "Kalori OUT = ${dataInput?.getString("KALORIOUT")}"
                outCaloriesTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16F) // Set text size to 16dp
                outCaloriesTextView.setTextColor(resources.getColor(R.color.white))
                outCaloriesTextView.setBackgroundColor(resources.getColor(R.color.darkblue))
                outCaloriesTextView.setTypeface(null, Typeface.BOLD) // Set text to bold
                outCaloriesTextView.setPadding(16, 16, 16, 16) // Set padding to 16dp
                outCaloriesTextView.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                Kalori.addView(outCaloriesTextView)
            }
            caloriesLeft.text = calLeft.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val data = intent.extras

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.lightBlue)

        val calendar = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(calendar)

        with(binding) {
            caloriesLeft.text = data?.getInt("CALORY").toString()
            nama.text = data?.getString("NAME")
            usia.text = data?.getString("AGE")
            tujuan.text = data?.getString("PURPOSE")
            if (data?.getString("BBCURENTM") == "Kg"){
                BBSekarang.text = data?.getInt("BBCURRENT").toString() + "  Kg"
            } else {
                BBSekarang.text = data?.getInt("BBCURRENT").toString() + "  Lb"
            }
            if (data?.getString("BBGOALM") == "Kg"){
                BBTujuan.text = data?.getInt("BBGOAL").toString() + "  Kg"
            } else {
                BBTujuan.text = data?.getInt("BBGOAL").toString() + "  Lb"
            }
            kalori.text = data?.getInt("CALORY").toString()
            dateGoal.text = "${data?.getInt("YEAR")}-${data?.getInt("MONTH")}-${data?.getInt("DATE")}"
            dateNow.text = "$currentDate"

            buttonInputData.setOnClickListener {
                val intent = Intent(this@HomeScreen, InputDataScreen::class.java)
                launcher.launch(intent)
            }
        }
    }
}