package com.example.shape_up_2022

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import java.util.*
import kotlin.concurrent.timer

var sysEnd3_:Int=0
val random3= Random()

var cx3=0f
var cy3=0f
val ra3=200f

var gametime3=100
var gametimec3= gametime3
var score3:Int=0
class SnacksimulationActivity : AppCompatActivity() {
    lateinit private var Gv3:GameView3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_feedsimulation)

        SetTimerm3()

        Gv3=GameView3(this)
        setContentView(Gv3)
    }

    override fun onDestroy() {
        super.onDestroy()
        sysEnd3_=1
    }
}

private class GameView3(context: Context): View(context){
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val paint= BitmapFactory.decodeResource(resources, R.drawable.snackinsim)
        val paint2= Paint()
        canvas?.drawBitmap(paint, cx3.toFloat(), cy3.toFloat(), null)
        paint2.setTextSize(100f);
        canvas?.drawText("횟수 $score3", 0f, 100f, paint2)
        paint.recycle()


        if(score3>4){
            Toast.makeText(context, "간식주기가 완료되었습니다.", Toast.LENGTH_SHORT).show()
            //onBackPressed();
        }

        invalidate()
    }

    override fun onTouchEvent(eventx: MotionEvent?): Boolean {
        var tcx =0f
        var tcy=0f
        var cxl=cx3-ra3
        var cxr=cx3+ ra3
        var cxt=cy3-ra3
        var cxb=cy3+ ra3

        tcx=eventx!!.x
        tcy=eventx!!.y

        when(eventx.actionMasked){
            MotionEvent.ACTION_DOWN->{
                if(tcx>cxl){
                    if(tcx<cxr){
                        if(tcy>cxt){
                            if(tcy<cxb){
                                score3++
                            }
                        }
                    }
                }
            }
        }

        return true
    }
}

fun GetRnd3(za:Int):Float{
    var rlt:Int=0
    var rltf:Float

    rlt=random3.nextInt(za)
    rltf=rlt.toFloat()

    return rltf
}

fun GameMainLoopm3(){
    gametimec3++
    if(gametimec3< gametime3){return}
    gametimec3=0

    cx3= GetRnd3(1000)
    cy3= GetRnd3(1000)
}

fun SetTimerm3(){
    timer(period=10,initialDelay=1000){
        GameMainLoopm3()
        if(sysEnd3_!=0){cancel()}
    }
}