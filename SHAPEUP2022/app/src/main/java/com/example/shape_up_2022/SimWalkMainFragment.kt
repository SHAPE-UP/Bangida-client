package com.example.shape_up_2022

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.shape_up_2022.databinding.FragmentSimWalkMainBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.youtube.player.internal.i
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
 * Use the [SimWalkMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SimWalkMainFragment : Fragment(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentSimWalkMainBinding
    lateinit var apiClient: GoogleApiClient  // 사용자 현위치정보 얻기 위한 퍼미션 획득용
    lateinit var providerClient: FusedLocationProviderClient  // 구글맵 사용 시 기본적으로 필요

    var latitude: Double = 0.0  // 사용자 현위치 갱신용
    var longitude: Double = 0.0  // 사용자 현위치 갱신용

    private lateinit var geocoder: Geocoder  // 주소 <-> 좌표 변환용
    var userAddress = ""  // 사용자 위치정보 -> 주소 변환 (공공데이터 주변시설 요청 키워드 추출용)

    private lateinit var mView: MapView
    var googleMap: GoogleMap? = null

    var tempmarkerOptions: Marker? = null

    var datas = mutableListOf<myItem>() // 전체 data
    var hospitalDatas = mutableListOf<myItem>() // 동물병원 data
    var pharmacyDatas = mutableListOf<myItem>() // 동물약국 data
    var parkDatas = mutableListOf<myItem>() // 공원 data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_sim_walk_main, container, false)
        mView = rootView.findViewById(R.id.map_main)
        mView.onCreate(savedInstanceState)
        mView.getMapAsync(this)
        binding = FragmentSimWalkMainBinding.inflate(inflater, container, false)

        getPermissionLocation() // 사용자 퍼미션 얻기

        // 칩 레이아웃 클릭 이벤트
        

        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SimWalkMainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SimWalkMainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getPermissionLocation() {
        // 사용자 퍼미션 얻기
        providerClient = LocationServices.getFusedLocationProviderClient(activity as SimWalkMainActivity)
        apiClient = GoogleApiClient.Builder(activity as SimWalkMainActivity)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                if (it.all { permission -> permission.value == true }) {
                    apiClient.connect() // 정보 가져옴, onConnected와 연결
                } else {
                    Toast.makeText(activity as SimWalkMainActivity, "권한 거부..", Toast.LENGTH_SHORT).show()
                }
            }
        if (ContextCompat.checkSelfPermission(
                activity as SimWalkMainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                activity as SimWalkMainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) !== PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                activity as SimWalkMainActivity,
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

        /* 마커 클릭 이벤트 */
        var index: Int = 0
        googleMap!!.setOnMarkerClickListener(object :GoogleMap.OnMarkerClickListener{
            override fun onMarkerClick(p0: Marker): Boolean {

                if (p0.title=="내 위치") {
                    Log.d("mobileApp", "현위치마커클릭")
                    tempmarkerOptions?.remove()
                    binding.fabAdd.visibility = View.VISIBLE
                    binding.mainPetplaceInfo.visibility = View.GONE
                    //binding.fabStar.visibility = View.GONE
                    return false
                }

                if (p0==tempmarkerOptions) {
                    Log.d("mobileApp", "temp마커클릭")
                    binding.fabAdd.visibility = View.VISIBLE
                    binding.mainPetplaceInfo.visibility = View.GONE
                    //binding.fabStar.visibility = View.GONE
                    return false
                }

                Log.d("mobileApp", "일반마커클릭")
                // 마커가 클릭되면 하단의 스크롤뷰에 상세정보를 띄운다
                binding.fabAdd.visibility = View.GONE
                binding.mainPetplaceInfo.visibility = View.VISIBLE
                //binding.fabStar.visibility = View.VISIBLE
/*                findViewById<TextView>(R.id.info_placename).setText(p0.title)  // 마커의 타이틀과 같은 장소명을 가진 데이터를 찾아온다
                for (i in 0 until datas.size) {
                    if (datas[i].placename==p0.title){
                        index = i
                        break
                    }
                }*/
//                findViewById<TextView>(R.id.info_placetype).setText(datas[index].placetype)
//                findViewById<TextView>(R.id.info_addr).setText(datas[index].addr ?: datas[index].road_addr)
//                findViewById<TextView>(R.id.info_content).setText("")

                return false
            }
        })

        var markerOptions : MarkerOptions
        /* 맵 클릭 이벤트 */
        googleMap!!.setOnMapClickListener(object: GoogleMap.OnMapClickListener{
            override fun onMapClick(latLng: LatLng) {
                Log.d("mobileApp", "클릭!")
                binding.fabAdd.visibility = View.VISIBLE
                binding.mainPetplaceInfo.visibility = View.GONE
                //binding.fabStar.visibility = View.GONE

                // 클릭한 위치를 마커로 표시
                tempmarkerOptions?.remove()  // 직전에 생성된 마커를 지움
                markerOptions = MarkerOptions()
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                markerOptions.position(latLng!!)

                tempmarkerOptions = googleMap?.addMarker(markerOptions)  // 일시적으로 생성된 마커를 tempmarterOptions에 보관
                Log.d("mobileApp", "$latLng")
            }
        })
    }

    override fun onConnected(p0: Bundle?) {
        // Log.d("mobileApp", "onConnected")
        if(ContextCompat.checkSelfPermission(activity as SimWalkMainActivity, Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED){
            providerClient.lastLocation.addOnSuccessListener(
                activity as SimWalkMainActivity,
                object: OnSuccessListener<Location> {
                    override fun onSuccess(p0: Location?) {
                        p0?.let{
                            latitude = p0.latitude  // 사용자 위치 초기화
                            longitude = p0.longitude  // 사용자 위치 초기화
                            //Log.d("mobileApp", "lat: $latitude, lng: $longitude")
                            moveMap(latitude, longitude)  // 사용자 위치로 카메라 이동

                            // 사용자 위치 주소로 변환하기
                            userAddress = addressToLocate(latitude, longitude)
                            crtProcess(userAddress)
                        }
                    }
                }
            )
            apiClient.disconnect()
        }
    }

    override fun onConnectionSuspended(p0: Int) {
    }
    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    // 구글맵 작동
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
    }
    override fun onPause() {
        super.onPause()
        mView.onPause()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        mView.onLowMemory()
    }
    override fun onDestroy() {
        mView.onDestroy()
        super.onDestroy()
    }


    /* crt: Coroutine 코루틴 스코프 */
    private fun crtProcess(userAddress: String){
        CoroutineScope(Dispatchers.Main).launch {
            Log.d("mobileApp", "코루틴 실행! userAddress: ${userAddress}")
            callDataCrt(userAddress) // 공공데이터 요청: 매개변수에 사용자 위치 키워드가 들어감

            // 마커 그리기 (초기 세팅, 전체 장소 마커를 그림)
            Log.d("mobileApp", "코루틴 실행! addMarker")
            addMarker(datas)
        }
    }

    private fun addMarker(datas: MutableList<myItem>){
        // 주소 -> 위도 경도
        var locationList = locateToAddress(datas)

        val markerOp = MarkerOptions()

        for(i in 0 until datas.size){
            val latLng = LatLng(locationList[i][0], locationList[i][1])
            Log.d("mobileApp", "${locationList[i][0]} ${locationList[i][1]}")
            markerOp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            markerOp.position(latLng)
            markerOp.title(datas[i].title)
            Log.d("mobileApp", "markerOp!")
            googleMap?.addMarker(markerOp)
        }
    }

    // 사용자 위치 주소 키워드를 얻기 위함
    private fun strSlice(addr: String): String {
        val temp = addr.split(" ")
        Log.d("mobileApp", "${temp[2]}")
        return temp[2]
    }

    // 공공데이터 요청1: 지역별 검색 기능 지원
    private suspend fun callDataCrt(addr: String){
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

            // type에 따라서 각각 다른 데이터를 배열에 푸시
            for (i in 0..locationItem.size -1) {
                datas.add(locationItem[i])
                when (locationItem[i].subjectCategory) {
                    "동물병원" -> hospitalDatas.add(locationItem[i])
                    "동물약국" -> pharmacyDatas.add(locationItem[i])
                    else -> parkDatas.add(locationItem[i])
                }
            }

        }catch (e: SocketTimeoutException){
            callDataCrt(addr)
            Log.d("mobileApp", "Exception: ${e.toString()}")
        }

    }

    // geocoder: 주소 -> 위도/경도 변환
    private fun locateToAddress(dataItem: MutableList<myItem>): Array<DoubleArray>{
        var locationList =  Array(dataItem.size) { DoubleArray(2) { 0.0 } }
        var list: List<Address>? = null
        var str = dataItem
        for(i in 0 until str.size - 1) {
            try {
                list = geocoder.getFromLocationName(
                    str[i].venue, // 지역 이름
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

    // geocoder: 위도/경도 -> 주소 변환
    private fun addressToLocate(latitude: Double, longitude: Double): String{
        var myLocation = ""
        geocoder = Geocoder(activity as SimWalkMainActivity)
        var list: List<Address>? = null
        try {
            var d1: Double = latitude
            var d2: Double = longitude

            list = geocoder.getFromLocation(
                d1, // 위도
                d2, // 경도
                10
            ) // 얻어올 값의 개수
        } catch (e: IOException) {
            Log.e("mobileApp", "입출력 오류 - 서버에서 주소변환시 에러발생");
            e.printStackTrace()
        }

        if (list != null) {
            if (list.size == 0) {
                Log.d("mobileApp", "해당되는 주소 정보는 없습니다.")
            } else {
                Log.d("mobileApp", "${list.get(0)}")

                //Log.d("mobileApp", "address list: ${list.get(0).locality}")
                if(list.get(0).locality != null){
                    myLocation = list.get(0).locality.toString()
                } else if(list.get(0).thoroughfare != null){
                    myLocation = list.get(0).thoroughfare.toString()
                } else {
                    myLocation = list.get(0).adminArea.toString()
                }
            }
        }
        Log.d("mobileApp", "myLocation: ${myLocation}")
        return myLocation
    }

    // 구글맵 카메라 초기 세팅용 함수 (최초 1회 사용됨)
    private fun moveMap(latitude: Double, longitude: Double){
        val latLng = LatLng(latitude, longitude)
        val position: CameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(16f)
            .build()
        googleMap!!.moveCamera(CameraUpdateFactory.newCameraPosition(position))

        // 마커 추가하기: 내 위치
        val markerOp = MarkerOptions()
        markerOp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        markerOp.position(latLng)
        markerOp.title("내 위치")
        Log.d("mobileApp", "markerOp")
        googleMap?.addMarker(markerOp)
    }
}