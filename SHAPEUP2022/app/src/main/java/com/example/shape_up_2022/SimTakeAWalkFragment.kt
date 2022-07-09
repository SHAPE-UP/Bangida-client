package com.example.shape_up_2022

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.shape_up_2022.databinding.FragmentSimTakeAWalkBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener

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
        moveMap(37.568256, 126.897240)
    }

    override fun onConnected(p0: Bundle?) {
        Log.d("mobileApp", "onConnected")
        if(ContextCompat.checkSelfPermission(activity as SimTakeAWalkActivity, Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED){
            providerClient.lastLocation.addOnSuccessListener(
                activity as SimTakeAWalkActivity,
                object: OnSuccessListener<Location> {
                    override fun onSuccess(p0: Location?) {
                        p0?.let{
                            latitude = p0.latitude
                            longitude = p0.longitude
                            Log.d("mobileApp", "lat: $latitude, lng: $longitude")
                            moveMap(latitude, longitude)
                            Log.d("mobileApp", "moveMap")
                        }
                    }
                }
            )
            apiClient.disconnect()
        }
    }

    // 카메라를 이동시키는 함수
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
}