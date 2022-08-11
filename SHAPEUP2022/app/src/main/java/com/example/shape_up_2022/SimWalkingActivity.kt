package com.example.shape_up_2022

import android.Manifest
import android.content.Context
import android.content.Intent

import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri

import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.shape_up_2022.databinding.ActivitySimWalkMainBinding
import com.example.shape_up_2022.databinding.ActivitySimWalkingBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.GoogleMap

import com.google.android.youtube.player.internal.e
import com.google.android.youtube.player.internal.m
import java.io.*
import java.lang.Exception

import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.model.LatLngBounds
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SimWalkingActivity : AppCompatActivity() , LocationListener {
    var pauseTime = 0L

    private var locationManager: LocationManager? = null
    private var mLastlocation: Location? = null
    private var walking_speed: TextView? = null
    private var tvCalSpeed: TextView? = null
    private var tvTime: TextView? = null
    private var tvLastTime: TextView? = null
    private var tvGpsEnable: TextView? = null
    private var tvTimeDif: TextView? = null
    private var walking_distance: TextView? = null
    private var speed = 0.0


    lateinit var binding: ActivitySimWalkingBinding
    private var mapfragment = SimWalkingFragment()  // 지도 프래그먼트를 갖고 있음
    lateinit var googleMap: GoogleMap  // 프래그먼트의 구글맵 (액티비티 실행 시 바로 초기화되지 않음. 주의!)

    private var REQUEST_ACCESS_FINE_LOCATION = 1000



    private val fl: FrameLayout by lazy {
        findViewById(R.id.walking_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySimWalkingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(mapfragment)  // 지도 프래그먼트 부착


        //권한 체크
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val lastKnownLocation = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (lastKnownLocation != null) {
            val sdf = SimpleDateFormat("HH:mm:ss")
            val formatDate = sdf.format(Date(lastKnownLocation.time))
            tvTime!!.text = ": $formatDate" //Time
        }
        // GPS 사용 가능 여부 확인
        val isEnable = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        tvGpsEnable!!.text = ": $isEnable" //GPS Enable
        locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0f, this)


        //1번 눌렀을 때 or apply
        binding.btnWalkingStart.setOnClickListener {
            binding.walkingTime
            binding.walkingTime.base=SystemClock.elapsedRealtime()
            binding.walkingTime.start()
            //binding.btnWalkingStart.isEnabled=false
            //binding.btnWalkingFinish.isEnabled=true
            binding.btnWalkingStart.setEnabled(false)
            binding.btnWalkingStart.setVisibility(View.GONE)
            binding.btnWalkingFinish.setEnabled(true)
            binding.btnWalkingFinish.setVisibility(View.VISIBLE)

        }

        binding.btnWalkingFinish.setOnClickListener {

            googleMap = mapfragment.googleMap!! // 캡쳐할 지도 프래그먼트 가져오기
            CaptureMapScreen()  // 캡쳐 및 이동 (!!!!!인텐트 생성과정을 포함하고 있음!!!!! 수정 필요)

            //val intent = Intent(this@SimWalkingActivity, SimWalkReviewAddActivity::class.java)
            //startActivity(intent)

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

        binding.btnWalkingFinish.setOnClickListener {
            val intent = Intent(this, SimWalkReviewAddActivity::class.java)
            startActivity(intent)
            binding.walkingTime.stop()
        }
    }
    override fun onLocationChanged(location: Location) {
        val sdf = SimpleDateFormat("HH:mm:ss")
        var deltaTime = 0.0

        //  getSpeed() 함수를 이용하여 속도를 계산
        val getSpeed = String.format("%.3f", location.speed).toDouble()
        walking_speed!!.text = ": $getSpeed" //Get Speed
        val formatDate = sdf.format(Date(location.time))
        tvTime!!.text = ": $formatDate" //Time

        // 위치 변경이 두번째로 변경된 경우 계산에 의해 속도 계산
        if (mLastlocation != null) {
            //시간 간격
            deltaTime = (location.time - mLastlocation!!.time) / 1000.0
            tvTimeDif!!.text = ": $deltaTime sec" // Time Difference
            walking_distance!!.text = ": " + mLastlocation!!.distanceTo(location) + " m" // Time Difference
            // 속도 계산
            speed = mLastlocation!!.distanceTo(location) / deltaTime
            val formatLastDate = sdf.format(Date(mLastlocation!!.time))
            tvLastTime!!.text = ": $formatLastDate" //Last Time
            val calSpeed = String.format("%.3f", speed).toDouble()
            tvCalSpeed!!.text = ": $calSpeed" //Cal Speed
        }
        // 현재위치를 지난 위치로 변경
        mLastlocation = location

    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onProviderEnabled(provider: String) {
        //권한 체크
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        // 위치정보 업데이트
        locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0f, this)
    }
    override fun onPause() {
        super.onPause()
        // 위치정보 가져오기 제거
        locationManager!!.removeUpdates(this)

    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(fl.id, fragment).commit()
    }

    override fun onStart() {
        super.onStart()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //권한이 없을 경우 최초 권한 요청 또는 사용자에 의한 재요청 확인
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) &&
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                // 권한 재요청
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    100
                )
                return
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    100
                )
                return
            }
        }
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