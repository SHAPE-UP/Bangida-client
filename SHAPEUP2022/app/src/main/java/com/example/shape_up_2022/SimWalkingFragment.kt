package com.example.shape_up_2022

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.shape_up_2022.databinding.FragmentSimWalkingBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.youtube.player.internal.t
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.awaitResponse
import java.io.IOException
import java.net.SocketTimeoutException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SimWalkingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SimWalkingFragment : Fragment(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentSimWalkingBinding
    lateinit var apiClient: GoogleApiClient  // 사용자 현위치정보 얻기 위한 퍼미션 획득용
    lateinit var providerClient: FusedLocationProviderClient  // 구글맵 사용 시 기본적으로 필요

    var latitude: Double = 0.0  // 사용자 현위치 갱신용
    var longitude: Double = 0.0  // 사용자 현위치 갱신용

    private lateinit var geocoder: Geocoder  // 주소 <-> 좌표 변환용

    private lateinit var mView: MapView
    var googleMap: GoogleMap? = null

    private var startLatLng: LatLng=LatLng(0.0, 0.0)  // polyline 시작점
    private var endLatLng: LatLng=LatLng(0.0, 0.0)  // polyline 끝점
    private lateinit var locationRequest: LocationRequest  // polyline
    private lateinit var locationCallback: LocationCallback  // polyline


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        latitude = activity?.intent!!.getDoubleExtra("userlat", 0.0)
        longitude = activity?.intent!!.getDoubleExtra("userlng", 0.0)
        startLatLng = LatLng(latitude, longitude) // polyline 시작점 초기화
        locationInit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_sim_walking, container, false)
        mView = rootView.findViewById(R.id.map_walking)
        mView.onCreate(savedInstanceState)
        mView.getMapAsync(this)
        binding = FragmentSimWalkingBinding.inflate(inflater, container, false)

        getPermissionLocation() // 사용자 퍼미션 얻기

        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SimWalkingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SimWalkingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun getPermissionLocation() {
        // 사용자 퍼미션 얻기
        providerClient = LocationServices.getFusedLocationProviderClient(activity as SimWalkingActivity)
        apiClient = GoogleApiClient.Builder(activity as SimWalkingActivity)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                if (it.all { permission -> permission.value == true }) {
                    apiClient.connect() // 정보 가져옴, onConnected와 연결
                } else {
                    Toast.makeText(activity as SimWalkingActivity, "권한 거부..", Toast.LENGTH_SHORT).show()
                }
            }
        if (ContextCompat.checkSelfPermission(
                activity as SimWalkingActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                activity as SimWalkingActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) !== PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                activity as SimWalkingActivity,
                Manifest.permission.ACCESS_NETWORK_STATE
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("mobileApp", "checkSelfPermission")
            requestPermissionLauncher.launch(  // 권한 요청
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_NETWORK_STATE
                )
            )
        } else {
            apiClient.connect() // 모두 허용되어 있는 상태라면 정보 가져옴
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
    }

    override fun onConnected(p0: Bundle?) {
        // Log.d("mobileApp", "onConnected")
        if(ContextCompat.checkSelfPermission(activity as SimWalkingActivity, Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED){
            providerClient.lastLocation.addOnSuccessListener(
                activity as SimWalkingActivity,
                object: OnSuccessListener<Location> {
                    override fun onSuccess(p0: Location?) {
                        p0?.let{
                            latitude = p0.latitude  // 사용자 위치 초기화
                            longitude = p0.longitude  // 사용자 위치 초기화
                            //Log.d("mobileApp", "lat: $latitude, lng: $longitude")
                            moveMap(latitude, longitude)  // 사용자 위치로 카메라 이동
                            // 사용자 위치 주소로 변환하기
                            //userAddress = addressToLocate(latitude, longitude)
                            //crtProcess(userAddress)
                        }
                    }
                }
            )
            apiClient.disconnect()
        }
    }

    private fun crtProcess(userAddress: String){
        CoroutineScope(Dispatchers.Main).launch {
            Log.d("mobileApp", "코루틴 실행! userAddress: ${userAddress}")
            var address = callDataCrt("당산2동") //** 임시 수정
            //Log.d("mobileApp", "address: ${address}")
            //val locationList = locateToAddress(address)
        }
    }

    // string slice
    private fun strSlice(addr: String): String {
        val temp = addr.split(" ")
        Log.d("mobileApp", "${temp[2]}")
        return temp[2]
    }

    // 지역 마커 찍기
    private fun addMarker(res: MutableList<myItem>, loc: Array<DoubleArray>){
        var color = 0.0F
        for(i in 0..loc.size - 1){
            val latLng = LatLng(loc[i][0], loc[i][1])

            // 카테고리 분류
            val category = res[i].subjectCategory
            if(category == "동물병원"){
                color = BitmapDescriptorFactory.HUE_ORANGE
            }else if(category == "동물약국"){
                color = BitmapDescriptorFactory.HUE_ROSE
            } else if(category == "축구장" || category == "야구장"){
                color = BitmapDescriptorFactory.HUE_YELLOW
            } else{
                color = BitmapDescriptorFactory.HUE_VIOLET
            }

            // 마커 추가하기
            val markerOp = MarkerOptions()
            markerOp.icon(BitmapDescriptorFactory.defaultMarker(color))
            markerOp.position(latLng)
            markerOp.title(res[i].title.toString())
            googleMap?.addMarker(markerOp)
            Log.d("mobileApp", "markerOp")
        }
    }

    private suspend fun callDataCrt(addr: String): ArrayList<String>{
        var addressList = arrayListOf<String>()
        val call: Call<responseInfo> = MyApplication.networkServicePlaceData.getList(
            "1",
            "20",
            "",
            addr,
            "264157a5-947e-4f12-8c21-c499089c507a"
        )
        try{
            val response = call.awaitResponse()
            if(!response.isSuccessful){
                Log.d("mobileApp", "데이터 연결 실패!")
            }
            Log.d("mobileApp", "데이터 연결 성공!")
            val locationItem = response!!.body()!!.body!!.items!!.item
            Log.d("mobileApp", "${locationItem}")

            for(i in 0 until locationItem.size - 1){
                addressList.add(locationItem[i].venue.toString())
            }

            val locationList = locateToAddress(addressList)
            // 마커 표시
            addMarker(locationItem, locationList)

        }catch (e: SocketTimeoutException){
            Log.d("mobileApp", "Exception: ${e.toString()}")
            addressList = callDataCrt(addr)
        }
        return addressList
    }

    // 주소 -> 위도/경도
    private fun locateToAddress(address: ArrayList<String>): Array<DoubleArray>{
        var locationList =  Array(address.size) { DoubleArray(2) { 0.0 } }
        var list: List<Address>? = null
        var str = address
        for(i in 0 until str.size - 1) {
            try {
                list = geocoder.getFromLocationName(
                    str[i], // 지역 이름
                    str.size
                ) // 읽을 개수
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("mobileApp", "입출력 오류 - 서버에서 주소변환시 에러발생")
            }
            if (list != null) {
                if (list!!.size == 0) {
                    Log.d("mobileApp", "해당되는 주소 정보는 없습니다.")
                } else {
                    locationList[i][0] = list[0].latitude
                    locationList[i][1] = list[0].longitude
                    //Log.d("mobileApp", "locationList**: ${locationList[i][0]}")
                }
            }
        }
        return locationList
    }

    // 위도/경도 -> 주소
    private fun addressToLocate(latitude: Double, longitude: Double): String{
        var myLocation = ""
        geocoder = Geocoder(activity as SimWalkingActivity)
        var list: List<Address>? = null
        try {
            var d1: Double = latitude
            var d2: Double = longitude

            list = geocoder.getFromLocation(
                d1, // 위도
                d2, // 경도
                10
            ); // 얻어올 값의 개수
        } catch (e: IOException) {
            Log.e("mobileApp", "입출력 오류 - 서버에서 주소변환시 에러발생");
            e.printStackTrace()
        }

        if (list != null) {
            if (list.size == 0) {
                Log.d("mobileApp", "해당되는 주소 정보는 없습니다.")
            } else {
                Log.d("mobileApp", "address list: ${list.get(0)}")
                // 이게 null인 경우가 있음
                Log.d("mobileApp", "address list: ${list.get(0).thoroughfare}")
                myLocation = list.get(0).thoroughfare.toString()

            }
        }
        Log.d("mobileApp", "myLocation: ${myLocation}")
        return myLocation
    }


    override fun onConnectionSuspended(p0: Int) {
    }
    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    // 구글맵 생명주기함수
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

    // 구글맵 카메라 초기 세팅용 함수 (최초 1회 사용됨)
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
    // polyline 초기화
    private fun locationInit() {
        providerClient = FusedLocationProviderClient(activity as Activity)
        locationCallback = MyLocationCallBack()
        locationRequest = LocationRequest()   // LocationRequest객체로 위치 정보 요청 세부 설정을 함
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY   // GPS 우선
        locationRequest.interval = 5000   // 5초. 상황에 따라 다른 앱에서 더 빨리 위치 정보를 요청하면
        // 자동으로 더 짧아질 수도 있음
        locationRequest.fastestInterval = 3000  // 이보다 더 빈번히 업데이트 하지 않음 (고정된 최소 인터벌)
    }
    // polyline
    private fun addLocationListener() {
        if (ActivityCompat.checkSelfPermission(
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

    inner class MyLocationCallBack: LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation   // GPS가 꺼져 있을 경우 Location 객체가
            // null이 될 수도 있음

            location?.run {
                val latLng = LatLng(latitude, longitude)   // 위도, 경도
                googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))  // 카메라 이동

                endLatLng = latLng
                val polylineOptions = PolylineOptions().add(startLatLng).add(endLatLng).width(10f).color(
                    Color.RED)
                googleMap?.addPolyline(polylineOptions)         // 선 그리기 (위치 정보가 갱신되면
                // polyLineOptions 객체에 추가되고
                // 지도에 polylineOptions 객체를 추가 함
                startLatLng=endLatLng
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
        } else {
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


}