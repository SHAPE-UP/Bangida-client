package com.example.shape_up_2022

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shape_up_2022.databinding.ActivityTodoCalendarBinding
import java.text.SimpleDateFormat
import java.util.*

class TodoCalendarActivity : AppCompatActivity() {
    lateinit var binding: ActivityTodoCalendarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dayText = binding.todoToday
        val calendarView = binding.todoCalendarView
        val date = Date(calendarView.date)

        // 현재 날짜 초기화, 오늘 todo
        dayText.text = SimpleDateFormat("yyyy년 M월 d일").format(date)
        binding.todoListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.todoListRecyclerView.adapter = TodoCalendarAdapter(this, addDatas())

        // 캘린더 클릭 이벤트
        calendarView.setOnDateChangeListener{ calendarView, year, month, dayOfMonth ->
            // 해당 날짜 변수에 담기
            var day = "${year}년 ${month+1}월 ${dayOfMonth}일"
            // 날짜에 해당하는 To-do 불러오기
            binding.todoListRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.todoListRecyclerView.adapter = TodoCalendarAdapter(this, addDatas())
            dayText.text = day
        }
    }

    data class exampleDatas(val todo: String, val user: String, val time: String)

    private fun recyclerViewTodo(){ // 추후에 date type을 매개변수로
        //Log.d("mobileApp", "recyclerViewTodo")
        binding = ActivityTodoCalendarBinding.inflate(layoutInflater)

    }

    // 현재 날짜에 해당하는 To-do 데이터 불러오기(retrofit, recyclerview)
    private fun addDatas(): MutableList<exampleDatas>{
        // 임시 data 사용, 날짜(format 설정)에 해당하는 todo를 서버에서 불러옴
        //Log.d("mobileApp", "addDatas")
        var data = mutableListOf<exampleDatas>()
        data.add(exampleDatas("할일 1", "유수연", "시간"))
        data.add(exampleDatas("할일 2", "유숨숨", "시간"))

        return data
    }
}