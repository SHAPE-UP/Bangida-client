package com.example.shape_up_2022

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shape_up_2022.databinding.ActivitySimWalkReviewAddBinding

class SimWalkReviewAddActivity : AppCompatActivity() {

    lateinit var binding : ActivitySimWalkReviewAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySimWalkReviewAddBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 리뷰 저장
        binding.addreviewSave.setOnClickListener {
            // 리뷰 DB에 넘기기

            // 산책 main 화면으로 이동
            val intent = Intent(this, SimWalkMainActivity::class.java)
            startActivity(intent)
        }
    }
}