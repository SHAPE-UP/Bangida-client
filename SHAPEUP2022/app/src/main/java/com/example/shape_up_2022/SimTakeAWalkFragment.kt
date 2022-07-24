package com.example.shape_up_2022

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.shape_up_2022.databinding.FragmentSimTakeAWalkBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.youtube.player.internal.l
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets.UTF_8
import kotlin.text.Charsets.UTF_8

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SimTakeAWalkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SimTakeAWalkFragment : Fragment(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentSimTakeAWalkBinding

    lateinit var apiClient: GoogleApiClient
    lateinit var providerClient: FusedLocationProviderClient

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private lateinit var mView: MapView
    var googleMap: GoogleMap? = null
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            locationInit()
        }
        locationInit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_sim_take_a_walk, container, false)
        mView = rootView.findViewById(R.id.mapView)
        mView.onCreate(savedInstanceState)
        mView.getMapAsync(this)

        binding = FragmentSimTakeAWalkBinding.inflate(inflater, container, false)

        // 사용자 퍼미션 얻기
        providerClient = LocationServices.getFusedLocationProviderClient(activity as SimTakeAWalkActivity)
        apiClient = GoogleApiClient.Builder(activity as SimTakeAWalkActivity)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                if (it.all { permission -> permission.value == true }) {
                    apiClient.connect() // 정보 가져옴, onConnected와 연결
                } else {
                    Toast.makeText(activity as SimTakeAWalkActivity, "권한 거부..", Toast.LENGTH_SHORT).show()
                }
            }
        if (ContextCompat.checkSelfPermission(
                activity as SimTakeAWalkActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                activity as SimTakeAWalkActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) !== PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                activity as SimTakeAWalkActivity,
                Manifest.permission.ACCESS_NETWORK_STATE
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("mobileApp", "checkSelfPermission")
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_NETWORK_STATE
                )
            )
        } else {
            apiClient.connect() // 정보 가져옴
        }
        /*
        val routeDrawer: RouteDrawer = RouteDrawer.RouteDrawerBuilder(googleMap)
            .withColor(Color.BLUE)
            .withWidth(8)
            .withAlpha(0.5f)
            .withMarkerIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
            .build()*/

        // data 불러오기
        var address = callData()
        var location = callLocationData(address)
        // 마커 만들기


        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SimTakeAWalkFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SimTakeAWalkFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        //moveMap(37.568256, 126.897240)
    }

    override fun onConnected(p0: Bundle?) {
       // Log.d("mobileApp", "onConnected")
        if(ContextCompat.checkSelfPermission(activity as SimTakeAWalkActivity, Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED){
            providerClient.lastLocation.addOnSuccessListener(
                activity as SimTakeAWalkActivity,
                object: OnSuccessListener<Location> {
                    override fun onSuccess(p0: Location?) {  // 사용자의 현재 위치를 얻음
                        p0?.let{
                            latitude = p0.latitude
                            longitude = p0.longitude
                            //Log.d("mobileApp", "lat: $latitude, lng: $longitude")
                            moveMap(latitude, longitude)  // 카메라 초기화
                            //Log.d("mobileApp", "moveMap")
                        }
                    }
                }
            )
            apiClient.disconnect()
        }
    }
    // request location data
    private fun callLocationData(address: String): ArrayList<Double>{
        val locationList = arrayListOf(0.0, 0.0)
        val call: Call<LocationPageModel> = MyApplication.networkServiceKakao.getLocation(
            "KakaoAK 5bb29ef01ac31bd67a3370489b740f6d",
            address,
        )
        Log.d("mobileApp", "call - locationList")
        call?.enqueue(object: Callback<LocationPageModel> {
            override fun onResponse(call: Call<LocationPageModel>, response: Response<LocationPageModel>) {
                if(response.isSuccessful){
                    Log.d("mobileApp", "위치 데이터 연결 성공!")
                    Log.d("mobileApp", "Raw: ${response.raw()}")
                    Log.d("mobileApp", "hello! - locationList : ${response.body()?.documents}")
                    var locationItem = response.body()?.documents
                    locationList[0] = locationItem?.get(0)!!.x.toDouble()
                    locationList[1] = locationItem?.get(0)!!.y.toDouble()
                    Log.d("mobileApp", "hello! - locationList : ${locationList[0]}")

                }
            }

            override fun onFailure(call: Call<LocationPageModel>, t: Throwable) {
                Toast.makeText(context,"데이터 연결 실패",  Toast.LENGTH_SHORT).show()
                Log.d("mobileApp", "onFailure $t")
            }
        })

        return locationList
    }

    // request data
    private fun callData() : String {
        var address = ""
        val call: Call<responseInfo> = MyApplication.networkServicePlaceData.getList(
            "1",
            "10",
            "동물",
            "",
            "264157a5-947e-4f12-8c21-c499089c507a"
        )
        Log.d("mobileApp", "call - address")
        call?.enqueue(object: Callback<responseInfo> {
            override fun onResponse(call: Call<responseInfo>, response: Response<responseInfo>) {
                Log.d("mobileApp", "hello! - address : ${response.body()}")
                if(response.isSuccessful){
                    Log.d("mobileApp", "데이터 연결 성공!")
                    var locationItem = response!!.body()!!.body!!.items!!.item
                    address = locationItem[0].venue.toString()
                }
            }

            override fun onFailure(call: Call<responseInfo>, t: Throwable) {
                Toast.makeText(context, "데이터 연결 실패", Toast.LENGTH_SHORT).show()
                Log.d("mobileApp", "onFailure $t")

            }
        })
        Log.d("mobileApp", "address: ${address}" )

        return address
    }

    // 카메라를 이동시키는 메소드
    private fun moveMap(latitude: Double, longitude: Double){
        val latLng = LatLng(latitude, longitude)
        val position: CameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(16f)
            .build()
        googleMap!!.moveCamera(CameraUpdateFactory.newCameraPosition(position))

        // 마커 추가하기
        val markerOp = MarkerOptions()
        markerOp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        markerOp.position(latLng)
        markerOp.title("내 위치")
        Log.d("mobileApp", "markerOp")
        googleMap?.addMarker(markerOp)
    }

    //성민추가
    private fun locationInit() {
        providerClient = FusedLocationProviderClient(activity as Activity)
        locationCallback = MyLocationCallBack()

        locationRequest = LocationRequest()   // LocationRequest객체로 위치 정보 요청 세부 설정을 함
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY   // GPS 우선
        locationRequest.interval = 10000   // 10초. 상황에 따라 다른 앱에서 더 빨리 위치 정보를 요청하면
        // 자동으로 더 짧아질 수도 있음
        locationRequest.fastestInterval = 5000  // 이보다 더 빈번히 업데이트 하지 않음 (고정된 최소 인터벌)
    }
    private fun addLocationListener() {
        if (ActivityCompat.checkSelfPermission(  // 위치 권한 허용 체크
                activity as Activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity as Activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        providerClient.requestLocationUpdates(locationRequest,
            locationCallback,
            null)  // 혹시 안드로이드 스튜디오에서 비정상적으로 권한 요청 오류를 표시할 경우, 'Alt+Enter'로
        // 표시되는 제안 중, Suppress: Add @SuppressLint("MissingPermission") annotation
        // 을 클릭할 것
        // (에디터가 원래 권한 요청이 필요한 코드 주변에서만 권한 요청 코딩을 허용했었기 때문임.
        //  현재 우리 코딩처럼 이렇게 별도의 메소드에 권한 요청 코드를 작성하지 못하게 했었음)
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onStart() {
        super.onStart()
        mView.onStart()
    }
    override fun onStop() {
        super.onStop()
        mView.onStop()
    }
    override fun onResume() {
        super.onResume()
        mView.onResume()
        // 권한 요청
        permissionCheck(
            cancel = {  },   // 권한 필요 안내창
            ok = { addLocationListener()}      // ③   주기적으로 현재 위치를 요청
        )
    }
    override fun onPause() {
        super.onPause()
        mView.onPause()
        removeLocationListener()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        mView.onLowMemory()
    }
    override fun onDestroy() {
        mView.onDestroy()
        super.onDestroy()
    }


    inner class MyLocationCallBack: LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation   // GPS가 꺼져 있을 경우 Location 객체가
            // null이 될 수도 있음

            location?.run {
                val latLng = LatLng(latitude, longitude)   // 위도, 경도
                googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))  // 카메라 이동

                Log.d("MapsActivity", "위도: $latitude, 경도: $longitude")     // 로그 확인 용



                /*
                *
                *   이동 경로 그리기 (여기에서는 구글맵에서 이동 자취 그리기용으로 지원해주는
                *                     편리한 메서드를 이용)

                */
                googleMap?.addPolyline(polylineOptions)         // 선 그리기 (위치 정보가 갱신되면
                // polyLineOptions 객체에 추가되고
                // 지도에 polylineOptions 객체를 추가 함
            }
        }
    }

    private val REQUEST_ACCESS_FINE_LOCATION = 1000

    private fun permissionCheck(cancel: () -> Unit, ok: () -> Unit) {   // 전달인자도, 리턴값도 없는
        // 두 개의 함수를 받음

        if (ContextCompat.checkSelfPermission(activity as Activity,                  // 권한이 없는 경우
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity as Activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {       // 권한 거부 이력이 있는 경우

                cancel()

            } else {
                ActivityCompat.requestPermissions(activity as Activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_ACCESS_FINE_LOCATION)
            }
        } else {                                                    // 권한이 있는 경우
            ok()
        }
    }


    // 권한 요청 결과 처리
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_ACCESS_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    addLocationListener()
                } else {
                    Log.d("mobileApp", "권한이 거부됨")
                }
                return
            }
        }
    }



    private fun removeLocationListener() {
        providerClient.removeLocationUpdates(locationCallback)
    }

    private val polylineOptions = PolylineOptions().width(5f).color(Color.RED)
}