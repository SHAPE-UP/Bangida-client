package com.example.shape_up_2022.common

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.shape_up_2022.*
import com.example.shape_up_2022.achieve.AchieveActivity
import com.example.shape_up_2022.databinding.ActivityYoutubeBinding
import com.example.shape_up_2022.databinding.MainPageBinding
import com.example.shape_up_2022.simulation.TestActivity
import com.example.shape_up_2022.todo.MainCalenderFragment
import com.example.shape_up_2022.todo.TodoActivity
import java.util.*


class MainActivity : AppCompatActivity() {
    private val binding by lazy { MainPageBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val bindingYouTube = ActivityYoutubeBinding.inflate(layoutInflater)

        //매주 같은 요일 모달창으로 호감도 검사 하라고 알림
        //day==1이면 일요일임
        val instance = Calendar.getInstance()
        val day = instance.get(Calendar.DATE)

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


        // 프래그먼트 연결 - 캘린더 프래그먼트
        viewFragment(MainCalenderFragment() , R.id.fragment_calender)
        
        
        // 탭바 연결
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
        // 하단 메뉴
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
            //finish()
        }
    }

    override fun onStart() {
        super.onStart()
        /* 테스트하지 않은 사용자를 돌려보내는 다이얼로그 */
        // 취소(메인으로), 테스트(테스트 시작)
        val eventhandler = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(p1==DialogInterface.BUTTON_POSITIVE) {
                    val intent = Intent(this@MainActivity, TestActivity::class.java)
                    startActivity(intent)
                    finish()
                }
//                else if (p1==DialogInterface.BUTTON_NEGATIVE) {
//                    val intent = Intent(this@MainActivity, MainActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
            }
        }
        var builder = AlertDialog.Builder(this)
            .setTitle("테스트하지 않은 사용자")
            .setMessage("반려견 케어 성향 점검을 먼저 진행하세요.")
            .setPositiveButton("테스트", eventhandler)
            .setCancelable(false)

        if(!SaveSharedPreference.getUserTested(this)!!){ // tested == false
            builder.show()

        } else{
            if(SaveSharedPreference.getFamliyID(this)!! == ""){ // familyID == ""
                binding.noFamHomeInfoDog.visibility = View.VISIBLE
                binding.homeInfoDog.visibility = View.GONE
            } else{
                binding.noFamHomeInfoDog.visibility = View.GONE
                binding.homeInfoDog.visibility = View.VISIBLE
            }

        }
    }

    // 프래그먼트 연결
    private fun viewFragment(fragment : Fragment, location:Int){
        Log.d("app_test", "viewFragment start")
        val fragmentManager : FragmentManager = supportFragmentManager
        val transaction : FragmentTransaction = fragmentManager.beginTransaction()

        transaction.add(location, fragment)
        transaction.commit()
    }
}