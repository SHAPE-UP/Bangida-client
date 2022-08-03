package com.example.shape_up_2022

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.shape_up_2022.databinding.ActivitySimStartNamingBinding

class SimStartNamingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySimStartNamingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nameStep1.visibility = View.VISIBLE
        binding.nameStep2.visibility = View.GONE
        binding.nameStep3.visibility = View.GONE
        binding.nameStep4.visibility = View.GONE

        binding.nameBtn1.setOnClickListener {
            binding.nameStep1.visibility = View.GONE
            binding.nameStep2.visibility = View.VISIBLE
            binding.nameStep3.visibility = View.GONE
            binding.nameStep4.visibility = View.GONE
        }

        binding.nameBtn2.setOnClickListener {
            binding.nameStep1.visibility = View.GONE
            binding.nameStep2.visibility = View.GONE
            binding.nameStep3.visibility = View.VISIBLE
            binding.nameStep4.visibility = View.GONE
        }

        binding.btnNamingFinish.setOnClickListener {
            binding.nameStep1.visibility = View.GONE
            binding.nameStep2.visibility = View.GONE
            binding.nameStep3.visibility = View.GONE
            binding.nameStep4.visibility = View.VISIBLE
        }

        binding.btnRegisterInfoLink.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.animal.go.kr/front/community/show.do;jsessionid=Lg5YgpnbEoP5liudNsFMSicezdue41UcTvIrz0zd46ptK48F7PERl09jE3i0g3a8.aniwas2_servlet_front?boardId=contents&seq=66&menuNo=2000000016"))
            startActivity(browserIntent)
        }

        binding.nameBtn4.setOnClickListener {
            val intent = Intent(this, ToDoActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}