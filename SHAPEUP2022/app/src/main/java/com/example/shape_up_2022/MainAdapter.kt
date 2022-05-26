package com.example.shape_up_2022

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shape_upapptest.databinding.ItemMainMenuListBinding
import com.example.shape_upapptest.databinding.ItemTodoBinding

class ListViewHolder(val recycleBinding:ItemMainMenuListBinding) : RecyclerView.ViewHolder(recycleBinding.root) {}// recycler에 대한 목록
class TodoViewHolder(val todoBinding: ItemTodoBinding) : RecyclerView.ViewHolder(todoBinding.root){} // todo에 대한 목록

class MainListAdapter(val datas: MutableList<MainListViewData>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListViewHolder(ItemMainMenuListBinding.inflate(LayoutInflater.from(parent.context), parent, false ))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bindingRecycle = (holder as ListViewHolder).recycleBinding
        bindingRecycle.mainItemListRecycle.text = datas!![position].title
    }

    override fun getItemCount(): Int {
        return datas?.size ?: 0 // datas.size가 0일때 0 반환
    }
}

class TodoListAdapter(val datas: MutableList<MainTodoListViewData>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TodoViewHolder(ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false ))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val bindingTodo = (holder as TodoViewHolder).todoBinding
            bindingTodo.mainItemListTodo.text = datas!![position].todo
    }

    override fun getItemCount(): Int {
        return datas?.size ?: 0 // datas.size가 0일때 0 반환
    }

    override fun getItemViewType(position: Int): Int {
        return datas?.get(position)?.type ?: 0 // 0으로 설정 -> 타입이 없으므로 오류 발생하도록
    }
}