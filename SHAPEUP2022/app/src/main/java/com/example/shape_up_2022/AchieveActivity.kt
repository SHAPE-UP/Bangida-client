package com.example.shape_up_2022

import com.example.progressbar.ViewPagerAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shape_up_2022.databinding.ActivityAchieveBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AchieveActivity : AppCompatActivity() {

    lateinit var binding: ActivityAchieveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchieveBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btn.setOnClickListener { view ->
            binding.pb4.incrementProgressBy(5)
        }

        binding.btn2.setOnClickListener { view ->
            binding.pb4.incrementProgressBy(-5)
        }

        binding.btn3.setOnClickListener { view ->
            binding.pb4.progress = 50
        }

        binding.tabs.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        binding.pager.adapter = ViewPagerAdapter(this)


        TabLayoutMediator(binding.tabs, binding.pager) {
                tab,position->
            when(position){
                0->tab.text = "탭1"
                1->tab.text = "탭2"
                2->tab.text = "탭3"
                3->tab.text = "탭4"
            }
        }.attach()
    }
}