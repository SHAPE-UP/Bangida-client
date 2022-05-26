package com.example.shape_up_2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.shape_up_2022.databinding.SimulationMainBinding

class SimulationActivity : AppCompatActivity() {
    lateinit var binding: SimulationMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SimulationMainBinding.inflate(layoutInflater)
        if (false) { // 시뮬레이션을 시작하지 않은 유저라면(반려견이 없는지 검사)
            binding.simNotStarted.visibility= View.VISIBLE
            binding.simStarted.visibility= View.INVISIBLE
            binding.simStartBtn.setOnClickListener {
                //버튼 클릭하면 시뮬레이션 시작 액티비티로 전환
                //val intent = Intent(this, 시작액티비티::class.java)
                //startActivity(intent)
            }
        }

        setContentView(binding.root)

        // 실내/외출 모드 전환

        binding.btnout.setOnClickListener {
            binding.btnout.visibility = View.INVISIBLE
            binding.indoor.visibility = View.INVISIBLE
            binding.btnin.visibility = View.VISIBLE
            binding.outdoor.visibility = View.VISIBLE
        }

        binding.btnin.setOnClickListener {
            binding.btnin.visibility = View.INVISIBLE
            binding.outdoor.visibility = View.INVISIBLE
            binding.btnout.visibility = View.VISIBLE
            binding.indoor.visibility = View.VISIBLE
        }

    }
}