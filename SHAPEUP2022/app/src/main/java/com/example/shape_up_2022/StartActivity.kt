package com.example.shape_upapptest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.shape_upapptest.databinding.StartAppBinding

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.start_app)

        val binding = StartAppBinding.inflate(layoutInflater) // 바인딩
        setContentView(binding.root) // 액티비티 화면 출력

        
        //버튼 이벤트에 따른 인텐트 연결
        // 1. 로그인
        binding.btnLogin.setOnClickListener {
            Log.d("app_test", "LoginActivity")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        // 2. 회원가입
        binding.btnJoin.setOnClickListener {
            Log.d("app_test", "JoinActivity")
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
            this.finish()
        }
        
    }
}