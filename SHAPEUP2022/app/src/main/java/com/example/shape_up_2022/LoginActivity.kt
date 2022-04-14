package com.example.shape_upapptest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.shape_upapptest.databinding.SignInBinding

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = SignInBinding.inflate(layoutInflater) // 바인딩
        setContentView(binding.root) // 액티비티 화면 출력: 로그인

        // 회원가입 페이지 이동
        binding.gotoSignUp.setOnClickListener{
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        // 로그인 버튼을 눌렀을 때
        binding.btnLogin.setOnClickListener{
            val inputID = binding.inputId.text.toString() // 아이디에 대한 String
            val inputPW = binding.inputPw.text.toString() // 비밀번호에 대한 String
            
            // DB에서 아이디가 있는지 검색, 아이디가 있으면 아이디에 해당하는 비밀번호가 맞는지 검색
            if (inputID == "" || inputPW == ""){ // ID or 패스워드를 입력하지 않았을 때
                Log.d("app_test", "다시 입력하세요")
                binding.reEnterIdPw.visibility = View.VISIBLE
            }
            else if (inputID == "1234"){ // 해당하는 아이디가 존재할 때
                if(inputPW == "1234"){
                    Log.d("app_test", "로그인 완료")

                    // intent: mainActivity로 이동
                    val intent = Intent(
                        this, MainActivity::class.java)
                    startActivity(intent)
                    this.finish()
                    // 로그인 이력 저장, 다음에는 로그인 창 없이 바로 메인 페이지로 이동하도록 구현
                } else{ // 비밀번호가 틀린 경우

                }
            }
            else{ // 아이디와 비밀번호가 틀린 경우
                
                Log.d("app_test", "로그인 실패 ${inputID}, ${inputPW}")
                // 리렌더링 후 아이디와 비밀번호가 틀렸음을 알린다. (모달 띄우는 것보다는 xml에서 알리면 좋을 듯)
            }
        }
            //간편 로그인 연동 => api
    }

}