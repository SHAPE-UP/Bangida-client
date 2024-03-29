package com.example.shape_up_2022.common

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.shape_up_2022.*
import com.example.shape_up_2022.achieve.AchieveActivity
import com.example.shape_up_2022.databinding.ActivityYoutubeBinding
import com.example.shape_up_2022.databinding.MainPageBinding
import com.example.shape_up_2022.retrofit.GetPetIDRes
import com.example.shape_up_2022.retrofit.GetPetInfoRes
import com.example.shape_up_2022.retrofit.MyApplication
import com.example.shape_up_2022.simulation.TestActivity
import com.example.shape_up_2022.todo.MainCalenderFragment
import com.example.shape_up_2022.todo.TodoActivity
import java.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private val binding by lazy { MainPageBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val bindingYouTube = ActivityYoutubeBinding.inflate(layoutInflater)

        //매주 같은 요일 모달창으로 호감도 검사 하라고 알림
        //day==1이면 일요일임
        val instance = Calendar.getInstance()
        val day = instance.get(Calendar.DAY_OF_WEEK)

        // eventHandler
        val alertEvent = object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(p1 == DialogInterface.BUTTON_NEUTRAL) {
                    val intent = Intent(this@MainActivity, PreferenceActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }
        }

        if(day==1){
            AlertDialog.Builder(this).run {
                setTitle("호감도 알림")
                setIcon(R.drawable.maltese)
                setMessage("이번주 호감도 검사는 진행하셨나요? \n아직 진행하지 못하셨다면 지금 바로 보호자님의 호감도를 검사해보세요~")
                setNeutralButton("호감도 검사", null)
                setPositiveButton("확인", alertEvent)
                show()
            }.setCanceledOnTouchOutside(false)
        }

        // 강아지 현재 상태, 정보
        getPetInfo()

        // 프래그먼트 연결 - 캘린더 프래그먼트
        viewFragment(MainCalenderFragment() , R.id.fragment_calender)
        
        
        // 탭바 연결
        setTabBar()

        // 하단 메뉴 연결
        setMenu()
    }

    override fun onStart() {
        super.onStart()

        val eventhandler = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(p1==DialogInterface.BUTTON_POSITIVE) {
                    val intent = Intent(this@MainActivity, MyPageActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);  // 액티비티 화면 전환 애니메이션 제거
                    finish()
                }
            }
        }
        var builder = AlertDialog.Builder(this)
            .setTitle("환영합니다!")
            .setIcon(R.drawable.ic_main)
            .setMessage("'반기다:반려견을 기다리며 하는 다짐'을 가족과 함께 사용해보세요. 먼저 가족 그룹을 만들어볼까요?")
            .setPositiveButton("설정하기", eventhandler)
            .setCancelable(false)

        if(SaveSharedPreference.getPetID(this)!! == ""){ // 반려견 입양 x
            binding.noFamHomeInfoDog.visibility = View.VISIBLE
            binding.homeInfoDog.visibility = View.GONE
            binding.btnSimulationMain.setOnClickListener {
                val intent = Intent(this@MainActivity, SimulationActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0);  // 액티비티 화면 전환 애니메이션 제거
                finish()
            }
            if(SaveSharedPreference.getFamliyID(this)!! == ""){ // 가족 그룹 가입 x
                builder.show()
            }
        }
    }

    // 탭바 연결
    private fun setTabBar() {
        binding.navHome.setOnClickListener {
            val intent_home = Intent(this, MainActivity::class.java)
            startActivity(intent_home)
            overridePendingTransition(0, 0);
            finish()
        }

        binding.navTodo.setOnClickListener {
            val intent_todo = Intent(this, TodoActivity::class.java)
            startActivity(intent_todo)
            overridePendingTransition(0, 0);
            finish()
        }

        binding.navSimulation.setOnClickListener {
            val intent_simul = Intent(this, SimulationActivity::class.java)
            startActivity(intent_simul)
            overridePendingTransition(0, 0);
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
            overridePendingTransition(0, 0);
            finish()
        }
    }

    private fun setMenu() {
        binding.menuBtn1.setOnClickListener {
            val intent = Intent(this, FaqActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        binding.menuBtn2.setOnClickListener {
            val intent = Intent(this@MainActivity, YoutubeActivity::class.java)
            intent.putExtra("search", "강아지 훈련")
            startActivity(intent)
        }
        binding.menuBtn3.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
        binding.menuBtn4.setOnClickListener {
            val intent_budget = Intent(this, BudgetActivity::class.java)
            startActivity(intent_budget)
            overridePendingTransition(0, 0)
        }
    }

    // 프래그먼트 연결
    private fun viewFragment(fragment : Fragment, location:Int){
        //Log.d("app_test", "viewFragment start")
        val fragmentManager : FragmentManager = supportFragmentManager
        val transaction : FragmentTransaction = fragmentManager.beginTransaction()

        transaction.add(location, fragment)
        transaction.commit()
    }

    /* 강아지 정보 업데이트 */
    private fun getPetInfo(){
        val petID = SaveSharedPreference.getPetID(this)!!
        //Log.d("mobileApp", "$petID")

        val call: Call<GetPetInfoRes> = MyApplication.networkServicePet.getPetInfo(
            petID = petID
        )

        call?.enqueue(object : Callback<GetPetInfoRes> {
            override fun onResponse(call: Call<GetPetInfoRes>, response: Response<GetPetInfoRes>) {
                if(response.isSuccessful){
                    //Log.d("mobileApp", "$response ${response.body()}")
                    if(response.body()!!.success){
                        // 강아지 이름 업데이트
                        binding.homeInfoDog.visibility = View.VISIBLE
                        binding.puppyProfileName.text = response.body()?.petInfo?.name
                    } else{
                        binding.homeInfoDog.visibility = View.GONE
                        binding.noFamHomeInfoDog.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<GetPetInfoRes>, t: Throwable) {
                Log.d("mobileApp", "onFailure $t")
                Toast.makeText(baseContext, "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
            }
        })
    }

}