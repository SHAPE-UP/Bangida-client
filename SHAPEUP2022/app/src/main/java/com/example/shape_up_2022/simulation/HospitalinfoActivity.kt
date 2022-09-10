package com.example.shape_up_2022.simulation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.shape_up_2022.databinding.ActivityHospitalinfoBinding

class HospitalinfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHospitalinfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 튜토리얼
        binding.tutoHospital.setOnClickListener {
            binding.tutoHospital.visibility = View.GONE
        }

        binding.injection68.setOnClickListener{
            AlertDialog.Builder(this).run {
                setTitle("1차 접종: 생후 6-8주")
                setMessage("종합백신, 코로나 장염")
                setPositiveButton("확인", null)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(true)
            true
        }
        binding.injection810.setOnClickListener{
            AlertDialog.Builder(this).run {
                setTitle("2차 접종: 생후 8-10주")
                setMessage("종합백신, 코로나 장염")
                setPositiveButton("확인", null)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(true)
            true
        }
        binding.injection1012.setOnClickListener{
            AlertDialog.Builder(this).run {
                setTitle("3차 접종: 생후 10-12주")
                setMessage("종합백신, 켄넬코프")
                setPositiveButton("확인", null)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(true)
            true
        }
        binding.injection1214.setOnClickListener{
            AlertDialog.Builder(this).run {
                setTitle("4차 접종: 생후 12-14주")
                setMessage("종합백신, 켄넬코프")
                setPositiveButton("확인", null)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(true)
            true
        }
        binding.injection1416.setOnClickListener{
            AlertDialog.Builder(this).run {
                setTitle("5차 접종: 생후 14-16주")
                setMessage("종합백신, 인플루엔자")
                setPositiveButton("확인", null)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(true)
            true
        }
        binding.injection1618.setOnClickListener{
            AlertDialog.Builder(this).run {
                setTitle("6차 접종: 생후 16-18주")
                setMessage("광견병, 인플루엔자, 항체가검사")
                setPositiveButton("확인", null)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(true)
            true
        }
        binding.injection15.setOnClickListener{
            AlertDialog.Builder(this).run {
                setTitle("추가 접종: 매월 15일")
                setMessage("심장사상충, 외부기생충")
                setPositiveButton("확인", null)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(true)
            true
        }

        binding.quiz1.setOnClickListener {
            val intent = Intent(this, HospitalquizActivity::class.java)
            startActivity(intent)
        }
        binding.quiz2.setOnClickListener {
            val intent = Intent(this, Hospitalquiz2Activity::class.java)
            startActivity(intent)
        }
        binding.quiz3.setOnClickListener {
            val intent = Intent(this, Hospitalquiz3Activity::class.java)
            startActivity(intent)
        }
    }
}