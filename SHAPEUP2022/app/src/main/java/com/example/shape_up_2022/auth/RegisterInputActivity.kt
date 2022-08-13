package com.example.shape_up_2022.auth

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.example.shape_up_2022.R
import com.example.shape_up_2022.databinding.ActivityRegisterInputBinding
import com.example.shape_up_2022.retrofit.MyApplication
import com.example.shape_up_2022.retrofit.RegisterReq
import com.example.shape_up_2022.retrofit.RegisterRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterInputActivity : AppCompatActivity() {

    lateinit var binding : ActivityRegisterInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterInputBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // EditText binding
        val input_name = binding.inputName
        val input_email = binding.newEmail
        val new_password = binding.inputNewpassword
        val check_password = binding.checkPassword

        // 버튼 view 바인딩
        binding.nextCreate.setOnClickListener{
            // 회원 등록
            registerUser(input_name.text.toString(), input_email.text.toString(), new_password.text.toString())

            // email 전달, 화면 이동
            val intent = Intent(this, RegisterShareActivity::class.java)
            intent.putExtra("email", input_email.text.toString())
            startActivity(intent)
        }

        binding.nextCreate.isEnabled = false
        binding.nextCreate.setBackgroundColor(Color.parseColor("#d3d3d3"));

        // 핸들러 적용
        var textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (input_name.length() > 0 && input_email.length() > 0 && new_password.length() > 0 && check_password.length() > 0){
                    binding.nextCreate.isEnabled = true
                    binding.nextCreate.setBackgroundColor(Color.parseColor("#FF9966"));
                } else {
                    binding.nextCreate.isEnabled = false
                    binding.nextCreate.setBackgroundColor(Color.parseColor("#d3d3d3"));
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        }

        // 이벤트 핸들러 연결하기
        input_name.addTextChangedListener(textWatcher)
        input_email.addTextChangedListener(textWatcher)
        new_password.addTextChangedListener(textWatcher)
        check_password.addTextChangedListener(textWatcher)
    }

    private fun registerUser(userName: String, email: String, password: String){
        val call: Call<RegisterRes> = MyApplication.networkServiceAuth.register(
            RegisterReq(userName,email, password)
        )

        call?.enqueue(object : Callback<RegisterRes> {
            override fun onResponse(call: Call<RegisterRes>, response: Response<RegisterRes>) {
                if(response.isSuccessful){
                    Log.d("mobileApp", "$response ${response.body()}")
                }
            }

            override fun onFailure(call: Call<RegisterRes>, t: Throwable) {
                Log.d("mobileApp", "onFailure $t")
            }
        })
    }
}