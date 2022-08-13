package com.example.shape_up_2022.achieve

import android.content.DialogInterface
import android.content.Intent
import com.example.shape_up_2022.adapter.AchieveAdapter
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.shape_up_2022.*
import com.example.shape_up_2022.common.MainActivity
import com.example.shape_up_2022.common.MyPageActivity
import com.example.shape_up_2022.common.SaveSharedPreference
import com.example.shape_up_2022.databinding.ActivityAchieveBinding
import com.example.shape_up_2022.common.SimulationActivity
import com.example.shape_up_2022.simulation.TestActivity
import com.example.shape_up_2022.todo.TodoActivity
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
        binding.achievePager.adapter = AchieveAdapter(this)


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
    }

    override fun onStart() {
        super.onStart()


        val eventHandler = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(p1 == DialogInterface.BUTTON_POSITIVE) {
                    val intent = Intent(this@AchieveActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        var builder = AlertDialog.Builder(this)
            .setTitle("가족 그룹에 가입되어 있지 않은 사용자")
            .setIcon(R.drawable.maltese)
            .setMessage("가족 그룹에 먼저 가입해주세요.")
            .setPositiveButton("확인", eventHandler)
            .setCancelable(false)

        if(SaveSharedPreference.getFamliyID(this)!! == ""){
            builder.show()
        }
    }
}