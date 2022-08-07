package com.example.shape_up_2022

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shape_up_2022.databinding.ActivitySimWalkSearchBinding
import com.example.shape_up_2022.databinding.ItemSearchWalkplaceBinding
import com.google.android.material.chip.ChipGroup
import com.google.android.material.chip.ChipGroup.OnCheckedStateChangeListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.awaitResponse
import java.net.SocketTimeoutException

class SimWalkSearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySimWalkSearchBinding

    var datas = mutableListOf<myItem>() // 전체 data
    var hospitalDatas = mutableListOf<myItem>() // 동물병원 data
    var pharmacyDatas = mutableListOf<myItem>() // 동물약국 data
    var parkDatas = mutableListOf<myItem>() // 공원 data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimWalkSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //검색 메뉴에 대한 코드
        var searchViewTextListener: SearchView.OnQueryTextListener =
            object : SearchView.OnQueryTextListener {
                // 검색버튼 입력시 호출
                override fun onQueryTextSubmit(s: String): Boolean {
                    // 검색어에 해당하는 데이터 호출
                    // 배열 비우기
                    datas.clear()
                    hospitalDatas.clear()
                    pharmacyDatas.clear()
                    parkDatas.clear()
                    
                    CoroutineScope(Dispatchers.Main).launch {
                        Log.d("mobileApp", "callDataCrt")
                        callDataCrt(s) // 공공데이터 호출
                    }
                    return false
                }

                //텍스트 입력/수정시에 호출
                override fun onQueryTextChange(s: String): Boolean {

                    return false
                }
            }

        // 검색메뉴에 대한 코드
        binding.walkSearch.setOnQueryTextListener(searchViewTextListener)

        // 칩 이벤트 처리(추후 코드 수정)
        binding.walkChipgroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (binding.placeHospital.isChecked) {
                Log.d("mobileApp", "병원 선택")
            }
            if (binding.placePharmacy.isChecked) {
                Log.d("mobileApp", "약국 선택")
            }
            if (binding.placePark.isChecked) {
                Log.d("mobileApp", "공원 선택")
            }
            if (binding.placeCafe.isChecked) {
                Log.d("mobileApp", "카페 선택")
            }
        }


    }
    // 리사이클러 뷰
    private fun displayRecyclerView(data: MutableList<myItem>){
        val searchAdapter =  SimWalkSearchAdapter(this, data)
        binding.walkSearchRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.walkSearchRecyclerView.adapter = searchAdapter // data array

        // 리사이클러뷰 이벤트 처리
        searchAdapter.setItemClickListener(object: SimWalkSearchAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                // 클릭 시 상세정보 뜨도록
                binding.petplaceInfo.visibility = View.VISIBLE
                binding.placeBottomSheetPlace.text = data.get(position).title
                binding.placeBottomSheetAddr.text = data.get(position).venue
                binding.placeBottomSheetTel.text = data.get(position).reference
                binding.placeBottomSheetType.text = data.get(position).subjectCategory
                // 추후에 이미지 관련 코드 추가(이미지 data 필요)
            }
        })
    }

    // 공공데이터 요청1: 지역별 검색 기능 지원
    private suspend fun callDataCrt(keyword: String){
        val call: Call<responseInfo> = MyApplication.networkServicePlaceData.getList(
            "1",
            "20",
            keyword,
            "",
            "264157a5-947e-4f12-8c21-c499089c507a"
        )
        try{
            val response = call.awaitResponse()
            if(!response.isSuccessful){
                Log.d("mobileApp", "데이터 연결 실패!")
            }
            Log.d("mobileApp", "데이터 연결 성공!")
            val locationItem = response!!.body()!!.body?.items?.item
            Log.d("mobileApp", "${locationItem}")

            // 리사이클러 뷰
            if(locationItem.size == 0) binding.noSearch.visibility = View.VISIBLE
            else {
                // type에 따라서 각각 다른 데이터를 배열에 푸시
                for (i in 0..locationItem.size -1) {
                    datas.add(locationItem[i])
                    when (locationItem[i].subjectCategory) {
                        "동물병원" -> hospitalDatas.add(locationItem[i])
                        "동물약국" -> pharmacyDatas.add(locationItem[i])
                        else -> parkDatas.add(locationItem[i])
                    }
                }

                binding.noSearch.visibility = View.GONE
                displayRecyclerView(datas)
            }

        }catch (e: SocketTimeoutException){
            callDataCrt(keyword)
            Log.d("mobileApp", "Exception: ${e.toString()}")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.walk_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_search -> {
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}