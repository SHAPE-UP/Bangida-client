package com.example.shape_up_2022.simulation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.shape_up_2022.R
import com.example.shape_up_2022.databinding.ActivityCleaningbedBinding
import kotlin.random.Random

class SimCleaningbedActivity : AppCompatActivity() {

    lateinit var binding: ActivityCleaningbedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCleaningbedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 튜토리얼
        binding.tutoBed.setOnClickListener {
            binding.tutoBed.visibility = View.GONE
        }

        val layout : RelativeLayout = findViewById(R.id.layout_clean)
        val random = Random

        // 5부터 15까지
        val count = random.nextInt(5) + 5
        var delDusts = count

        // 먼지를 클릭하면 먼지가 사라짐
        val cleanDustEvent = View.OnClickListener{
            delDusts--
            it.visibility = View.INVISIBLE

            // 먼지를 다 없애면 알림창이 뜸
            if (delDusts < 0){
                alertDialog()
            }
        }

        // 랜덤으로 먼지 생성
        for(i in 0..count){
            val locX = random.nextInt(900) + 16 + random.nextFloat() // x값
            val locY = random.nextInt(400) + 800 + random.nextFloat() // y값
            val sizeY = random.nextInt(300) + 100 // dust size

            val image = ImageView(this@SimCleaningbedActivity)
            val imageLayoutParams = RelativeLayout.LayoutParams(
                150,
                sizeY,
            )
            image.translationX = locX
            image.translationY = locY
            image.setOnClickListener(cleanDustEvent)
            image.layoutParams = imageLayoutParams
            Glide.with(this@SimCleaningbedActivity).load(R.drawable.dust).into(image)
            layout.addView(image)
        }
    }

    private fun alertDialog(){
        AlertDialog.Builder(this).run {
            setTitle("반려견 관리 TIP")
            setIcon(R.drawable.maltese)
            setMessage("반려견의 침대를 자주 관리하는 것은 중요합니다.")
            setPositiveButton("확인", null)
            setCancelable(false)
            show()
        }.setCanceledOnTouchOutside(false) // 메시지 값 출력
        true
    }
}