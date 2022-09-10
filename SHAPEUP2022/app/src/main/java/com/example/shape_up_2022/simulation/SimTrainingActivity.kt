package com.example.shape_up_2022.simulation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.shape_up_2022.R
import com.example.shape_up_2022.databinding.ActivitySimTrainingBinding

class SimTrainingActivity : AppCompatActivity() {

    lateinit var binding : ActivitySimTrainingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tutoTrain.setOnClickListener {
            binding.tutoTrain.visibility = View.GONE
        }

        binding.trainingInfo.setOnClickListener{
            AlertDialog.Builder(this).run {
                setTitle("강아지 훈련에 관한 설명")
                setIcon(R.drawable.maltese)
                setMessage("강아지 훈련은 중요해요~")
                setPositiveButton("확인", null)
                show()
            }.setCanceledOnTouchOutside(false)
        }


    }
}