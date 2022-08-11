package com.example.shape_up_2022

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.shape_up_2022.databinding.ActivitySimWalkMainBinding
import com.google.android.gms.maps.GoogleMap

class SimWalkMainActivity : AppCompatActivity() {
    lateinit var binding: ActivitySimWalkMainBinding
    private var mapfragment = SimWalkMainFragment()  // 지도 프래그먼트를 갖고 있음
    var googleMap: GoogleMap? = mapfragment.googleMap  // 프래그먼트의 구글맵

    private val fl: FrameLayout by lazy {
        findViewById(R.id.walk_main_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimWalkMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(mapfragment)  // 지도 프래그먼트 부착

        binding.btnReviewMain.setOnClickListener {  // 산책일기 목록
            val intent = Intent(this@SimWalkMainActivity, SimWalkReviewMainActivity::class.java)
            startActivity(intent)
        }
        binding.btnStartWalking.setOnClickListener {  // 산책 시작
            val intent = Intent(this@SimWalkMainActivity, SimWalkingActivity::class.java)
            //Log.d("mobileApp", "user 좌표 ${mapfragment.latitude}, ${mapfragment.longitude}")
            intent.putExtra("userlat", mapfragment.latitude)
            intent.putExtra("userlng", mapfragment.longitude)
            startActivity(intent)
            overridePendingTransition(0, 0);  // 액티비티 화면 전환 애니메이션 제거
        }

        // 툴바 적용
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.title = "반려견과 산책하기"
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(fl.id, fragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.walk_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_search -> {
                // 장소 검색 액티비티 이동
                val intent = Intent(this, SimWalkSearchActivity::class.java)
                startActivity(intent)
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}