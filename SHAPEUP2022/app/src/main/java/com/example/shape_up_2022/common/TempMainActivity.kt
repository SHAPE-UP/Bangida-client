package com.example.shape_up_2022.common

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.shape_up_2022.achieve.AchieveActivity
import com.example.shape_up_2022.databinding.ActivityTempMainBinding
import com.example.shape_up_2022.simulation.*
import com.example.shape_up_2022.todo.TodoActivity
import com.example.shape_up_2022.todo.TodoCalendarActivity
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class TempMainActivity : AppCompatActivity() {
    private lateinit var intent1 : Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTempMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getHashKey()

        binding.btnSimulationMain.setOnClickListener {
            intent1 = Intent(this, SimulationActivity::class.java)
            startActivity(intent1)
        }

        binding.budgetMain.setOnClickListener {
            intent1 = Intent(this, BudgetActivity::class.java)
            startActivity(intent1)
        }

        binding.btnCalenderTodolist.setOnClickListener {
            intent1 = Intent(this, TodoActivity::class.java)
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

        binding.youtubebtn.setOnClickListener {
            intent1 = Intent(this, YoutubeActivity::class.java)
            startActivity(intent1)
        }
        
        binding.btnSimulationStart.setOnClickListener {
            intent1 = Intent(this, SimStartActivity::class.java)
            startActivity(intent1)
        }

        binding.hospitalbtn.setOnClickListener{
            intent1 = Intent(this, HospitalinfoActivity::class.java)
            startActivity(intent1)
        }

        binding.calendar.setOnClickListener{
            intent1 = Intent(this, TodoCalendarActivity::class.java)
            startActivity(intent1)
        }

        binding.petplaceSearch.setOnClickListener{
            intent1 = Intent(this, SimWalkSearchActivity::class.java)
            startActivity(intent1)
        }

        binding.petplaceTraining.setOnClickListener {
            intent1 = Intent(this, SimTrainingActivity::class.java)
            startActivity(intent1)
        }

    }

    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }
}