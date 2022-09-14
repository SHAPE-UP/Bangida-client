package com.example.shape_up_2022.common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.shape_up_2022.auth.LoginActivity
import com.example.shape_up_2022.auth.RegisterInputActivity
import com.example.shape_up_2022.databinding.ActivitySplashBinding
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로그인 버튼
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        // 회원가입 버튼
        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, RegisterInputActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        // 스플래시 동작
        val backGroundExecutor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        val mainExecutor: Executor = ContextCompat.getMainExecutor(this)
        backGroundExecutor.schedule({
            mainExecutor.execute{

                // 로그인 판정
                if(SaveSharedPreference.getUserEmail(this)?.length != 0){
                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else { // 로그아웃 상태면 로그인, 회원가입 버튼 보이기
                    binding.btnLogin.visibility = View.VISIBLE
                    binding.btnSignup.visibility = View.VISIBLE
                }
            }
        }, 2, TimeUnit.SECONDS)  // 2초 후에 실행
    }
}