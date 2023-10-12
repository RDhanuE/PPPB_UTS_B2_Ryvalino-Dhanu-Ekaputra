package com.example.calorify

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.calorify.databinding.ActivityGetStartedScreenBinding
import java.util.Calendar

class GetStartedScreen : AppCompatActivity() {
    private lateinit var binding: ActivityGetStartedScreenBinding
    private var year : Int = 0
    private var month : Int = 0
    private var date : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGetStartedScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val purpose = resources.getStringArray(R.array.tujuan)
        val weight = resources.getStringArray(R.array.weight)
        setContentView(binding.root)

        val adapterTujuan = ArrayAdapter(this, android.R.layout.simple_spinner_item, purpose)
        adapterTujuan.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)

        val adapterBBSekarang = ArrayAdapter(this, android.R.layout.simple_spinner_item, weight)
        adapterBBSekarang.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)

        val adapterBBTujuan = ArrayAdapter(this, android.R.layout.simple_spinner_item, weight)
        adapterBBTujuan.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)


        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.lightBlue)

        with(binding){
            spinnerTujuan.adapter = adapterTujuan
            spinnerBBSekarang.adapter = adapterBBSekarang
            spinnerBBTujuan.adapter = adapterBBTujuan


            val calenderView = calender
            val data = Bundle()

            var years = 2020
            var months = 1
            var dates = 1

            calenderView.setOnDateChangeListener { view, year, month, dayOfMonth ->
                years = year
                months = month
                dates = dayOfMonth

            }


            homeButton.setOnClickListener{
                if (inputNama.text.isEmpty() || textInputBBGoals.text.isEmpty() || textInputBBSekarang.text.isEmpty() || inputKalori.text.isEmpty()){
                    Toast.makeText(this@GetStartedScreen, "Please fill in the form to continue", Toast.LENGTH_SHORT).show()
                }
                else {
                    val nama = inputNama.text.toString()
                    val usia = inputUsia.text.toString()
                    val tujuan = spinnerTujuan.selectedItem.toString()
                    val BBSekarangInput = textInputBBSekarang.text.toString()
                    val BBSekarang = BBSekarangInput.toInt()
                    val BBSekarangUkuran = spinnerBBSekarang.selectedItem.toString()
                    val BBTujuanInput = textInputBBGoals.text.toString()
                    val BBTujuan = BBTujuanInput.toInt()
                    val BBTujuanUkuran = spinnerBBTujuan.selectedItem.toString()
                    val KaloriInput = inputKalori.text.toString()
                    val Kalori = KaloriInput.toInt()


                    data.putString("NAME", nama)
                    data.putString("AGE", usia)
                    data.putString("PURPOSE", tujuan)
                    data.putInt("BBCURRENT", BBSekarang)
                    data.putString("BBCURRENTM", BBSekarangUkuran)
                    data.putInt("BBGOAL", BBTujuan)
                    data.putString("BBGOALM", BBTujuanUkuran)
                    data.putInt("CALORY", Kalori)
                    data.putInt("YEAR", years)
                    data.putInt("MONTH", months + 1)
                    data.putInt("DATE", dates)


                    val intent = Intent(
                        this@GetStartedScreen,
                        HomeScreen::class.java
                    ).apply { putExtras(data) }
                    startActivity(intent)
                }
            }
        }
    }
}