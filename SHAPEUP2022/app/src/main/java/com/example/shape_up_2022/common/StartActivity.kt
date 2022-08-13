package com.example.shape_up_2022.common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.shape_up_2022.auth.LoginActivity
import com.example.shape_up_2022.auth.RegisterInputActivity
import com.example.shape_up_2022.databinding.StartAppBinding


class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = StartAppBinding.inflate(layoutInflater) // 바인딩
        setContentView(binding.root) // 액티비티 화면 출력
        
        //버튼 이벤트에 따른 인텐트 연결
        if(SaveSharedPreference.getUserEmail(this)?.length != 0){
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else{
            // 1. 로그인
            binding.btnLogin.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                this.finish()
            }

            // 2. 회원가입
            binding.btnJoin.setOnClickListener {
                Log.d("app_test", "JoinActivity")
                val intent = Intent(this, RegisterInputActivity::class.java)
                startActivity(intent)
                this.finish()
            }
        }
    }
}