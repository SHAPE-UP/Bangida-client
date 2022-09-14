package com.example.shape_up_2022.simulation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.example.shape_up_2022.R
import com.example.shape_up_2022.databinding.ActivityDefecationcharacteristicsBinding
import java.util.*
import kotlin.collections.ArrayList

class SimDefecationCActivity : AppCompatActivity() {
    lateinit var binding :ActivityDefecationcharacteristicsBinding
    //val BallList = ArrayList<Bitmap>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDefecationcharacteristicsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        //이미지 비트맵
        for(i in 0..6)
        {
            var bmp : Int = getResources().getIdentifier("poop" + (i + 1), "drawable", packageName )
            var bitmap:Bitmap = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(getResources(), bmp),
                70,70, false)
            BallList.add(bitmap)

        }
        // 랜덤 이미지 1개

            var set: TreeSet<Int> = TreeSet()

            while (set.size < 1) {
                val random = Random()
                val num = random.nextInt(7)
                set.add(num)
            }
            var nCount = 0
            for( i in set)
            {
                var tmpID : Int =  getResources().getIdentifier("ballView" + (nCount + 1),
                    "id", packageName )
                val imgView = findViewById<ImageView>(tmpID)
                imgView.setImageBitmap(BallList.get(i))
                nCount++
            }

            AlertDialog.Builder(this).run {
                setTitle("반려견 배변 TIP")
                setIcon(R.drawable.puppy)
                setMessage("반려견 배변에 따른 건강 상태를 파악해야 합니다.")
                setPositiveButton("확인", null)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(false) // 메시지 값 출력
            true
        */

        binding.btnO.setOnClickListener{
            AlertDialog.Builder(this).run {
                setTitle("정답")
                setMessage("자세한 설명이 필요하다면 자주하는 질문을 확인해주세요.")
                setPositiveButton("확인", null)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(true)
            true
        }

        binding.btnx.setOnClickListener{
            AlertDialog.Builder(this).run {
                setTitle("오답")
                setMessage("오답을 공부하고 싶다면 자주하는 질문을 확인해주세요.")
                setPositiveButton("확인", null)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(true)
            true
        }

    }




}