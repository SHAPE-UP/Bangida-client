package com.example.shape_up_2022

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petcarestarter1.databinding.BudgetItemBinding

class MyViewHolder(val binding: BudgetItemBinding): RecyclerView.ViewHolder(binding.root)

class BudgetAdapter(val datas_itemname: MutableList<String>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return datas_itemname?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(BudgetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as MyViewHolder).binding
        binding.itemname.text= datas_itemname!![position]
    }
}