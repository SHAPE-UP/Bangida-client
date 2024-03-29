package com.example.shape_up_2022.achieve

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import com.example.shape_up_2022.adapter.AchieveAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.shape_up_2022.*
import com.example.shape_up_2022.common.MainActivity
import com.example.shape_up_2022.common.MyPageActivity
import com.example.shape_up_2022.common.SaveSharedPreference
import com.example.shape_up_2022.databinding.ActivityAchieveBinding
import com.example.shape_up_2022.common.SimulationActivity
import com.example.shape_up_2022.retrofit.CompleteAchieveRes
import com.example.shape_up_2022.retrofit.GetPetInfoRes
import com.example.shape_up_2022.retrofit.MyApplication
import com.example.shape_up_2022.todo.TodoActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AchieveActivity : AppCompatActivity() {

    lateinit var binding: ActivityAchieveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchieveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* 준비도: progressBar */
        if(SaveSharedPreference.getAchieve(this) == null)
            binding.pbAchieveTodo.progress = 0
        else DisplayProgress()

        /*
        binding.btn.setOnClickListener { view ->
            binding.pbAchieveTodo.incrementProgressBy(5)
        }

        binding.btn2.setOnClickListener { view ->
            binding.pbAchieveTodo.incrementProgressBy(-5)
        }

        binding.btn3.setOnClickListener { view ->
            binding.pbAchieveTodo.progress = 50
        }
        */
        
        /* 반려견 이름 업데이트 */
        getPetInfo()
        
        binding.achieveTabs.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        binding.achievePager.adapter = AchieveAdapter(this)

        TabLayoutMediator(binding.achieveTabs, binding.achievePager) {
                tab,position->
            when(position){
                0->tab.text = "진행도"
                1->tab.text = "성실도"
                2->tab.text = "호감도"
            }
        }.attach()

        // 탭바 연결
        setTabBar()
    }

    override fun onStart() {
        super.onStart()

        /* 가족 그룹에 가입되어 있지 않은 상태일 때 */
        val eventHandler = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(p1 == DialogInterface.BUTTON_POSITIVE) {
                    val intent = Intent(this@AchieveActivity, MyPageActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);  // 액티비티 화면 전환 애니메이션 제거
                    finish()
                }
            }
        }
        var builder = AlertDialog.Builder(this)
            .setTitle("가족 그룹에 가입되어 있지 않은 사용자")
            .setIcon(R.drawable.ic_main)
            .setMessage("가족 그룹에 먼저 가입해주세요.")
            .setPositiveButton("확인", eventHandler)
            .setCancelable(false)

        if(SaveSharedPreference.getFamliyID(this)!! == "") {
            builder.show()
        } else { // 가족 그룹 o, 강아지 입양 x

        }
    }

    /* 강아지 정보 업데이트 */
    private fun getPetInfo(){
        val petID = SaveSharedPreference.getPetID(this)!!
        Log.d("mobileApp", "$petID")

        val call: Call<GetPetInfoRes> = MyApplication.networkServicePet.getPetInfo(
            petID = petID
        )

        call?.enqueue(object : Callback<GetPetInfoRes> {
            override fun onResponse(call: Call<GetPetInfoRes>, response: Response<GetPetInfoRes>) {
                if(response.isSuccessful){
                    //Log.d("mobileApp", "$response ${response.body()}")
                    if(response.body()!!.success){
                        // 강아지 이름 업데이트
                        binding.achievePetName.text = response.body()?.petInfo?.name
                    }
                }
            }

            override fun onFailure(call: Call<GetPetInfoRes>, t: Throwable) {
                Log.d("mobileApp", "onFailure $t")
                Toast.makeText(baseContext, "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setTabBar() {
        binding.navHome.setOnClickListener {
            val intent_home = Intent(this, MainActivity::class.java)
            startActivity(intent_home)
            overridePendingTransition(0, 0)
            finish()
        }
        binding.navTodo.setOnClickListener {
            val intent_todo = Intent(this, TodoActivity::class.java)
            startActivity(intent_todo)
            overridePendingTransition(0, 0)
            finish()
        }
        binding.navSimulation.setOnClickListener {
            val intent_simul = Intent(this, SimulationActivity::class.java)
            startActivity(intent_simul)
            overridePendingTransition(0, 0)
            finish()
        }
        binding.navAchievement.setOnClickListener {
            val intent_achieve = Intent(this, AchieveActivity::class.java)
            startActivity(intent_achieve)
            overridePendingTransition(0, 0)
            finish()
        }
        binding.navMypage.setOnClickListener {
            val intent_mypage = Intent(this, MyPageActivity::class.java)
            startActivity(intent_mypage)
            overridePendingTransition(0, 0)
            finish()
        }
    }

    /* 업적을 달성했을 때 */
        /* 준비도: progressBar */
        private fun DisplayProgress(){
            var clearCount = 0
            //binding = ActivityAchieveBinding.inflate(layoutInflater)
            val progress = binding.pbAchieveTodo

            if(SaveSharedPreference.getAchieve(this)!! == null){
                // 준비도 = 0
                progress.progress = 0
            } else{
                val checkedArray = SaveSharedPreference.getAchieve(this)!!
                for(i in 0 until checkedArray.size){
                    if(checkedArray[i]) clearCount++
                    if(i == checkedArray.size - 1){ // 마지막 인덱스일 때
                        // 준비도 반영하기
                        val ratio = (clearCount.toFloat() / 14)
                        Log.d("mobileApp", "$ratio")
                        progress.progress = (ratio * 100).toInt()
                    }
                }
            }

        }

        fun clearAchieve(context: Context, position: Int){
            // 프리퍼런스 값 바꾸기
            Log.d("mobileApp", "clearAchieve!")
            val temp = SaveSharedPreference.getAchieve(context)!!
            temp[position] = true
            SaveSharedPreference.setAchieve(context, temp)

            // 라우터 연결, 업데이트
            val call: Call<CompleteAchieveRes> = MyApplication.networkServiceUsers.setCheckedTrue(
                userID = SaveSharedPreference.getUserID(context)!!, position = position
             )

            call?.enqueue(object : Callback<CompleteAchieveRes> {
                override fun onResponse(call: Call<CompleteAchieveRes>, response: Response<CompleteAchieveRes>) {
                    if(response.isSuccessful){
                        Log.d("mobileApp", "$response ${response.body()}")
                        if(response.body()!!.success){
                            Log.d("mobileApp", "업데이트 완료!")

                        }
                    }
                }

                override fun onFailure(call: Call<CompleteAchieveRes>, t: Throwable) {
                    Log.d("mobileApp", "onFailure $t")
                    //Toast.makeText(baseContext, "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
                }
            })

            // 진행도 업데이트
            //DisplayProgress()

        }

}