package com.example.shape_up_2022.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shape_up_2022.adapter.TodoCalendarAdapter
import com.example.shape_up_2022.databinding.ActivityTodoCalendarBinding
import java.text.SimpleDateFormat
import java.util.*

class TodoCalendarActivity : AppCompatActivity() {
    lateinit var binding: ActivityTodoCalendarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)


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