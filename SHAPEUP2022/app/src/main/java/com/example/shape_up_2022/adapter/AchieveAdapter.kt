package com.example.shape_up_2022.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.shape_up_2022.achieve.AchieveFragment1
import com.example.shape_up_2022.achieve.AchieveFragment2
import com.example.shape_up_2022.achieve.AchieveFragment3
import com.example.shape_up_2022.achieve.AchieveFragment4


class AchieveAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int =3

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0-> AchieveFragment1()
            1-> AchieveFragment2()
            else -> AchieveFragment4()

        }
    }
}