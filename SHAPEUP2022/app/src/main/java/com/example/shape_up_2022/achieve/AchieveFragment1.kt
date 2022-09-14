package com.example.shape_up_2022.achieve

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shape_up_2022.R
import com.example.shape_up_2022.adapter.AchieveProgressAdapter
import com.example.shape_up_2022.common.SaveSharedPreference
import com.example.shape_up_2022.databinding.AchieveFragment1Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class AchieveFragment1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding : AchieveFragment1Binding

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
        binding = AchieveFragment1Binding.inflate(inflater, container, false)

        // 리사이클러 뷰
        achieveList()

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AchieveFragment1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    /* 업적 리사이클러 뷰 */
    private fun achieveList(){
        val achieveAdapter =  AchieveProgressAdapter(requireContext(), addAchieveList())
        binding.achieveProgressRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.achieveProgressRecyclerview.adapter = achieveAdapter // data array
    }

    /* 업적 목록 추가 */
    data class AchieveProgress(val content: String, val checked: Boolean)

    private fun addAchieveList(): ArrayList<AchieveProgress>{
        var data = arrayListOf<AchieveProgress>()
        // 배열 가져오기
        val achieveArray: Array<String> = resources.getStringArray(R.array.achieve_progress) // 업적 데이터
        val achieveChecked : ArrayList<Boolean> = SaveSharedPreference.getAchieve(requireContext())!! // 달성 여부

        // 목록 추가
        for(i in achieveArray.indices) data.add(AchieveProgress(achieveArray[i], achieveChecked[i]))
        Log.d("mobileApp", "$data")

        return data
    }
}