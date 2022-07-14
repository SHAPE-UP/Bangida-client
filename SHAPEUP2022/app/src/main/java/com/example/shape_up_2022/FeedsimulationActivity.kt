package com.example.shape_up_2022

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import java.util.*
import kotlin.concurrent.timer

var sysEnd_:Int=0
val random= Random()

var cx=0f
var cy=0f
val ra=200f

var gametime=100
var gametimec= gametime
var score:Int=0
lateinit var intent9 : Intent


class FeedsimulationActivity : AppCompatActivity() {

    lateinit private var Gv:GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_feedsimulation)

        SetTimerm()

        Gv=GameView(this)
        setContentView(Gv)
        if(score>4){
            //intent9 = Intent(this, DogFeedsimulationActivity::class.java)
            //startActivity(intent9) Log.d("mobilaApp", "fhkdfjghsk")
            //Log.d("mobileApp", "fhkdfjghsk")
            //score=0
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        sysEnd_=1
    }
}

private class GameView(context: Context): View(context){
    var width: Float = 0F
    var height: Float = 0F
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val options = BitmapFactory.Options()
        options.inSampleSize = 2

        val paint=BitmapFactory.decodeResource(resources, R.drawable.foodinsim, options)
        val paint2= Paint()
        canvas?.drawBitmap(paint, cx.toFloat(), cy.toFloat(), null)
        paint2.setTextSize(100f);
        canvas?.drawText("횟수 $score", 0f, 100f, paint2)
        paint.recycle()

        // width, height 값 받기
        width = paint.width.toFloat()
        height = paint.height.toFloat()

        if(score>4){
            Toast.makeText(context, "밥주기가 완료되었습니다.", Toast.LENGTH_SHORT).show()
            this.visibility = View.GONE

            val intent = Intent(context, DogFeedsimulationActivity::class.java)
            (context as FeedsimulationActivity).startActivity(intent)
            score = 0

        }

        invalidate()
    }

    override fun onTouchEvent(eventx: MotionEvent?): Boolean {
        var tcx =0f
        var tcy=0f
        var cxl=cx-ra
        var cxr=cx+ ra
        var cxt=cy-ra
        var cxb=cy+ ra

        tcx=eventx!!.x - width/2
        tcy=eventx!!.y- height/2

        when(eventx.actionMasked){
            MotionEvent.ACTION_DOWN->{
                if(tcx>cxl){
                    if(tcx<cxr){
                        if(tcy>cxt){
                            if(tcy<cxb){
                                score++
                            }
                        }
                    }
                }
            }
        }

        return true
    }
}

fun GetRnd(za:Int):Float{
    var rlt:Int=0
    var rltf:Float

    rlt=random.nextInt(za)
    rltf=rlt.toFloat()

    return rltf
}

fun GameMainLoopm(){
    gametimec++
    if(gametimec< gametime){return}
    gametimec=0

    cx= GetRnd(1000)
    cy= GetRnd(1000)
}

fun SetTimerm(){
    timer(period=10,initialDelay=1000){
        GameMainLoopm()
        if(sysEnd_!=0){cancel()}
    }
}