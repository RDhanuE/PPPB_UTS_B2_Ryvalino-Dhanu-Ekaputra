package com.example.calorify

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.calorify.databinding.ActivityInputDataScreenBinding
import com.google.android.material.tabs.TabLayout

class InputDataScreen : AppCompatActivity(){
    private lateinit var binding: ActivityInputDataScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityInputDataScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val adapter = PagerAdapter(supportFragmentManager)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.lightBlue)



        with(binding){
            viewPager.adapter = adapter
            tabLayout.setupWithViewPager(viewPager)

            tabLayout.getTabAt(0)?.text = "IN"
            tabLayout.getTabAt(1)?.text = "OUT"
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabSelected(tab: TabLayout.Tab) {
                    val selectedPosition = tab.position
                    viewPager.currentItem = selectedPosition
                }
            })


        }
    }

}