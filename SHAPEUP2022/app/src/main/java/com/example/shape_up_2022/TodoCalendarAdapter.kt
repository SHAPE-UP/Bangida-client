package com.example.shape_up_2022

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shape_up_2022.databinding.ItemCalendarTodoBinding


class ViewHolderCalendar(val binding: ItemCalendarTodoBinding): RecyclerView.ViewHolder(binding.root)
class TodoCalendarAdapter(val context: Context, val datas:MutableList<TodoCalendarActivity.exampleDatas>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderCalendar(ItemCalendarTodoBinding.inflate(LayoutInflater.from(parent.context), parent,false ))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolderCalendar).binding
        val model = datas!![position]

        binding.todoCalContent.text = model.todo
        binding.todoCalTime.text = model.time
        binding.todoCalUser.text = model.user
    }

    override fun getItemCount(): Int {
        //Log.d("mobileApp", "${datas?.size}")
        return datas?.size ?: 0
    }

}