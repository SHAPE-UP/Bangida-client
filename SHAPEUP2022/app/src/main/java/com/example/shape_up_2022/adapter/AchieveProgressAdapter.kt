package com.example.shape_up_2022.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shape_up_2022.R
import com.example.shape_up_2022.achieve.AchieveFragment1
import com.example.shape_up_2022.databinding.ItemAchieveBinding
import com.example.shape_up_2022.retrofit.myItem


class ViewHolderAchieve(val binding: ItemAchieveBinding): RecyclerView.ViewHolder(binding.root)
class AchieveProgressAdapter(val context: Context, val datas:ArrayList<AchieveFragment1.AchieveProgress>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderAchieve(ItemAchieveBinding.inflate(LayoutInflater.from(parent.context), parent,false ))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ViewHolderAchieve).binding
        val model = datas!![position]

        // 업적 목록
        binding.achieveContent.text = model.content

        // 스탬프 찍기
        if(model.checked) {
            Glide.with(binding.root)
                .load(R.drawable.ic_icon_dog_orange)
                .override(30,30)
                .into(binding.achieveStamp)
        }
        else{
            Glide.with(binding.root)
                .load(R.drawable.ic_icon_dog_gray)
                .override(30,30)
                .into(binding.achieveStamp)
        }
    }

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }
}