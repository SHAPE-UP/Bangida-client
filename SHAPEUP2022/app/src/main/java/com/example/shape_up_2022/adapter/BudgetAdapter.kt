package com.example.shape_up_2022.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shape_up_2022.data.BudgetItem
import com.example.shape_up_2022.databinding.BudgetItemBinding

class BudgetAdapter(val datas: MutableList<BudgetItem>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int)  // 뷰와 포지션값
        fun onEditClick(v: View?, position: Int)  // 수정
        fun onDeleteClick(v: View?, positon: Int)  // 삭제
    }

    lateinit var mListener: OnItemClickListener

    // 메인에서 사용할 메소드
    public fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mListener = listener
    }

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(BudgetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as MyViewHolder).binding
        binding.itemname.text= datas!![position].itemname
        binding.price.text = datas!![position].price.toString()
        binding.category.text = datas!![position].category.toString()
        binding.term.text= datas!![position].term.toString() ?: ""
    }

    // 뷰 홀더를 갖고 있는 형태
    inner class MyViewHolder(val binding: BudgetItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener { view ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onEditClick(view, position)
                    }
                }
            }

            /* 수정 버튼을 없애고 항목을 클릭하면 수정되도록 함
            binding.btnEdit.setOnClickListener(View.OnClickListener { view ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onEditClick(view, position)
                    }
                }
            })

             */

            /* 삭제 버튼 삭제
            binding.btnDelete.setOnClickListener(View.OnClickListener { view ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onDeleteClick(view, position)
                    }
                }
            })

             */
        }
    }

}