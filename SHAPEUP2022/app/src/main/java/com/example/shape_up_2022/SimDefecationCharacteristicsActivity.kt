package com.example.shape_up_2022

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.util.*
import kotlin.collections.ArrayList

class SimDefecationCharacteristicsActivity : AppCompatActivity() {
    val BallList = ArrayList<Bitmap>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_defecationcharacteristics)

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
        val btnA : Button = findViewById<Button>(R.id.btA)

        btnA.setOnClickListener {

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
        }
//체크박스 값 가져오기
        //1.체크박스를 하나하나 가져옴

//사진이랑 체크박스 일치 여부..
    }

// 다이얼로그로 정답 표시? 다르게..
    //private fun alertDialog(){

    //}
}