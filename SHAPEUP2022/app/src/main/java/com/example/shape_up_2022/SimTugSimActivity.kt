package com.example.shape_up_2022

import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.example.shape_up_2022.databinding.ActivityTugsimBinding
import kotlin.random.Random

class SimTugSimActivity : AppCompatActivity() {
    lateinit var binding : ActivityTugsimBinding
    var handler : Handler = Handler()
    var runnable : Runnable = Runnable{}
    var random = Random
    var status = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTugsimBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 액티비티 이동 리스너
        val activiy = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                val intent = Intent(this@SimTugSimActivity, SimToyPuppyActivity::class.java)
                startActivity(intent)
            }

        }
        // view를 띄운 후 실행하도록
        binding.tugDog.setOnTouchListener{ view, event ->
            when(event.action){
                MotionEvent.ACTION_MOVE -> {
                    binding.tugProgressBar.incrementProgressBy(1)
                    status += 1
                    var num = random.nextInt(5) + 100

                    MoveImage(binding.tugDog, binding.tugDog.x, binding.tugDog.y - num, 300L)
                    MoveImage(binding.tugDog, event.x, event.y, 300L)

                    // 프로그레스 바가 다 찼을 때 break
                    if(status == 500){
                        AlertDialog.Builder(this).run {
                            setTitle("반려견과 토그 가지고 놀기 완료!")
                            setIcon(R.drawable.maltese)
                            setMessage("반려견은 본능적으로 입에 물고 장난감을 가지고 노는 것을 좋아합니다!")
                            setPositiveButton("확인", activiy)
                            show()
                        }.setCanceledOnTouchOutside(true) // 메시지 값 출력
                    }
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