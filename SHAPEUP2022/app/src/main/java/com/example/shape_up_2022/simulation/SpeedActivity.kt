package com.example.shape_up_2022.simulation
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Chronometer
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.shape_up_2022.R
import com.example.shape_up_2022.databinding.ActivitySimWalkingBinding
import com.example.shape_up_2022.databinding.ActivitySpeedBinding
import com.google.android.gms.maps.GoogleMap
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SpeedActivity : AppCompatActivity(), LocationListener {
    lateinit var binding: ActivitySpeedBinding
    private var mapfragment = SimWalkingFragment()  // 지도 프래그먼트를 갖고 있음
    var googleMap: GoogleMap? = mapfragment.googleMap  // 프래그먼트의 구글맵

    //배열 선언
    var Dist_list = mutableListOf<Float>()
    var speed_list = ArrayList<Float>()

    // for문 종료 변수
    var for_end =0

    //전달할 변수 선언
    var distance=0.0f
    var avespeed=0.0f
    var fullspeed =0.0f
    var fulltime=0

    private val fl: FrameLayout by lazy {
        findViewById(R.id.walking_fragment)
    }

    private var locationManager: LocationManager? = null
    private var mLastlocation: Location? = null
    private var tvGetSpeed: TextView? = null
    private var tvCalSpeed: TextView? = null
    private var tvTime: TextView? = null
    private var tvLastTime: TextView? = null
    private var tvGpsEnable: TextView? = null
    private var tvTimeDif: TextView? = null
    private var tvDistDif: TextView? = null
    private var speed = 0.0

    // 배열
    var calSpeedArray = mutableListOf<Float>(0.0F)
    var tvDistArray = mutableListOf<Float>(0.0F)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // setContentView(R.layout.activity_speed)

        //replaceFragment(mapfragment)  // 지도 프래그먼트 부착

        tvGetSpeed = findViewById<View>(R.id.tvGetSpeed) as TextView
        tvCalSpeed = findViewById<View>(R.id.tvCalSpeed) as TextView
        tvTime = findViewById<View>(R.id.tvTime) as TextView
        tvLastTime = findViewById<View>(R.id.tvLastTime) as TextView
        tvGpsEnable = findViewById<View>(R.id.tvGpsEnable) as TextView
        tvTimeDif = findViewById<View>(R.id.tvTimeDif) as TextView
        tvDistDif = findViewById<View>(R.id.tvDistDif) as TextView



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


        /*binding.btnWalkingStart.setOnClickListener {
            //binding.btnWalkingStart.isEnabled=false
            //binding.btnWalkingFinish.isEnabled=true
            binding.btnWalkingStart.setEnabled(false)
            binding.btnWalkingStart.setVisibility(View.GONE)
            binding.btnWalkingFinish.setEnabled(true)
            binding.btnWalkingFinish.setVisibility(View.VISIBLE)

            val intent = Intent(this, SimWalkingActivity::class.java)
            intent.putExtra("answer", distance)
            startActivity(intent)

        }*/


        binding.btnWalkingFinish.setOnClickListener {

            for_end=100
            //속도 계산배열의 마지막 index값, 배열의 갯수
            for(i in 0..speed_list.size -1){
                avespeed= (speed_list[i]+avespeed)

            }
            var avespeed_intent= avespeed/speed_list.size
            Log.d("mobileD", "${avespeed_intent}")

            //값 넘기기 getXExtra,putExtra
            var distance_intent=distance.toString()

            val intent = Intent(this, SimWalkReviewAddActivity::class.java)
            intent.putExtra("Key", distance_intent)
            intent.putExtra("Key1", avespeed_intent)
            startActivity(intent)
        }


    }
    //private fun replaceFragment(fragment: Fragment) {
    //    supportFragmentManager.beginTransaction().replace(fl.id, fragment).commit()
    //}



    override fun onLocationChanged(location: Location) {
        val sdf = SimpleDateFormat("HH:mm:ss")
        var deltaTime = 0.0

        //  getSpeed() 함수를 이용하여 속도를 계산
        val getSpeed = String.format("%.3f", location.speed).toDouble()

        //속도 배열 담기

        tvGetSpeed!!.text = ": $getSpeed" //Get Speed

        val formatDate = sdf.format(Date(location.time))
        tvTime!!.text = ": $formatDate" //Time

        // 위치 변경이 두번째로 변경된 경우 계산에 의해 속도 계산
        if (mLastlocation != null) {
            //시간 간격

            deltaTime = (location.time - mLastlocation!!.time) / 1000.0
            tvTimeDif!!.text = ": $deltaTime sec" // Time Difference
            tvDistDif!!.text = ": " + mLastlocation!!.distanceTo(location) + " m" // Time Difference

            //거리 간격 배열에 담기
            Dist_list.add(mLastlocation!!.distanceTo(location))

            //로그 거리
           /*Log.d("mobileD","${Dist_list.size}")
            for(i in 0..Dist_list.size - 1) {
               Log.d("mobileD", "{$i : ${Dist_list[i]}")
            }*/

            //거리 합

            for(i in 0..Dist_list.size -1){
                distance= (Dist_list[i]+distance)

                if(for_end==100)
                    break
            }
            Log.d("mobilekey","${distance}")

            // 속도 계산 distanceTo좌표로 거리 구하기
            speed = mLastlocation!!.distanceTo(location) / deltaTime
            val formatLastDate = sdf.format(Date(mLastlocation!!.time))
            tvLastTime!!.text = ": $formatLastDate" //Last Time
            val calSpeed = String.format("%.2f", speed).toDouble()
            tvCalSpeed!!.text = ": $calSpeed" //Cal Speed

            if(for_end!=100) {
                speed_list.add(calSpeed.toFloat())
            }

            for(i in 0..speed_list.size - 1) {
                Log.d("mobileApp", "{$i : ${speed_list[i]}")
            }
        }

        mLastlocation = location
        //Log.d("mobileApp", "$location")

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

    override fun onProviderDisabled(provider: String) {}
    override fun onResume() {
        super.onResume()
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

}


