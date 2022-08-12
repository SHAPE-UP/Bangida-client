package com.example.shape_up_2022

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shape_up_2022.simulation.DogFeedsimulationActivity
import java.util.*
import kotlin.concurrent.timer


var sysEnd2_:Int=0
val random2= Random()

var cx2=0f
var cy2=0f
val ra2=200f

var gametime2=100
var gametimec2= gametime2
var score2:Int=0
class WatersimulationActivity : AppCompatActivity() {

    lateinit private var Gv2:GameView2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_feedsimulation)

        SetTimerm2()

        Gv2=GameView2(this)
        setContentView(Gv2)

        if(score2>4){
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sysEnd2_=1
    }
}

 class GameView2(context: Context): View(context){
     var width: Float = 0F
     var height: Float = 0F
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val options = BitmapFactory.Options()
        options.inSampleSize = 2

        val paint=BitmapFactory.decodeResource(resources, R.drawable.waterinsim, options)
        val paint2= Paint()
        canvas?.drawBitmap(paint, cx2.toFloat(), cy2.toFloat(), null)
        paint2.setTextSize(100f);
        canvas?.drawText("횟수 $score2", 0f, 100f, paint2)
        paint.recycle()

        // width, height 값 받기
        width = paint.width.toFloat()
        height = paint.height.toFloat()

        if(score2>4){
            Toast.makeText(context, "물주기가 완료되었습니다.", Toast.LENGTH_SHORT).show()
            this.visibility = View.GONE

            val intent = Intent(context, DogFeedsimulationActivity::class.java)
            (context as WatersimulationActivity).startActivity(intent)
            score2 = 0

        }

        invalidate()
    }

    override fun onTouchEvent(eventx: MotionEvent?): Boolean {
        var tcx =0f
        var tcy=0f
        var cxl=cx2-ra2
        var cxr=cx2+ ra2
        var cxt=cy2-ra2
        var cxb=cy2+ ra2

        tcx=eventx!!.x - width/2
        tcy=eventx!!.y- height/2

        when(eventx.actionMasked){
            MotionEvent.ACTION_DOWN->{
                if(tcx>cxl){
                    if(tcx<cxr){
                        if(tcy>cxt){
                            if(tcy<cxb){
                                score2++
                            }
                        }
                    }
                }
            }
        }

        return true
    }
}

fun GetRnd2(za:Int):Float{
    var rlt:Int=0
    var rltf:Float

    rlt=random2.nextInt(za)
    rltf=rlt.toFloat()

    return rltf
}

fun GameMainLoopm2(){
    gametimec2++
    if(gametimec2< gametime2){return}
    gametimec2=0

    cx2= GetRnd2(1000)
    cy2= GetRnd2(1000)
}

fun SetTimerm2(){
    timer(period=10,initialDelay=1000){
        GameMainLoopm2()
        if(sysEnd2_!=0){cancel()}
    }
}