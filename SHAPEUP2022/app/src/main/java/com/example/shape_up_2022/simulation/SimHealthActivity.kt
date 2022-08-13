package com.example.shape_up_2022.simulation

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.shape_up_2022.R
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

        // 복용약 먹이기
        binding.medicine1.setOnClickListener{
            // alert 창
            alertFunc("복용약 먹이기","강아지에게 약을 먹이겠습니까?")
        }

        // 연고 바르기
        binding.medicine2.setOnClickListener{
            // alert 창
            alertFunc("연고 바르기", "강아지에게 연고를 바르겠습니까?")
        }
    }
    // eventHandler
    val alertEvent = object : DialogInterface.OnClickListener{
        override fun onClick(p0: DialogInterface?, p1: Int) {
            Log.d("mobileApp", "$p0, $p1")
            // text 값 변경
            // data 값 변경, 반영
        }
    }

    // func 프래그먼트 교체
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(fl.id, fragment).commit()
    }

    // alert
    private fun alertFunc(title: String, content: String){
        AlertDialog.Builder(this).run {
            setTitle(title)
            setIcon(R.drawable.maltese_p1)
            setMessage(content)
            setPositiveButton("YES", alertEvent)
            setNegativeButton("NO", null)
            show()
        }.setCanceledOnTouchOutside(false)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}