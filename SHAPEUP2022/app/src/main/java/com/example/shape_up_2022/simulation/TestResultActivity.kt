package com.example.shape_up_2022.simulation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shape_up_2022.R
import com.example.shape_up_2022.common.MainActivity
import com.example.shape_up_2022.common.SaveSharedPreference
import com.example.shape_up_2022.databinding.ActivityTestResultBinding

class TestResultActivity : AppCompatActivity() {
    lateinit var binding :ActivityTestResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_test_result)
        binding = ActivityTestResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val answer = intent.getIntExtra("ANSWER",0)
        if(answer<50){
            binding.ImgPet.setImageResource(R.drawable.maltese)
            binding.text1.text="말티즈"
            binding.text2.text="<일반외모>\n" +
                    "\n" +
                    "작은 사이즈와 길쭉한 체형으로 매우 길고 하얀 코트로 덮여 있으며 자신감 있고 품위 있게 들어올린 머리를 가진 우아한 모습이다.\n" +
                    "\n" +
                    "　\n" +
                    "<습성 / 성격>\n" +
                    "\n" +
                    "활발하고 애정이 깊으며 유순하고 지능이 높다.\n" +
                    "\n" +
                    " \n" +
                    "<주요비율>\n" +
                    "\n" +
                    "체장이 체고보다 대략 38% 정도 길고 두부의 길이는 체고의 6/11과 같다. "
            //DB에서 정보를 불러오는 깔끔한 방법?
        }

        binding.btnTestEnd.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}