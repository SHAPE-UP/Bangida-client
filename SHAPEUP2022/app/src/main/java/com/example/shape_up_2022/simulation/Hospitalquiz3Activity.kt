package com.example.shape_up_2022.simulation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.shape_up_2022.databinding.ActivityHospitalquiz3Binding

class Hospitalquiz3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHospitalquiz3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.q31W.setOnClickListener{
            val intent = Intent(this, HospitalinfoActivity::class.java)
            startActivity(intent)
        }
        binding.q32.setOnClickListener{
            AlertDialog.Builder(this).run {
                setTitle("오답")
                setMessage("다시 한 번 풀어보세요")
                setPositiveButton("확인", null)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(true)
            true
        }
        binding.q33.setOnClickListener{
            AlertDialog.Builder(this).run {
                setTitle("오답")
                setMessage("다시 한 번 풀어보세요")
                setPositiveButton("확인", null)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(true)
            true
        }
        binding.q34.setOnClickListener{
            AlertDialog.Builder(this).run {
                setTitle("오답")
                setMessage("다시 한 번 풀어보세요")
                setPositiveButton("확인", null)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(true)
            true
        }
    }
}