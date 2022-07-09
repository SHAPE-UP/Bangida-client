package com.example.shape_up_2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.shape_up_2022.databinding.ActivitySimTakeAwalkBinding
import com.google.android.gms.maps.GoogleMap

class SimTakeAWalkActivity : AppCompatActivity() {
    lateinit var binding: ActivitySimTakeAwalkBinding
    private var mapfragment = SimTakeAWalkFragment()  // 지도 프래그먼트
    var googleMap: GoogleMap? = mapfragment.googleMap  // 구글맵

    private val fl: FrameLayout by lazy {
        findViewById(R.id.take_a_walk_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimTakeAwalkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(mapfragment)  // 지도 프래그먼트 부착
        binding.recyclerViewPlacelist.visibility = View.GONE  // 리스트 안 보이게




    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(fl.id, fragment).commit()
    }
}