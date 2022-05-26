package com.example.shape_up_2022

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shape_upapptest.databinding.FragmentMainCalenderTodoBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainCalenderTodoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainCalenderTodoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var adapter : TodoListAdapter
    var datas = mutableListOf<MainTodoListViewData>()

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
        val binding = FragmentMainCalenderTodoBinding.inflate(inflater, container, false)

        // data 내용 삽입
        dataFunc()

        //리사이클러 뷰 생성
        if (datas?.size == 0){
            binding.blankTodo.visibility = View.VISIBLE
            binding.blankTodo.text = "사용자가 오늘 할 일이 없습니다."
            binding.blankTodo.textSize = 20F
        } else{
            binding.mainCalender.layoutManager = LinearLayoutManager(activity)
            adapter = TodoListAdapter(datas)
            binding.mainCalender.adapter = adapter
        }
        // 데커레이션 적용


        return binding.root
    }

    private fun dataFunc(): MutableList<MainTodoListViewData> { // 데이터 추가 예시
//        datas.add(MainTodoListViewData(null,  "강아지랑 산책", todo_type))
//        datas.add(MainTodoListViewData(null,  "강아지랑 놀기", todo_type))
//        datas.add(MainTodoListViewData(null,  "목욕", todo_type))
//        datas.add(MainTodoListViewData(null,  "예방 접종", todo_type))
//        datas.add(MainTodoListViewData(null,  "강아지랑 접종", todo_type))
//        datas.add(MainTodoListViewData(null,  "예방 접종", todo_type))
//        datas.add(MainTodoListViewData(null,  "예방 강아지랑", todo_type))
//        datas.add(MainTodoListViewData(null,  "예방 접종", todo_type))

        return datas
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainCalenderTodoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainCalenderTodoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}