package com.example.shape_up_2022

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shape_up_2022.databinding.ActivitySimWalkSearchBinding
import com.example.shape_up_2022.databinding.ItemSearchWalkplaceBinding
import com.google.android.material.chip.ChipGroup
import com.google.android.material.chip.ChipGroup.OnCheckedStateChangeListener

class SimWalkSearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySimWalkSearchBinding
    var data = mutableListOf<petPlace>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimWalkSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //검색 메뉴에 대한 코드
        var searchViewTextListener: SearchView.OnQueryTextListener =
            object : SearchView.OnQueryTextListener {
                //검색버튼 입력시 호출
                override fun onQueryTextSubmit(s: String): Boolean {
                    // 해당하는 데이터 호출
                    return false
                }

                //텍스트 입력/수정시에 호출
                override fun onQueryTextChange(s: String): Boolean {
                    //Log.d("appTest", "SearchVies Text is changed : $s")
                    // 해당하는 데이터 호출
                    return false
                }
            }

        // 검색메뉴에 대한 코드
        binding.walkSearch.setOnQueryTextListener(searchViewTextListener)

        // 리사이클러 뷰
        val searchAdapter =  SimWalkSearchAdapter(this, addDatas())
        binding.walkSearchRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.walkSearchRecyclerView.adapter = searchAdapter // data array

        // 칩 이벤트 처리
        binding.walkChipgroup.setOnCheckedStateChangeListener { group, checkedIds ->
            var flag = true
            //var resarr = arrayListOf<myRow>()
            if (binding.placeHospital.isChecked) {
                Log.d("mobileApp", "병원 선택")
                //resarr.addAll(playgrounds)
                flag = false
            }
            if (binding.placePharmacy.isChecked) {
                Log.d("mobileApp", "약국 선택")
                //resarr.addAll(kidscafes)

                flag = false
            }
            if (binding.placePark.isChecked) {
                Log.d("mobileApp", "공원 선택")
                //resarr.addAll(libs)

                flag = false
            }
            if (binding.placeCafe.isChecked) {
                Log.d("mobileApp", "카페 선택")
                //resarr.addAll(libs)

                flag = false
            }
            if (flag) {
                //resarr.addAll(datas)

            }
        }

        // 리사이클러뷰 이벤트 처리
        searchAdapter.setItemClickListener(object: SimWalkSearchAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                // 클릭 시 상세정보 뜨도록
                binding.petplaceInfo.visibility = View.VISIBLE
                binding.placeBottomSheetPlace.text = data.get(position).title
                binding.placeBottomSheetAddr.text = data.get(position).addr
                binding.placeBottomSheetTel.text = data.get(position).tel
                binding.placeBottomSheetType.text = data.get(position).type
                // 추후에 이미지 관련 코드 추가(이미지 data 필요)
            }
        })
    }

    //data class
    data class petPlace(val title: String, val type: String, val addr: String, val image: String, val tel: String)

    //add data
    private fun addDatas(): MutableList<petPlace>{
        // 임시 data 사용
        //Log.d("mobileApp", "addDatas")

        data.add(petPlace("장소1", "동물병원", "주소", "이미지", "전화번호"))
        data.add(petPlace("장소2", "동물약국", "주소11", "이미지11", "전화번호11"))

        return data
    }
}