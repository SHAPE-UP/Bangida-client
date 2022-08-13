package com.example.shape_up_2022.simulation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shape_up_2022.databinding.ActivitySimHygieneBinding

class SimHygieneActivity : AppCompatActivity() {
    lateinit var binding: ActivitySimHygieneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimHygieneBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.simBath.setOnClickListener {
            val intent = Intent(this@SimHygieneActivity, SimBathActivity::class.java)
            startActivity(intent)
        }

        binding.simBrushTooth.setOnClickListener {
            val intent = Intent(this@SimHygieneActivity, SimBrushToothActivity::class.java)
            startActivity(intent)
        }

        binding.simBrush.setOnClickListener {
            val intent = Intent(this@SimHygieneActivity, SimBrushActivity::class.java)
            startActivity(intent)
        }

        binding.simPawcare.setOnClickListener {
            //val intent = Intent(this@SimHygieneActivity, SimPawcareActivity::class.java)
            //startActivity(intent)
        }

        binding.simEarcare.setOnClickListener {
            //val intent = Intent(this@SimHygieneActivity, SimEarcareActivity::class.java)
            //startActivity(intent)
        }

    }
}