package com.example.shape_up_2022

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shape_up_2022.databinding.ActivityMainBinding
import com.example.shape_up_2022.databinding.ActivitySimBeautyBinding

class SimBeautyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySimBeautyBinding.inflate(layoutInflater) // 바인딩
        setContentView(binding.root) // 액티비티 화면 출력


        binding.button1.setOnClickListener {
            val intent_simul = Intent(this, SimBeutySelfActivity::class.java)
            startActivity(intent_simul)
            overridePendingTransition(0, 0)
            finish()
        }

        binding.button2.setOnClickListener {
            val intent_simul = Intent(this, SimBeutyShopActivity::class.java)
            startActivity(intent_simul)
            overridePendingTransition(0, 0)
            finish()
        }


    }
}