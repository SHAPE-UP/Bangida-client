package com.example.shape_up_2022.simulation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.shape_up_2022.common.MainActivity
import com.example.shape_up_2022.common.SaveSharedPreference
import com.example.shape_up_2022.databinding.ActivityTestBinding
import com.example.shape_up_2022.retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTestSkip.setOnClickListener {
            // save Preference
            SaveSharedPreference.setUserTested(baseContext, true)

            // tested router
            callCompleteTest()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun callCompleteTest(){
        val call: Call<CompleteTestRes> = MyApplication.networkServiceAuth.completeTest(
            CompleteTestReq(SaveSharedPreference.getUserID(this)!!)
        )

        call?.enqueue(object : Callback<CompleteTestRes> {
            override fun onResponse(call: Call<CompleteTestRes>, response: Response<CompleteTestRes>) {
                if(response.isSuccessful){
                    Log.d("mobileApp", "$response ${response.body()}")
                    Toast.makeText(baseContext, "성향 점검 테스트 성공!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CompleteTestRes>, t: Throwable) {
                Log.d("mobileApp", "onFailure $t")
                Toast.makeText(baseContext, "성향 점검 테스트 실패!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}