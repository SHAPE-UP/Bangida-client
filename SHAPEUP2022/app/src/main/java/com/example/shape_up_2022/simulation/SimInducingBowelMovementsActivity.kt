package com.example.shape_up_2022.simulation

import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.example.shape_up_2022.R
import com.example.shape_up_2022.databinding.ActivityInducingbowelBinding
import com.google.android.youtube.player.internal.t
import com.google.android.youtube.player.internal.x
import com.google.android.youtube.player.internal.y
import java.util.*
import kotlin.collections.ArrayList

class SimInducingBowelMovementsActivity : FragmentActivity() {
    lateinit var binding : ActivityInducingbowelBinding
    var runnable : Runnable = Runnable{}
    var handler : Handler = Handler()
    //이미지, xy 좌표, 진동 선언
    var iv_smile: ImageView? = null
    var poop: ImageView? = null
    var previous_x = 0f
    var previous_y = 0f
    var mVibe: Vibrator? = null
    val BallList = ArrayList<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        /*액티비티 생성*/
        super.onCreate(savedInstanceState)
        /*activity_main.xml에서 정의된 화면 레이아웃을 액티비티에 출력*/
        binding= ActivityInducingbowelBinding.inflate(layoutInflater)
        var status = 0
        binding.progressBar.progress = status
        setContentView(binding.root)

        /*화면을 full screen로 만듬*/window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding.toyInfo.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("배변 훈련과 타이밍 안내")
                setIcon(R.drawable.puppy)
                setMessage("강아지 대소변 타이밍 [아침에 일어난 후],[음식물이나 물을 섭취한 후], [낮잠을 잔 후], [운동이나 놀이 후], [저녁 취짐 전]입니다. " +
                        "강아지를 배변판 위로 이동시켜주세요. 배변판으로 이동하면 배변판을 눌러주세요. ")
                setPositiveButton("확인", null)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(true) // 메시지 값 출력
            true
        }

        for(i in 0..3)
        {
            var bmp : Int = getResources().getIdentifier("maltese_p" + (i + 1), "drawable", packageName )
            var bitmap:Bitmap = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(getResources(), bmp),
                150,150, false)
            BallList.add(bitmap)

        }

        /*id가 "smile'인 이미지를 인식함*/iv_smile = findViewById<View>(R.id.smile) as ImageView
        mVibe = getSystemService(FragmentActivity.VIBRATOR_SERVICE) as Vibrator

        val poop : ImageView = findViewById<ImageView>(R.id.poop)
        poop.setOnLongClickListener {  event ->
            Log.d("obileApp","${binding.poop.width},${binding.poop.height}")
            var set: TreeSet<Int> = TreeSet()
            var x=100.00
            var y=100.00


            while (set.size < 1) {
                val random = Random()
                val num = random.nextInt(4)
                set.add(num)

                binding.progressBar.incrementProgressBy(2)
                status += 20
                //이미지 위치 처음으로 이동시키기
                MoveImage(binding.smile, x.toFloat(), y.toFloat(),1500L)
                AlertDialog.Builder(this).run {
                    setTitle("배변판으로 이동 완료")
                    setIcon(R.drawable.maltese)
                    setMessage("강아지를 칭찬하고 배변판을 치웠어요")
                    setPositiveButton("확인", null)
                    show()
                }.setCanceledOnTouchOutside(true)
            }
            if (status == 100){
                alertDialog()
                binding.progressBar.visibility = View.INVISIBLE
            }
            var nCount = 0
            for( i in set)
            {
                var tmpID : Int =  getResources().getIdentifier("smile" ,
                    "id", packageName )
                val imgView = findViewById<ImageView>(tmpID)
                imgView.setImageBitmap(BallList.get(i))
                nCount++
            }
            true



        }

    }


    /*터치 시에 시스템에 의해 호출됨*/
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {

            }
            MotionEvent.ACTION_MOVE -> {
                /*터치하고 있는 x,y 위치 인식*/
                val touch_x = event.x.toFloat()
                val touch_y = event.y.toFloat()
                MoveImage(binding.smile, touch_x, touch_y, 1500L)
            }
            MotionEvent.ACTION_UP -> {

            }

        }
        return false
    }

    //프로세스바(배변판) 다 차면 다음으로 이동, 다이얼로그
    private fun alertDialog(){
        fun toast_p() {
            Toast.makeText(this, "배변 점검으로 이동합니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,SimDefecationCharacteristicsActivity::class.java)
            startActivity(intent)
        }
        var dialog_listener = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when(which){
                    DialogInterface.BUTTON_POSITIVE ->
                        toast_p()
                }
            }
        }
        AlertDialog.Builder(this).run {
            setTitle("배변판 치우기 TIP")
            setIcon(R.drawable.puppy)
            setMessage("배변판,배변패드는 활동 구역에 설치하여 반려견에게 인식시켜야 합니다.")
            setPositiveButton("확인", dialog_listener)
            setCancelable(false)
            show()
        }.setCanceledOnTouchOutside(false) // 메시지 값 출력
        true
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

    override fun onStart() {
        super.onStart()

        var listener = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

            }
        }
        AlertDialog.Builder(this).run {
            setTitle("배변 훈련 체험활동")
            setIcon(R.drawable.puppy)
            setMessage("느낌표를 눌러 배변훈련 순서를 안내받고 시작하세요.")
            setPositiveButton("확인", listener)
            setCancelable(false)
            show()
        }.setCanceledOnTouchOutside(false) // 메시지 값 출력
        true
    }

}