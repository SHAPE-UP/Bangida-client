package com.example.shape_up_2022

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shape_up_2022.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var intent1 : Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.budgetMain.setOnClickListener {
            intent1 = Intent(this, BudgetActivity::class.java)
            startActivity(intent1)
        }

        binding.btnCalenderTodolist.setOnClickListener {
            intent1 = Intent(this, ToDoActivity::class.java)
            startActivity(intent1)
        }

        binding.btnDogAlarm.setOnClickListener {
            intent1 = Intent(this, AlarmActivity::class.java)
            startActivity(intent1)
        }

        binding.btnStart.setOnClickListener {
            intent1 = Intent(this, StartActivity::class.java)
            startActivity(intent1)
        }

        binding.mypage.setOnClickListener{
            intent1 = Intent(this, MyPageActivity::class.java)
            startActivity(intent1)
        }

        binding.achieveMain.setOnClickListener {
            intent1 = Intent(this, AchieveActivity::class.java)
            startActivity(intent1)
        }
    }
}