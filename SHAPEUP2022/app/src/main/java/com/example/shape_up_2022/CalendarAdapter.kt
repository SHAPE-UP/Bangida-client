package com.example.shape_up_2022

import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shape_up_2022.databinding.ItemCalendarListBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class CalendarViewHolder(val binding: ItemCalendarListBinding) : RecyclerView.ViewHolder(binding.root)

class CalendarAdapter(val datas: List<CalendarValue>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = ItemCalendarListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as CalendarViewHolder).binding
        binding.calDate.text = datas!![position].calendar_date
        binding.calDay.text = datas!![position].calendar_day

        var today = binding.calDate.text

        // 오늘 날짜
        val now = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.now().format(DateTimeFormatter.ofPattern("dd").withLocale(Locale.forLanguageTag("ko")))
        } else {
            Log.d("appTest","달력 구현이 되지 않습니다.")
        }
        // 오늘 날짜와 캘린더의 오늘 날짜가 같을 경우 background 색 적용하기
        if (today == now) {
            binding.weekCardview.setBackgroundResource(R.drawable.background_calendar)
            binding.calDay.setTextColor(Color.WHITE)
            binding.calDate.setTextColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }

}