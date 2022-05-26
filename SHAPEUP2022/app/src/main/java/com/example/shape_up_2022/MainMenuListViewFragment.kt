package com.example.shape_up_2022

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shape_upapptest.databinding.FragmentMainMenuListViewBinding
import com.example.shape_upapptest.databinding.ItemMainMenuListBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainRecyclerViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainRecyclerViewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var itemListBinding: ItemMainMenuListBinding
    lateinit var adapter : MainListAdapter

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
        val binding = FragmentMainMenuListViewBinding.inflate(inflater, container, false)
        itemListBinding = ItemMainMenuListBinding.inflate(inflater, container, false)

        //리사이클러 뷰 생성
        binding.mainRecyclerview.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false);
        binding.mainRecyclerview.adapter = MainListAdapter(dataStringFunc())

        //리사이클러 뷰 목록 deco 설정


        return binding.root
    }

    // 메뉴 text data List
    private fun dataStringFunc(): MutableList<MainListViewData> {
        val datas = mutableListOf<MainListViewData>()
        datas.add(MainListViewData( "오늘의 TIP"))
        datas.add(MainListViewData( "훈련 영상 모아보기"))
        datas.add(MainListViewData( "반려견 케이성향 점검"))
        datas.add(MainListViewData( "강아지 지갑"))

        return datas
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainRecyclerViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainRecyclerViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}