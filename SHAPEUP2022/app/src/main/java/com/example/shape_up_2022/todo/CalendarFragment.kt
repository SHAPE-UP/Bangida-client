package com.example.shape_up_2022.todo

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.shape_up_2022.common.SaveSharedPreference
import com.example.shape_up_2022.databinding.FragmentCalendarBinding
import com.example.shape_up_2022.retrofit.GetTodoReq
import com.example.shape_up_2022.retrofit.GetTodoRes
import com.example.shape_up_2022.retrofit.MyApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalendarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalendarFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding : FragmentCalendarBinding

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
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        val dayText = binding.todoToday
        val calendarView = binding.todoCalendarView
        val date = Date(calendarView.date)

        // 현재 날짜 초기화, 오늘 todo
        dayText.text = SimpleDateFormat("yyyy년 M월 d일").format(date)

        // 캘린더 클릭 이벤트
        calendarView.setOnDateChangeListener{ calendarView, year, month, dayOfMonth ->
            // 화면에 표시되는 날짜 업데이트
            var day = "${year}년 ${month+1}월 ${dayOfMonth}일"
            dayText.text = day

            // 날짜에 해당하는 To-do 불러오기 (DB 요청)
            val dateString = String.format("%d-%02d-%02d", year, month+1, dayOfMonth)
            val call: Call<GetTodoRes> = MyApplication.networkServiceTodo.getTodo(
                GetTodoReq(SaveSharedPreference.getFamliyID(context)!!, dateString)  // familyID, yyyy-mm-dd
            )
            call?.enqueue(object : Callback<GetTodoRes> {
                override fun onResponse(call: Call<GetTodoRes>, response: Response<GetTodoRes>) {
                    if(response.isSuccessful){
                        Log.d("mobileApp", "getTodo ${response.body()}")

                        // 리사이클러뷰에 할 일 목록 표시
                        TodoActivity.updateTodoList((response.body()!!.todoInfo).toMutableList())  // Array<TodoItem>
                    }
                }

                override fun onFailure(call: Call<GetTodoRes>, t: Throwable) {
                    Log.d("mobileApp", "onFailure $t")
                }
            })
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalendarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalendarFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}