package com.example.shape_up_2022.simulation

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.shape_up_2022.common.MainActivity
import com.example.shape_up_2022.databinding.ActivitySimBrushToothBinding

class SimBrushToothActivity : AppCompatActivity() {
    lateinit var binding: ActivitySimBrushToothBinding
    lateinit var alert: AlertDialog

    var level = 3  // 반려견의 양치 적응 단계 (1:치약, 2:거즈, 3:칫솔)

    var status = 0  // 진행도

    var moveX = 0f
    var moveY = 0f

    val areaX by lazy { measureX(binding.areaDoggyTeeth) }
    val areaY by lazy { measureY(binding.areaDoggyTeeth)+measureY(binding.areaMove)+280 }
    val areaH by lazy { measureH(binding.areaDoggyTeeth) }
    val areaW by lazy { measureW(binding.areaDoggyTeeth) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimBrushToothBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toothcancleBtn.visibility = View.GONE
        binding.progressTooth.max = 3000
        binding.progressTooth.progress = status  // 진행도 초기화

        alert = AlertDialog.Builder(this@SimBrushToothActivity)
            .setTitle("양치질을 마쳤어요.")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage("앞으로도 매일 관리해주세요.")
            .setPositiveButton("종료", eventHandler)
            .setCancelable(false)
            .create()

        /* 진행 단계에 따라 달라지도록 */
        when (level) {
            1 -> {
                binding.toothpasteBtn.isEnabled = true
                binding.toothgauzeBtn.visibility = View.GONE
                binding.toothbrushBtn.visibility = View.GONE
            }
            2 -> {
                binding.toothpasteBtn.isEnabled = true
                binding.toothgauzeBtn.isEnabled = true
                binding.toothbrushBtn.visibility = View.GONE
            }
            3 -> {
                binding.toothpasteBtn.isEnabled = true
                binding.toothgauzeBtn.isEnabled = true
                binding.toothbrushBtn.isEnabled = true
            }
        }

        /* 양치도구 꺼내기 & 집어넣기 */
        binding.toothpasteBtn.setOnClickListener {
            binding.toothcancleBtn.visibility = View.VISIBLE
            binding.toothpasteBtn.visibility = View.GONE
            binding.toothpasteMove.visibility = View.VISIBLE
            binding.toothgauzeBtn.visibility = View.VISIBLE
            binding.toothgauzeMove.visibility = View.GONE
            binding.toothbrushBtn.visibility = View.VISIBLE
            binding.toothbrushMove.visibility = View.GONE
        }

        binding.toothgauzeBtn.setOnClickListener {
            binding.toothcancleBtn.visibility = View.VISIBLE
            binding.toothpasteBtn.visibility = View.VISIBLE
            binding.toothpasteMove.visibility = View.GONE
            binding.toothgauzeBtn.visibility = View.GONE
            binding.toothgauzeMove.visibility = View.VISIBLE
            binding.toothbrushBtn.visibility = View.VISIBLE
            binding.toothbrushMove.visibility = View.GONE
        }

        binding.toothbrushBtn.setOnClickListener {
            binding.toothcancleBtn.visibility = View.VISIBLE
            binding.toothpasteBtn.visibility = View.VISIBLE
            binding.toothpasteMove.visibility = View.GONE
            binding.toothgauzeBtn.visibility = View.VISIBLE
            binding.toothgauzeMove.visibility = View.GONE
            binding.toothbrushBtn.visibility = View.GONE
            binding.toothbrushMove.visibility = View.VISIBLE
        }

        binding.toothcancleBtn.setOnClickListener {
            binding.toothcancleBtn.visibility = View.GONE
            binding.toothpasteBtn.visibility = View.VISIBLE
            binding.toothpasteMove.visibility = View.GONE
            binding.toothgauzeBtn.visibility = View.VISIBLE
            binding.toothgauzeMove.visibility = View.GONE
            binding.toothbrushBtn.visibility = View.VISIBLE
            binding.toothbrushMove.visibility = View.GONE
        }

        binding.toothpasteMove.setOnClickListener {
            // 치약 짜주기

            // 반려견이 치약을 먹는 모습

        }

        binding.toothgauzeMove.setOnTouchListener(moveTool)
        binding.toothbrushMove.setOnTouchListener(moveTool)

    }

    val moveTool = object : View.OnTouchListener {
        override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
            when(motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    moveX = view.x - motionEvent.rawX
                    moveY = view.y - motionEvent.rawY
                }

                MotionEvent.ACTION_MOVE -> {
                    view.animate()
                        .x(motionEvent.rawX + moveX)
                        .y(motionEvent.rawY + moveY)
                        .setDuration(0)
                        .start()
                    doBrushing(motionEvent.rawX, motionEvent.rawY)
                }
            }
            return true
        }
    }

    fun measureX(view: View): Float { return view.left.toFloat() }
    fun measureY(view: View): Float { return view.top.toFloat() }
    fun measureW(view: View): Float { return view.width.toFloat() }
    fun measureH(view: View): Float { return view.height.toFloat() }

    fun doBrushing(x: Float, y:Float) {
        if (status >= binding.progressTooth.max) {
            finishToothBrushing()
            return
        }
        if ( areaX < x && x < (areaX + areaW) &&
            areaY < y && y < (areaY + areaH)) {
            Log.d("mobileApp", "영역 내에서 움직임 $x , $y")
            binding.progressTooth.incrementProgressBy(1)
            status += 1

        }

/*
        if ( 480 < x && x < (480 + areaW) &&
            1300 < y && y < (1300 + areaH)) {
            Log.d("mobileApp", "영역 내에서 움직임 $x , $y")

            binding.progressTooth.incrementProgressBy(1)
            status += 1

        }
*/
        else {
            Log.d("mobileApp", "영역 밖에서 움직임 $x , $y")
        }
    }

    val eventHandler = object: DialogInterface.OnClickListener {
        override fun onClick(p0: DialogInterface?, p1: Int) {
            if(p1== DialogInterface.BUTTON_POSITIVE) {
                val intent = Intent(this@SimBrushToothActivity, MainActivity::class.java)
                finish()
            }
        }
    }

    fun finishToothBrushing() {
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

}