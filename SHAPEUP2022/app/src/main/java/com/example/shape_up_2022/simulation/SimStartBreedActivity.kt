package com.example.shape_up_2022.simulation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shape_up_2022.BreedFragment1
import com.example.shape_up_2022.R
import com.example.shape_up_2022.databinding.ActivitySimStartBreedBinding
import com.google.android.material.tabs.TabLayout

class SimStartBreedActivity : AppCompatActivity() {
    private lateinit var fragment1: BreedFragment1
    private lateinit var fragment2: BreedFragment2
    lateinit var binding: ActivitySimStartBreedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimStartBreedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragment1 = BreedFragment1()
        fragment2 = BreedFragment2()

        val transaction1 = supportFragmentManager.beginTransaction()
        transaction1.add(R.id.breed_tabContent, fragment1)

        // 첫 번째 탭을 기본으로 선택
        binding.breedTab.getTabAt(0)!!.select()
        transaction1.commit()

        binding.breedTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()

                when(tab?.text) {
                    "견종별" -> transaction.replace(R.id.breed_tabContent, fragment1)
                    "보호시설 유기견" -> transaction.replace(R.id.breed_tabContent, fragment2)
                }
                transaction.commit()
            }
            override fun onTabReselected(tab: TabLayout.Tab?) { }
            override fun onTabUnselected(tab: TabLayout.Tab?) { }
        })
    }
}