package com.example.shape_up_2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.shape_up_2022.databinding.ActivityWashingToyBinding

class SimWashingToyActivity : AppCompatActivity() {
    lateinit var binding : ActivityWashingToyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWashingToyBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // 1단계: 물에 담그기 / 장난감 대야에 옮기기
        replaceFragment(SimWashingFragment1())
    }

    fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.wash_fragment, fragment)
        transaction.commit()

    }
}