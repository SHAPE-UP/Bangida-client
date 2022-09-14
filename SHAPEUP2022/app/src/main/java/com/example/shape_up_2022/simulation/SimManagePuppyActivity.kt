package com.example.shape_up_2022.simulation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.shape_up_2022.R
import com.example.shape_up_2022.achieve.AchieveActivity
import com.example.shape_up_2022.common.SaveSharedPreference
import com.example.shape_up_2022.databinding.ActivityManagePuppyBinding
import com.google.android.youtube.player.internal.f

class SimManagePuppyActivity : AppCompatActivity() {
    lateinit var binding : ActivityManagePuppyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManagePuppyBinding.inflate(layoutInflater)
        var status = 0
        binding.progressBar.progress = status
        setContentView(binding.root)

        // 튜토리얼
        binding.simManageTuto.setOnClickListener {
            binding.simManageTuto.visibility = View.GONE
        }

        // 액티비티 이동: 강아지 청소
        binding.cleanBedBtn.setOnClickListener{
            val intent = Intent(this, SimCleaningbedActivity::class.java)
            startActivity(intent)
        }

        // 액티비티 이동: 장난감 갖고 놀기
        binding.toyPuppy.setOnClickListener {
            val intent = Intent(this, SimToyPuppyActivity::class.java)
            startActivity(intent)
        }

        // 강아지 애정 주기
        binding.puppyImage.setOnTouchListener { view, event ->
            when(event?.action){
                MotionEvent.ACTION_MOVE ->{
                    binding.progressBar.incrementProgressBy(2)
                    status += 2
                }
            }
            if (status == 500){
                alertDialog()
                binding.progressBar.visibility = View.INVISIBLE

                // 강아지를 쓰다듬었을 때 실행
                // position: 2
                val check = SaveSharedPreference.getAchieve(this)!!
                if(!check[2]) AchieveActivity().clearAchieve(this@SimManagePuppyActivity, 2)
            }
            true
        }
    }

    private fun alertDialog(){
        AlertDialog.Builder(this).run {
            setTitle("반려견 애정 주기 TIP")
            setIcon(R.drawable.maltese)
            setMessage("반려견은 애정을 자주 주어야 합니다.")
            setPositiveButton("확인", null)
            setCancelable(false)
            show()
        }.setCanceledOnTouchOutside(false) // 메시지 값 출력
        true
    }
}