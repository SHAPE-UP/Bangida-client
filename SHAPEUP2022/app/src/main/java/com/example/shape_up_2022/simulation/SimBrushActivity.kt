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
import com.example.shape_up_2022.databinding.ActivitySimBrushBinding

class SimBrushActivity : AppCompatActivity() {
    lateinit var binding: ActivitySimBrushBinding
    lateinit var alert: AlertDialog

    var status = 0  // 진행도

    var moveX = 0f
    var moveY = 0f

    val areaX by lazy { measureX(binding.areaBrushFace) }
    val areaY by lazy { measureY(binding.areaBrushFace)+measureY(binding.areaMove)+280 }
    val areaH by lazy { measureH(binding.areaBrushFace) }
    val areaW by lazy { measureW(binding.areaBrushFace) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimBrushBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.brushcancleBtn.visibility = View.GONE
        binding.progressBrush.max = 3000
        binding.progressBrush.progress = status  // 진행도 초기화

        alert = AlertDialog.Builder(this@SimBrushActivity)
            .setTitle("빗질을 마쳤어요.")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage("앞으로도 매일 관리해주세요.")
            .setPositiveButton("종료", eventHandler)
            .setCancelable(false)
            .create()

        /* 빗질도구 꺼내기 & 집어넣기 */
        binding.slickerbrushBtn.setOnClickListener {
            binding.brushcancleBtn.visibility = View.VISIBLE
            binding.slickerbrushBtn.visibility = View.GONE
            binding.slickerbrushMove.visibility = View.VISIBLE
            binding.pinbrushBtn.visibility = View.VISIBLE
            binding.pinbrushMove.visibility = View.GONE
            binding.combBtn.visibility = View.VISIBLE
            binding.combMove.visibility = View.GONE
            binding.facecombBtn.visibility = View.VISIBLE
            binding.facecombMove.visibility = View.GONE
        }

        binding.pinbrushBtn.setOnClickListener {
            binding.brushcancleBtn.visibility = View.VISIBLE
            binding.slickerbrushBtn.visibility = View.VISIBLE
            binding.slickerbrushMove.visibility = View.GONE
            binding.pinbrushBtn.visibility = View.GONE
            binding.pinbrushMove.visibility = View.VISIBLE
            binding.combBtn.visibility = View.VISIBLE
            binding.combMove.visibility = View.GONE
            binding.facecombBtn.visibility = View.VISIBLE
            binding.facecombMove.visibility = View.GONE
        }

        binding.combBtn.setOnClickListener {
            binding.brushcancleBtn.visibility = View.VISIBLE
            binding.slickerbrushBtn.visibility = View.VISIBLE
            binding.slickerbrushMove.visibility = View.GONE
            binding.pinbrushBtn.visibility = View.VISIBLE
            binding.pinbrushMove.visibility = View.GONE
            binding.combBtn.visibility = View.GONE
            binding.combMove.visibility = View.VISIBLE
            binding.facecombBtn.visibility = View.VISIBLE
            binding.facecombMove.visibility = View.GONE
        }

        binding.facecombBtn.setOnClickListener {
            binding.brushcancleBtn.visibility = View.VISIBLE
            binding.slickerbrushBtn.visibility = View.VISIBLE
            binding.slickerbrushMove.visibility = View.GONE
            binding.pinbrushBtn.visibility = View.VISIBLE
            binding.pinbrushMove.visibility = View.GONE
            binding.combBtn.visibility = View.VISIBLE
            binding.combMove.visibility = View.GONE
            binding.facecombBtn.visibility = View.GONE
            binding.facecombMove.visibility = View.VISIBLE
        }

        binding.brushcancleBtn.setOnClickListener {
            binding.brushcancleBtn.visibility = View.GONE
            binding.slickerbrushBtn.visibility = View.VISIBLE
            binding.slickerbrushMove.visibility = View.GONE
            binding.pinbrushBtn.visibility = View.VISIBLE
            binding.pinbrushMove.visibility = View.GONE
            binding.combBtn.visibility = View.VISIBLE
            binding.combMove.visibility = View.GONE
            binding.facecombBtn.visibility = View.VISIBLE
            binding.facecombMove.visibility = View.GONE
        }

        binding.pinbrushMove.setOnTouchListener(moveTool)
        binding.slickerbrushMove.setOnTouchListener(moveTool)
        binding.combMove.setOnTouchListener(moveTool)
        binding.facecombMove.setOnTouchListener(moveTool)
    }


    val moveTool = object : View.OnTouchListener {
        override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
            if (status == binding.progressBrush.max) {
                finishBrushing()
                return true
            }
            when (motionEvent.action) {
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

    fun doBrushing(x: Float, y: Float) {
        if (areaX < x && x < (areaX + areaW) &&
            areaY < y && y < (areaY + areaH)
        ) {
            Log.d("mobileApp", "영역 내에서 움직임 $x , $y")
            binding.progressBrush.incrementProgressBy(1)
            status += 1
        }
    }

    val eventHandler = object: DialogInterface.OnClickListener {
        override fun onClick(p0: DialogInterface?, p1: Int) {
            if(p1== DialogInterface.BUTTON_POSITIVE) {
                val intent = Intent(this@SimBrushActivity, MainActivity::class.java)
                finish()
            }
        }
    }

    fun finishBrushing() {
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

}