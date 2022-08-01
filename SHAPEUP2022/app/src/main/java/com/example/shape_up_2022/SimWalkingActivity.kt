package com.example.shape_up_2022

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.shape_up_2022.databinding.ActivitySimWalkMainBinding
import com.example.shape_up_2022.databinding.ActivitySimWalkingBinding
import com.google.android.gms.maps.GoogleMap

class SimWalkingActivity : AppCompatActivity() {
    lateinit var binding: ActivitySimWalkingBinding
    private var mapfragment = SimWalkingFragment()  // 지도 프래그먼트를 갖고 있음
    var googleMap: GoogleMap? = mapfragment.googleMap  // 프래그먼트의 구글맵

    private val fl: FrameLayout by lazy {
        findViewById(R.id.walking_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimWalkingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(mapfragment)  // 지도 프래그먼트 부착

        binding.btnWalkingFinish.setOnClickListener {
            val intent = Intent(this@SimWalkingActivity, SimWalkReviewAddActivity::class.java)
            startActivity(intent)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(fl.id, fragment).commit()
    }

    // 뒤로가기 버튼을 누르면 토스트
    private var backPressedTime : Long = 0
    override fun onBackPressed() {
        Log.d("mobileApp", "뒤로가기")

        // 2초내 다시 클릭하면 액티비티 종료
        if (System.currentTimeMillis() - backPressedTime < 2000) {
            finish()
            overridePendingTransition(0, 0);  // 액티비티 화면 전환 애니메이션 제거
            return
        }

        // 처음 클릭 메시지
        Toast.makeText(this, "산책을 취소하려면 한 번 더 누르세요", Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()
    }
}