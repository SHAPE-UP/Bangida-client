package com.example.shape_up_2022.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.shape_up_2022.common.MainActivity
import com.example.shape_up_2022.databinding.ActivityRegisterShareBinding
import com.example.shape_up_2022.retrofit.JoinFamilyReq
import com.example.shape_up_2022.retrofit.JoinFamilyRes
import com.example.shape_up_2022.retrofit.MyApplication
import com.google.android.youtube.player.internal.t
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterShareActivity : AppCompatActivity() {
    lateinit var binding : ActivityRegisterShareBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterShareBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("mobileApp", "실행 됨!")

        binding.shareButton.setOnClickListener {

            val familyCode: String = binding.familyCode.text.toString()
            val email = intent.getStringExtra("email")
            //val userEmail : String? = arguments?.getString("msg")
            Log.d("mobileApp", "familyCode: $familyCode")
            Log.d("mobileApp", "receiveData: $email")

            if(familyCode == "") {  // 입력한 코드가 없으면
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

            else {
                val call: Call<JoinFamilyRes> = MyApplication.networkServiceUsers.joinFamliy(
                    JoinFamilyReq(familyCode = familyCode, email = email!!)
                )

                call?.enqueue(object : Callback<JoinFamilyRes> {
                    override fun onResponse(call: Call<JoinFamilyRes>, response: Response<JoinFamilyRes>) {
                        if(response.isSuccessful){
                            Log.d("mobileApp", "$response ${response.body()}")

                            if(response.body()?.success == "true"){
                                // 로그인 액티비티로 이동
                                val intent = Intent(baseContext, LoginActivity::class.java)
                                //intent.putExtra("register", "회원가입이 완료되었습니다. 로그인을 진행해주세요.")
                                startActivity(intent)
                            } else{
                                Toast.makeText(baseContext, "올바르지 않은 코드입니다.", Toast.LENGTH_LONG)
                            }
                        }
                    }

                    override fun onFailure(call: Call<JoinFamilyRes>, t: Throwable) {
                        Log.d("mobileApp", "onFailure $t")
                    }
                })
            }
        }
    }
}