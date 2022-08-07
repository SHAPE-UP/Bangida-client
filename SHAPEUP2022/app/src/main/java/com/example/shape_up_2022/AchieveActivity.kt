package com.example.shape_up_2022

import android.content.Intent
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
        binding.achievePager.adapter = ViewPagerAdapter(this)


        TabLayoutMediator(binding.tabs, binding.achievePager) {
                tab,position->
            when(position){
                0->tab.text = R.string.achieve_tab1.toString()
                1->tab.text = R.string.achieve_tab2.toString()
                2->tab.text = R.string.achieve_tab3.toString()
                3->tab.text = R.string.achieve_tab4.toString()
            }
        }.attach()

        // 탭바 연결
        binding.navHome.setOnClickListener {
            val intent_home = Intent(this, MainActivity::class.java)
            startActivity(intent_home)
            overridePendingTransition(0, 0)
            finish()
        }
        binding.navTodo.setOnClickListener {
            val intent_todo = Intent(this, ToDoActivity::class.java)
            startActivity(intent_todo)
            overridePendingTransition(0, 0)
            finish()
        }
        binding.navSimulation.setOnClickListener {
            val intent_simul = Intent(this, SimulationActivity::class.java)
            startActivity(intent_simul)
            overridePendingTransition(0, 0)
            finish()
        }
        binding.navAchievement.setOnClickListener {
            val intent_achieve = Intent(this, AchieveActivity::class.java)
            startActivity(intent_achieve)
            overridePendingTransition(0, 0)
            finish()
        }
        binding.navMypage.setOnClickListener {
            val intent_mypage = Intent(this, MyPageActivity::class.java)
            startActivity(intent_mypage)
            overridePendingTransition(0, 0)
            finish()
        }
    }
}