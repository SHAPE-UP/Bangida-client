package com.example.shape_up_2022.common

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.shape_up_2022.*
import com.example.shape_up_2022.achieve.AchieveActivity
import com.example.shape_up_2022.databinding.ActivityYoutubeBinding
import com.example.shape_up_2022.databinding.MainPageBinding
import com.example.shape_up_2022.simulation.SimulationActivity
import com.example.shape_up_2022.simulation.TestActivity
import com.example.shape_up_2022.todo.MainCalenderFragment
import com.example.shape_up_2022.todo.TodoActivity


class MainActivity : AppCompatActivity() {
    private val binding by lazy { MainPageBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val bindingYouTube = ActivityYoutubeBinding.inflate(layoutInflater)

        // 강아지 현재 상태, 정보


        // 프래그먼트 연결 - 캘린더 프래그먼트
        viewFragment(MainCalenderFragment() , R.id.fragment_calender)
        
        
        // 탭바 연결
        binding.navHome.setOnClickListener {
            val intent_home = Intent(this, MainActivity::class.java)
            startActivity(intent_home)
            overridePendingTransition(0, 0)
            finish()
        }
        binding.navTodo.setOnClickListener {
            val intent_todo = Intent(this, TodoActivity::class.java)
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
        // 하단 메뉴
        binding.menuBtn2.setOnClickListener {
            videoGather()
        }
        binding.menuBtn3.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
        binding.menuBtn4.setOnClickListener {
            val intent_budget = Intent(this, BudgetActivity::class.java)
            startActivity(intent_budget)
            overridePendingTransition(0, 0)
            //finish()
        }
    }
    private fun videoGather() = object : DialogInterface.OnClickListener{
        override fun onClick(p0: DialogInterface?, p1: Int) {
            val intent = Intent(this@MainActivity, YoutubeActivity::class.java)
            intent.putExtra("search", "강아지 훈련")
            startActivity(intent)
        }
    }


    // 프래그먼트 연결
    private fun viewFragment(fragment : Fragment, location:Int){
        Log.d("app_test", "viewFragment start")
        val fragmentManager : FragmentManager = supportFragmentManager
        val transaction : FragmentTransaction = fragmentManager.beginTransaction()

        transaction.add(location, fragment)
        transaction.commit()
    }
}