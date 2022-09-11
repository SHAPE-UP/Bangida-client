package com.example.shape_up_2022.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shape_up_2022.databinding.TodoitemBinding
import com.example.shape_up_2022.data.TodoItem

class TodoViewHolder(val binding: TodoitemBinding): RecyclerView.ViewHolder(binding.root)
class TodoAdapter (val datas: MutableList<TodoItem>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TodoViewHolder(
            TodoitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as TodoViewHolder).binding

        binding.todowork.text = datas!![position].todowork
        binding.todorole.text = datas!![position].todorole!!.name ?: ""
        binding.todotime.text = datas!![position].todotime.toString() + "시"

        // todoref: 시뮬레이션 텍스트로 치환
        var todoreftext = when (datas!![position].todoref) {
            0 -> ""
            1 -> "강아지 관리"
            2 -> "먹이 챙겨주기"
            3 -> "위생 관리/목욕"
            4 -> "배변 관리"
            5 -> "건강 관리"
            6 -> "훈련하기"
            7 -> "산책"
            8 -> "병원"
            9 -> "미용"
            else -> ""
        }
        binding.todoref.text = todoreftext

        // done: 체크 여부 다르게 표시
        binding.tododone.isChecked = datas!![position].done == true

    }
}