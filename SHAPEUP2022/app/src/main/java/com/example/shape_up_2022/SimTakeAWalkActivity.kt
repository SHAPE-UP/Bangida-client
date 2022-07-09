package com.example.shape_up_2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.shape_up_2022.databinding.ActivitySimTakeAwalkBinding

class SimTakeAWalkActivity : AppCompatActivity() {

    private val fl: FrameLayout by lazy {
        findViewById(R.id.take_a_walk_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sim_take_awalk)

        replaceFragment(SimTakeAWalkFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(fl.id, fragment).commit()
    }
}