package com.example.shape_up_2022
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.text.SimpleDateFormat
import java.util.*

class SpeedActivity : AppCompatActivity(), LocationListener {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speed)

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
    }

    override fun onLocationChanged(location: Location) {
        val sdf = SimpleDateFormat("HH:mm:ss")
        var deltaTime = 0.0

        //  getSpeed() 함수를 이용하여 속도를 계산
        val getSpeed = String.format("%.3f", location.speed).toDouble()
        tvGetSpeed!!.text = ": $getSpeed" //Get Speed
        val formatDate = sdf.format(Date(location.time))
        tvTime!!.text = ": $formatDate" //Time

        // 위치 변경이 두번째로 변경된 경우 계산에 의해 속도 계산
        if (mLastlocation != null) {
            //시간 간격
            deltaTime = (location.time - mLastlocation!!.time) / 1000.0
            tvTimeDif!!.text = ": $deltaTime sec" // Time Difference
            tvDistDif!!.text = ": " + mLastlocation!!.distanceTo(location) + " m" // Time Difference
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



