package com.example.shape_up_2022

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.shape_up_2022.databinding.ActivityToyPuppyBinding
import com.example.shape_up_2022.databinding.ActivityYoutubeBinding


class SimToyPuppyActivity : AppCompatActivity() {
    lateinit var binding : ActivityToyPuppyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToyPuppyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bindingYouTube = ActivityYoutubeBinding.inflate(layoutInflater)

        // 다른 레이아웃의 뷰 사용

        // 장난감 정보: Info Alert
        binding.toyInfo.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("아직 강아지 장난감을 잘 모르신다면")
                setIcon(R.drawable.maltese)
                setMessage("강아지의 장난감 종류는 크게 공, 노즈워크, 인형, 터그가 있습니다. 장난감들을 눌러서 확인해보세요!")
                setPositiveButton("확인", null)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(true) // 메시지 값 출력
            true
        }

        // 액티비티 이동: 장난감 세탁하기
        binding.washingToy.setOnClickListener {
            val intent = Intent(this, SimWashingToyActivity::class.java)
            startActivity(intent)
        }

        // 영상 시청하기 이벤트: 클릭한 버튼에 대해 해당하는 text를 editText에 넣어서
        var vedioNoseWork = object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                val intent = Intent(this@SimToyPuppyActivity, YoutubeActivity::class.java)
                intent.putExtra("search", "강아지 노즈워크")
                startActivity(intent)
            }

        }

        var vedioDoll = object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                val intent = Intent(this@SimToyPuppyActivity, YoutubeActivity::class.java)
                intent.putExtra("search", "강아지 인형 훈련")
                startActivity(intent)
            }

        }

        var vedioTug = object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                val intent = Intent(this@SimToyPuppyActivity, YoutubeActivity::class.java)
                intent.putExtra("search", "강아지 터그놀이")
                startActivity(intent)
            }

        }

        var vedioBall = object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                val intent = Intent(this@SimToyPuppyActivity, YoutubeActivity::class.java)
                intent.putExtra("search", "강아지 공 훈련")
                startActivity(intent)
            }

        }

        // 액티비티 이동 이벤트: 클릭한 버튼에 대해 해당하는 액티비티 이동
        var activityTugSim = object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                val intent = Intent(this@SimToyPuppyActivity, SimTugSimActivity::class.java)
                startActivity(intent)
            }
        }

        var activityBallSim = object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                val intent = Intent(this@SimToyPuppyActivity, SimBallActivity::class.java)
                startActivity(intent)
            }
        }

        // Alert
        binding.toyBall.setOnClickListener{
            AlertDialog.Builder(this).run {
                setTitle("공")
                setIcon(R.drawable.maltese)
                setMessage("공은 강아지가 정말 좋아하는 장난감 중 하나입니다...")
                setPositiveButton("확인", null)
                setNeutralButton("강아지 놀아주기", activityBallSim)
                setNegativeButton("영상 시청하기", vedioBall)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(true) // 메시지 값 출력
            true
        }

        binding.toyNosewalk.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("노즈워크")
                setIcon(R.drawable.maltese)
                setMessage("노즈워크는 강아지가 좋아하는 장난감 중 하나입니다...")
                setPositiveButton("확인", null)
                setNegativeButton("영상 시청하기", vedioNoseWork)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(true) // 메시지 값 출력
            true
        }

        binding.toyDoll.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("인형")
                setIcon(R.drawable.maltese)
                setMessage("인형은 강아지가 좋아하는 장난감 중 하나입니다...")
                setPositiveButton("확인", null)
                setNegativeButton("영상 시청하기", vedioDoll)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(true) // 메시지 값 출력
            true
        }

        binding.toyTug.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("터그")
                setIcon(R.drawable.maltese)
                setMessage("터그는 강아지가 좋아하는 장난감 중 하나입니다...")
                setPositiveButton("확인", null)
                setNeutralButton("강아지 놀아주기", activityTugSim)
                setNegativeButton("영상 시청하기", vedioTug)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(true) // 메시지 값 출력
            true
        }

    }
}