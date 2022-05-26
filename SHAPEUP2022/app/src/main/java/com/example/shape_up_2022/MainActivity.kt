package com.example.shape_up_2022

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.shape_up_2022.databinding.MainPageBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { MainPageBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 강아지 현재 상태, 정보


        // 프래그먼트 연결 - 캘린더 프래그먼트
        viewFragment(MainCalenderFragment() , R.id.fragment_calender)

        // 프래그먼트 연결 - 여러가지 메뉴
        viewFragment(MainRecyclerViewFragment(), R.id.fragmentView_recycler)
    }

    // 프래그먼트 연결
    private fun viewFragment(fragment : Fragment, location:Int){
        Log.d("app_test", "viewFragment start")
        val fragmentManager : FragmentManager = supportFragmentManager
        val transaction : FragmentTransaction = fragmentManager.beginTransaction()

        transaction.add(location, fragment)
        transaction.commit()
    }
}