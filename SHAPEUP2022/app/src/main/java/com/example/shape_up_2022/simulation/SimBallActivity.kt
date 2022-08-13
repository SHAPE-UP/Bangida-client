package com.example.shape_up_2022.simulation

import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.example.shape_up_2022.R
import com.example.shape_up_2022.databinding.ActivityBallSimBinding

class SimBallActivity : AppCompatActivity() {

    lateinit var binding : ActivityBallSimBinding
    var handler : Handler = Handler()
    var runnable : Runnable = Runnable{}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBallSimBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var score: Int = 0

        // 액티비티 이동 리스너
        val activiy = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                val intent = Intent(this@SimBallActivity, SimToyPuppyActivity::class.java)
                startActivity(intent)
            }
        }

        // 위치를 클릭: 공 이동
        binding.ballSimLayout.setOnTouchListener { view, event ->
            when (event?.action) {
                MotionEvent.ACTION_UP -> {
                    var x = event.x - (binding.ball.width / 2)
                    var y = event.y - (binding.ball.height / 2)
                    Log.d("mobileApp", "${event.x}, ${event.y}")
                    MoveImage(binding.ball, x, y, 500L)
                    // 이미지 이동
                    Handler().postDelayed({
                        MoveImage(binding.ballBog, x, y, 1500L)
                    }, 500)
                    // 스코어 증가
                    Handler().postDelayed({
                        score++
                        binding.ballScore.text = score.toString()
                        if (score == 10) {
                            AlertDialog.Builder(this).run {
                                setTitle("반려견과 공 가지고 놀기 완료!")
                                setIcon(R.drawable.maltese)
                                setMessage("강아지는 통통 튀어다니는 공을 정말 좋아합니다!")
                                setPositiveButton("확인", activiy)
                                show()
                            }.setCanceledOnTouchOutside(true)
                        }
                    }, 1000)
                }
            }
            true
        }

    }


    private fun MoveImage(image : ImageView, moveX: Float, moveY: Float, duration1: Long){
        runnable = object : Runnable {
            override fun run() {
                ObjectAnimator.ofFloat(image, "x", moveX).apply{
                    duration = duration1
                    start()
                }
                ObjectAnimator.ofFloat(image, "y", moveY).apply{
                    duration = duration1
                    start()
                }
                //handler.postDelayed(runnable, duration1)
            }
        }
        handler.post(runnable)
    }
}