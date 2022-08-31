package com.example.shape_up_2022.common

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.shape_up_2022.R
import com.example.shape_up_2022.achieve.AchieveActivity
import com.example.shape_up_2022.databinding.SimulationMainBinding
import com.example.shape_up_2022.simulation.*
import com.example.shape_up_2022.todo.TodoActivity

class SimulationActivity : AppCompatActivity() {
    lateinit var binding: SimulationMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SimulationMainBinding.inflate(layoutInflater)
        if (false) { // 시뮬레이션을 시작하지 않은 유저라면(반려견이 없는지 검사)
            binding.simNotStarted.visibility= View.VISIBLE
            binding.simStarted.visibility= View.INVISIBLE
            binding.simStartBtn.setOnClickListener {
                //버튼 클릭하면 시뮬레이션 시작 액티비티로 전환
                //val intent = Intent(this, 시작액티비티::class.java)
                //startActivity(intent)
            }
        }

        setContentView(binding.root)

        // 실내/외출 모드 전환

        binding.btnout.setOnClickListener {
            binding.btnout.visibility = View.INVISIBLE
            binding.indoor.visibility = View.INVISIBLE
            binding.btnin.visibility = View.VISIBLE
            binding.outdoor.visibility = View.VISIBLE
        }

        binding.btnin.setOnClickListener {
            binding.btnin.visibility = View.INVISIBLE
            binding.outdoor.visibility = View.INVISIBLE
            binding.btnout.visibility = View.VISIBLE
            binding.indoor.visibility = View.VISIBLE
        }

        // 시뮬레이션: 강아지 관리
        binding.btnManagement.setOnClickListener {
            val intent = Intent(this, SimManagePuppyActivity::class.java)
            startActivity(intent)
        }

        // 시뮬레이션: 먹이 주기
        binding.btnFeed.setOnClickListener {
            val intent = Intent(this, DogFeedsimulationActivity::class.java)
            startActivity(intent)
        }

        // 시뮬레이션: 산책
        binding.takeawalk.setOnClickListener {
            val intent = Intent(this, SimWalkMainActivity::class.java)
            startActivity(intent)
        }

        // 시뮬레이션: 배변 관리
        binding.simPoop.setOnClickListener {
            val intent = Intent(this, SimInducingBowelMovementsActivity::class.java)
            startActivity(intent)
        }

        // 시뮬레이션: 위생 관리/목욕
        binding.simHygiene.setOnClickListener {
            val intent = Intent(this, SimHygieneActivity::class.java)
            startActivity(intent)
        }

        // 시뮬레이션: 건강 관리
        binding.simHealth.setOnClickListener {
            val intent = Intent(this, SimHealthActivity::class.java)
            startActivity(intent)
        }

        // 시뮬레이션: 음성 훈련
        binding.simTraining.setOnClickListener {
            val intent = Intent(this, SimTrainingActivity::class.java)
            startActivity(intent)
        }

        // 시뮬레이션: 병원
        binding.simHospital.setOnClickListener {
            val intent = Intent(this, HospitalinfoActivity::class.java)
            startActivity(intent)
        }

        // 시뮬레이션: 미용
        binding.simBeauty.setOnClickListener {
            val intent = Intent(this, SimBeautyActivity::class.java)
            startActivity(intent)
        }

        // 탭바 연결
        binding.navHome.setOnClickListener {
            val intent_home = Intent(this, MainActivity::class.java)
            startActivity(intent_home)
            overridePendingTransition(0, 0)
            overridePendingTransition(0, 0)
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

    override fun onStart() {
        super.onStart()


        val eventHandler = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(p1 == DialogInterface.BUTTON_POSITIVE) {
                    val intent = Intent(this@SimulationActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        var builder = AlertDialog.Builder(this)
            .setTitle("가족 그룹에 가입되어 있지 않은 사용자")
            .setIcon(R.drawable.maltese)
            .setMessage("가족 그룹에 먼저 가입해주세요.")
            .setPositiveButton("확인", eventHandler)
            .setCancelable(false)

        //if(SaveSharedPreference.getFamliyID(this)!! == ""){
        //    builder.show()
        //}
    }
}