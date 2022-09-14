package com.example.shape_up_2022.simulation

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import androidx.appcompat.app.AlertDialog
import com.example.shape_up_2022.R
import com.example.shape_up_2022.common.MainActivity
import com.example.shape_up_2022.databinding.ActivitySimStartBinding

class SimStartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySimStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 버튼 비활성화
        binding.btnSimulationStartStart.isEnabled = false
        binding.btnSimulationStartStart.setBackgroundColor(Color.parseColor("#d3d3d3"));

        /* 테스트하지 않은 사용자를 돌려보내는 다이얼로그 */
        // 취소(메인으로), 테스트(테스트 시작)
        val eventhandler = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(p1==DialogInterface.BUTTON_POSITIVE) {
                    val intent = Intent(this@SimStartActivity, TestActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else if (p1==DialogInterface.BUTTON_NEGATIVE) {
                    val intent = Intent(this@SimStartActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else if (p1==DialogInterface.BUTTON_NEUTRAL) {

                }
            }
        }

        var builder = AlertDialog.Builder(this)
            .setTitle("테스트하지 않은 사용자")
            .setMessage("반려견 케어 성향 점검을 먼저 진행하세요.")
            .setPositiveButton("테스트", eventhandler)
            .setNegativeButton("취소", eventhandler)
            .setNeutralButton("넘어가기", eventhandler)
            .setCancelable(false)
        // if 성향점검테스트를 하지 않은 유저라면 모달창 표시
       // if (true) {
        //    builder.show()
        //}



        /* 체크박스를 모두 체크해야 이동 버튼 활성화 */
        val check1 = binding.startCheck1
        val check2 = binding.startCheck2
        val check3 = binding.startCheck3
        val check4 = binding.startCheck4
        val check5 = binding.startCheck5

        // checkBox event
        val checkListener = CompoundButton.OnCheckedChangeListener{ button, isChecked ->
            Log.d("mobileApp", "check! ${check1.isChecked } + ${check2.isChecked } + ${check3.isChecked } + ${check4.isChecked } + ${check5.isChecked }")
            // 5개의 체크박스가 모두 checked == true
            if(check1.isChecked && check2.isChecked && check3.isChecked && check4.isChecked && check5.isChecked){
                binding.btnSimulationStartStart.isEnabled = true
                binding.btnSimulationStartStart.setBackgroundColor(Color.parseColor("#FFE4A07F"));
            } else {
                binding.btnSimulationStartStart.isEnabled = false
                binding.btnSimulationStartStart.setBackgroundColor(Color.parseColor("#d3d3d3"));
            }
        }

        // CheckBox에 event 달기
        check1.setOnCheckedChangeListener(checkListener)
        check2.setOnCheckedChangeListener(checkListener)
        check3.setOnCheckedChangeListener(checkListener)
        check4.setOnCheckedChangeListener(checkListener)
        check5.setOnCheckedChangeListener(checkListener)

        // 입양하기 버튼 -> 화면 이동
        binding.btnSimulationStartStart.setOnClickListener {
            val intent = Intent(this, SimStartBreedActivity::class .java)
            startActivity(intent)
            finish()
        }

    }


}