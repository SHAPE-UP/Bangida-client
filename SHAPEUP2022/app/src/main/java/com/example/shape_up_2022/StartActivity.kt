<<<<<<< HEAD
package com.example.shape_up_2022
=======
package com.example.shape_upapptest
>>>>>>> e162eb89f48732050dca3ebbd7b84501d5ec16c4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
<<<<<<< HEAD
import com.example.shape_up_2022.databinding.StartAppBinding

=======
import com.example.shape_upapptest.databinding.StartAppBinding
>>>>>>> e162eb89f48732050dca3ebbd7b84501d5ec16c4

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< HEAD

        val binding = StartAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
=======
        //setContentView(R.layout.start_app)

        val binding = StartAppBinding.inflate(layoutInflater) // 바인딩
        setContentView(binding.root) // 액티비티 화면 출력
>>>>>>> e162eb89f48732050dca3ebbd7b84501d5ec16c4

        
        //버튼 이벤트에 따른 인텐트 연결
        // 1. 로그인
        binding.btnLogin.setOnClickListener {
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