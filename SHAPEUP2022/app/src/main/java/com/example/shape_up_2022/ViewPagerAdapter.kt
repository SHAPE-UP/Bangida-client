package com.example.progressbar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.shape_up_2022.AchieveFragment1
import com.example.shape_up_2022.AchieveFragment2
import com.example.shape_up_2022.AchieveFragment3
import com.example.shape_up_2022.AchieveFragment4

class ViewPagerAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int =4

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0-> AchieveFragment1()
            1-> AchieveFragment2()
            2-> AchieveFragment3()
            else -> AchieveFragment4()

        }
    }
}