package com.example.shape_up_2022

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
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
import com.google.android.youtube.player.internal.e
import com.google.android.youtube.player.internal.m
import java.io.*
import java.lang.Exception

class SimWalkingActivity : AppCompatActivity() {
    lateinit var binding: ActivitySimWalkingBinding
    private var mapfragment = SimWalkingFragment()  // 지도 프래그먼트를 갖고 있음
    lateinit var googleMap: GoogleMap  // 프래그먼트의 구글맵 (액티비티 실행 시 바로 초기화되지 않음. 주의!)

    private val fl: FrameLayout by lazy {
        findViewById(R.id.walking_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimWalkingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(mapfragment)  // 지도 프래그먼트 부착

        binding.btnWalkingFinish.setOnClickListener {
            googleMap = mapfragment.googleMap!! // 캡쳐할 지도 프래그먼트 가져오기
            CaptureMapScreen()  // 캡쳐 및 이동 (!!!!!인텐트 생성과정을 포함하고 있음!!!!! 수정 필요)
        }
    }

    private fun CaptureMapScreen() {
        Log.d("mobileApp", "캡쳐버튼 누름")
        val callback = GoogleMap.SnapshotReadyCallback() { snapshot ->
            var bitmap = Bitmap.createBitmap(snapshot!!, 0, 0, snapshot.width, snapshot.height - 400, Matrix() ,false)

            try {
                var bytes = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, bytes)  // PNG 파일을 50 Quality로 지정
                Log.d("mobileApp", bitmap.toString())

                var byteArray = bytes.toByteArray()  // 이미지 정보를 담은 byteArray

                // ReviewAdd액티비티로 이동하는 인텐트
                var intentReview = Intent(this@SimWalkingActivity, SimWalkReviewAddActivity::class.java)
                intentReview.putExtra("mapCapture", byteArray) // 이미지 정보를 담은 byteArray를 intent로 넘김
                startActivity(intentReview)  // byteArray -> bitMap -> File 변환 처리는 이것을 전달받은 액티비티(ReviewAdd)에서 진행
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        googleMap!!.snapshot(callback)
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