package com.example.shape_up_2022

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shape_up_2022.databinding.TodoitemBinding

class MyViewHolder(val binding: TodoitemBinding): RecyclerView.ViewHolder(binding.root)
class TodoAdapter (val datas: MutableList<TodoItem>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(TodoitemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        binding.todowork.text = datas!![position].todowork
        binding.todorole.text = datas!![position].todorole
        binding.todosetting.text = datas!![position].todosetting
        binding.todoref.text = datas!![position].todoref
    }
}