package com.example.shape_up_2022.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.shape_up_2022.R
import com.example.shape_up_2022.common.MainActivity
import com.example.shape_up_2022.retrofit.LoginReq
import com.example.shape_up_2022.retrofit.LoginRes
import com.example.shape_up_2022.retrofit.MyApplication
import com.example.shape_up_2022.common.SaveSharedPreference
import com.example.shape_up_2022.databinding.SignInBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = SignInBinding.inflate(layoutInflater) // 바인딩
        setContentView(binding.root) // 액티비티 화면 출력: 로그인

        // 회원가입 페이지 이동
        binding.gotoSignUp.setOnClickListener{
            val intent = Intent(this, RegisterInputActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 로그인 버튼을 눌렀을 때
        binding.btnLogin.setOnClickListener {
            val inputID = binding.inputId.text.toString() // 아이디에 대한 String
            val inputPW = binding.inputPw.text.toString() // 비밀번호에 대한 String

            // DB에 저장된 아이디 값
            val call: Call<LoginRes> = MyApplication.networkServiceAuth.login(
                LoginReq(inputID, inputPW)
            )

            call?.enqueue(object : Callback<LoginRes> {
                override fun onResponse(call: Call<LoginRes>, response: Response<LoginRes>) {
                    if(response.isSuccessful){
                        Log.d("mobileApp", "$response ${response.body()}")
                        // 프리퍼런스에 값 저장
                        SaveSharedPreference.setUserEmail(baseContext, inputID)
                        SaveSharedPreference.setUserName(baseContext, response.body()!!.userName)
                        SaveSharedPreference.setUserID(baseContext, response.body()!!.userID)

                        // 메인 페이지로 이동
                        val intent = Intent(baseContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<LoginRes>, t: Throwable) {
                    Log.d("mobileApp", "onFailure $t")
                    Toast.makeText(baseContext, "아이디 또는 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                }
            })

        }
            //간편 로그인 연동 => api
    }


}