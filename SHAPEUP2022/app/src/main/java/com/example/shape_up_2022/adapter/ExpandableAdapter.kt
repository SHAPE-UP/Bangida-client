package com.example.shape_up_2022.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shape_up_2022.R
import com.example.shape_up_2022.ToggleAnimation
import com.example.shape_up_2022.data.Person
import com.example.shape_up_2022.databinding.ItemRowBinding

class ExpandableAdapter(
    private val personList: List<Person>
) : RecyclerView.Adapter<ExpandableAdapter.MyViewHolder>() {

    class MyViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(person: Person) {
            val txtName = itemView.findViewById<TextView>(R.id.txt_name)
            val imgMore = itemView.findViewById<ImageButton>(R.id.img_more)
            val txtExplain = itemView.findViewById<TextView>(R.id.txt_explain)
            val layoutExpand = itemView.findViewById<LinearLayout>(R.id.layout_expand)

            txtName.text = person.name
            txtExplain.text = person.exp

            imgMore.setOnClickListener {
                val position = bindingAdapterPosition
                // 1
                val show = toggleLayout(!person.isExpanded, it, layoutExpand, position)
                person.isExpanded = show
            }
        }

        private fun toggleLayout(isExpanded: Boolean, view: View, layoutExpand: LinearLayout, position: Int): Boolean {
            // 2
            ToggleAnimation.toggleArrow(view, isExpanded)
            if (isExpanded) {
                ToggleAnimation.expand(layoutExpand)
            } else {
                ToggleAnimation.collapse(layoutExpand)
            }
            return isExpanded
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(personList[position])
    }

    override fun getItemCount(): Int {
        return personList.size
    }

}