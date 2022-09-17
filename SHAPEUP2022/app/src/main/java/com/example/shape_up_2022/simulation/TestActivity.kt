package com.example.shape_up_2022.simulation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.shape_up_2022.R
import com.example.shape_up_2022.common.MainActivity
import com.example.shape_up_2022.common.SaveSharedPreference
import com.example.shape_up_2022.databinding.ActivityTestBinding
import com.example.shape_up_2022.databinding.ActivityWashingToyBinding
import com.example.shape_up_2022.retrofit.*
import com.google.android.youtube.player.internal.i
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestActivity : AppCompatActivity() {
    lateinit var binding : ActivityTestBinding

    var answer_list = ArrayList<Int>()
    var anserQ=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTestNext.setOnClickListener {
            // save Preference
            SaveSharedPreference.setUserTested(baseContext, true)
            // tested router
            callCompleteTest()

            when (binding.radioGroupA.checkedRadioButtonId){
                    R.id.radio_button_a1->answer_list.add(1)
                    R.id.radio_button_a2->answer_list.add(10)
                    R.id.radio_button_a3->answer_list.add(100)
            }
            when (binding.radioGroupB.checkedRadioButtonId){
                    R.id.radio_button_b1->answer_list.add(1)
                    R.id.radio_button_b2->answer_list.add(10)
                    R.id.radio_button_b3->answer_list.add(100)
                    R.id.radio_button_b4->answer_list.add(11)
            }

            for(i in 0..answer_list.size -1) {
                anserQ = (answer_list[i] + anserQ)
            }
            Log.d("mobileA","${anserQ}")

            val intent = Intent(this, TestResultActivity::class.java)
            intent.putExtra("ANSWER",anserQ)
            startActivity(intent)
        }
    }

    private fun callCompleteTest(){
        val call: Call<CompleteTestRes> = MyApplication.networkServiceUsers.completeTest(
            CompleteTestReq(SaveSharedPreference.getUserID(this)!!)
        )
        call?.enqueue(object : Callback<CompleteTestRes> {
            override fun onResponse(call: Call<CompleteTestRes>, response: Response<CompleteTestRes>) {
                if(response.isSuccessful){
                    Log.d("mobileApp", "$response ${response.body()}")
                    Toast.makeText(baseContext, "성향 점검 테스트 완료!", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<CompleteTestRes>, t: Throwable) {
                Log.d("mobileApp", "onFailure $t")
                Toast.makeText(baseContext, "성향 점검 데이터 전송 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }
}