package com.example.shape_up_2022

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shape_up_2022.databinding.FragmentSimHealthGraphBinding
import com.github.mikephil.charting.data.ChartData
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SimHealthGraphFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SimHealthGraphFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding : FragmentSimHealthGraphBinding
    lateinit var item : ChartData
    private val chartData = ArrayList<ChartData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    // 예시 데이터 클래스
    data class ChartData(var labelData: String, var lineData: Double)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSimHealthGraphBinding.inflate(inflater, container, false)

        // 차트 데이터 불러오기
        chartData.clear()

        // 차트 넣기
        addChartItem("1월", 7.9)
        addChartItem("2월", 10.9)
        addChartItem("3월", 8.5)
        addChartItem("4월", 4.3)
        addChartItem("5월", 1.2)

        //자료 넘기기
        lineChartFunc(chartData)

        return binding.root
    }

    private fun addChartItem(label: String, data: Double){
        item = ChartData("",0.0)
        item.labelData = label
        item.lineData = data
        chartData.add(item)
    }

    private fun lineChartFunc(chartData: ArrayList<ChartData>){
        val lineChart = binding.healthLinechart

        val entries=mutableListOf<Entry>()
        for(item in chartData){
            entries.add(Entry(item.labelData.replace(("[^\\d.]").toRegex(),"").toFloat(),item.lineData.toFloat()))
        }

        val linedataSet = LineDataSet(entries, "라인차트 예시")
        linedataSet.color = Color.BLUE
        linedataSet.setCircleColor(Color.DKGRAY)
        linedataSet.circleHoleColor = Color.WHITE

        val datasets = ArrayList<ILineDataSet>()
        datasets.add(linedataSet)

        val data = LineData(datasets)

        lineChart.data = data
        lineChart.description = null
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SimHealthGraphFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SimHealthGraphFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}