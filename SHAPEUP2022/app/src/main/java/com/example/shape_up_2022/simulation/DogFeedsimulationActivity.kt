package com.example.shape_up_2022.simulation

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.shape_up_2022.WatersimulationActivity
import com.example.shape_up_2022.databinding.ActivityDogFeedsimulationBinding

class DogFeedsimulationActivity : AppCompatActivity() {
    lateinit var binding: ActivityDogFeedsimulationBinding
    private lateinit var intent1 : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_dog_feedsimulation)
        binding = ActivityDogFeedsimulationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fsim.setOnClickListener{
            intent1 = Intent(this, FeedsimulationActivity::class.java)
            startActivity(intent1)
        }
        binding.wsim.setOnClickListener{
            intent1 = Intent(this, WatersimulationActivity::class.java)
            startActivity(intent1)
        }
        binding.ssim.setOnClickListener{
            intent1 = Intent(this, SnacksimulationActivity::class.java)
            startActivity(intent1)
        }
        val eventHandler=object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int){
                if(p1==DialogInterface.BUTTON_POSITIVE){
                    Log.d("mobileApp", "positive button")
                }
                else if(p1==DialogInterface.BUTTON_NEGATIVE){
                    Log.d("mobileApp", "negative button")
                }
            }
        }

        binding.foodtip.setOnClickListener{
            AlertDialog.Builder(this).run{
                setTitle("반려견 먹이주기 TIP")
                setIcon(android.R.drawable.ic_dialog_info)
                setMessage("생후 6-12주: 4번\n" +
                        "생후 3-6개월: 3번(체중의 4~5% 이내)\n"+
                        "생후 6-12개월: 2번(체중의 3~4% 이내)\n" +
                        "성견: 2번(체중의 2~2.5% 이내)")
                setPositiveButton("YES", eventHandler)
                setNegativeButton("NO", eventHandler)
                setNeutralButton("MORE", null)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(false)
        }

        binding.watertip.setOnClickListener{
            AlertDialog.Builder(this).run{
                setTitle("반려견 수분주기 TIP")
                setIcon(android.R.drawable.ic_dialog_info)
                setMessage("1. 식사할 때 물을 함께 급여한다.\n" +
                        "2. 수분 부족의 경우 물에 닭육수 등으로 섞어서 급여한다.\n"+
                        "3. 외출 시 물을 준비한다.\n")
                setPositiveButton("YES", eventHandler)
                setNegativeButton("NO", eventHandler)
                setNeutralButton("MORE", null)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(false)
        }

        binding.snacktip.setOnClickListener{
            AlertDialog.Builder(this).run{
                setTitle("반려견 간식주기 TIP")
                setIcon(android.R.drawable.ic_dialog_info)
                setMessage("*생후 4개월 전에는 간식을 주지 마세요\n" +
                        "\n"+
                        "적응 기간이 끝난 강아지에게는 1주일에 한 가지 정도의 간식을 사료량의 1/10미만으로 급여하고 식이과민증과 소화기 증상이 나타나지 않을 경우 한 가지씩 추가하여 급여합니다.\n")
                setPositiveButton("YES", eventHandler)
                setNegativeButton("NO", eventHandler)
                setNeutralButton("MORE", null)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(false)
        }


    }
}