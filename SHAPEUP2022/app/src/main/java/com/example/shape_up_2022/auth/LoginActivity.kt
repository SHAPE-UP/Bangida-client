package com.example.shape_up_2022.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shape_up_2022.common.MainActivity
import com.example.shape_up_2022.retrofit.LoginReq
import com.example.shape_up_2022.retrofit.LoginRes
import com.example.shape_up_2022.retrofit.MyApplication
import com.example.shape_up_2022.common.SaveSharedPreference
import com.example.shape_up_2022.databinding.SignInBinding
import com.example.shape_up_2022.retrofit.GetPetIDRes
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
            val call: Call<LoginRes> = MyApplication.networkServiceUsers.login(
                LoginReq(inputID, inputPW)
            )

            call?.enqueue(object : Callback<LoginRes> {
                override fun onResponse(call: Call<LoginRes>, response: Response<LoginRes>) {
                    if(response.isSuccessful){
                        Log.d("mobileApp", "$response ${response.body()}")
                        if(response.body()?.loginSuccess == "true"){ // 로그인 성공

                            // 프리퍼런스에 값 저장
                            SaveSharedPreference.setUserEmail(baseContext, inputID) // 이메일
                            SaveSharedPreference.setUserName(baseContext, response.body()!!.user.name) // 이름
                            SaveSharedPreference.setUserID(baseContext, response.body()!!.user._id) // userID
                            SaveSharedPreference.setFamliyID(baseContext, response.body()!!.user.familyID?:null) // familyID
                            SaveSharedPreference.setUserTested(baseContext, response.body()!!.user.tested) // 성향점검 여부
                            SaveSharedPreference.setAchieve(baseContext, response.body()!!.user.achieve) // 업적 달성 여부

                            // petID 프리퍼런스에 저장
                            if(response.body()!!.user.familyID != null){
                                //Log.d("")
                                callGetPetID(response.body()!!.user.familyID!!)
                            } else{
                                // 메인 페이지로 이동
                                val intent = Intent(baseContext, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }

                        } else { // 로그인 실패 or null
                            Toast.makeText(baseContext, "아이디 또는 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                        }

                    }
                }

                override fun onFailure(call: Call<LoginRes>, t: Throwable) {
                    Log.d("mobileApp", "onFailure $t")
                    Toast.makeText(baseContext, "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
                }
            })

        }
    }

    private fun callGetPetID(familyID: String){
        // DB에 저장된 아이디 값
        val call: Call<GetPetIDRes> = MyApplication.networkServiceFamily.getPetID(
            familyID = familyID
        )

        call?.enqueue(object : Callback<GetPetIDRes> {
            override fun onResponse(call: Call<GetPetIDRes>, response: Response<GetPetIDRes>) {
                if(response.isSuccessful){
                    Log.d("mobileApp", "$response ${response.body()}")
                        if(response.body()!!.success){
                            SaveSharedPreference.setPetID(baseContext, response.body()!!.petID)
                            Log.d("mobileApp", "${response.body()!!.petID}" )

                            // 메인 페이지로 이동
                            val intent = Intent(baseContext, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                }
            }

            override fun onFailure(call: Call<GetPetIDRes>, t: Throwable) {
                Log.d("mobileApp", "onFailure $t")
                Toast.makeText(baseContext, "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
            }
        })
    }


}