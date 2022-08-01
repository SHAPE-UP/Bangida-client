package com.example.shape_up_2022

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.shape_up_2022.databinding.ActivitySimHealthBinding

class SimHealthActivity : AppCompatActivity() {

    private val fl: LinearLayout by lazy {
        findViewById(R.id.fragment_health)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySimHealthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 초기화: 건강 정보
        replaceFragment(SimHealthInfoFragment())

        // 건강 그래프 버튼
        binding.healthGraphBtn.setOnClickListener {
            binding.healthGraphBtn.setBackgroundColor(Color.parseColor("#FFF9C299"))
            binding.healthInfoBtn.setBackgroundColor(Color.parseColor("#FFBAB4B2"))
            replaceFragment(SimHealthGraphFragment())
        }

        // 건강 정보 버튼
        binding.healthInfoBtn.setOnClickListener{
            binding.healthInfoBtn.setBackgroundColor(Color.parseColor("#FFF9C299"))
            binding.healthGraphBtn.setBackgroundColor(Color.parseColor("#FFBAB4B2"))
            replaceFragment(SimHealthInfoFragment())
        }

    }

    // 프래그먼트 교체
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(fl.id, fragment).commit()
    }
}