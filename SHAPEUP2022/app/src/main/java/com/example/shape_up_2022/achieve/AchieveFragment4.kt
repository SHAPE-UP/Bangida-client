package com.example.shape_up_2022.achieve

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shape_up_2022.common.PreferenceActivity
import com.example.shape_up_2022.common.SimulationActivity
import com.example.shape_up_2022.databinding.AchieveFragment4Binding
import com.example.shape_up_2022.databinding.ActivityAchieveBinding
import com.example.shape_up_2022.databinding.ActivityMyPageBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment4.newInstance] factory method to
 * create an instance of this fragment.
 */
class AchieveFragment4 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding : AchieveFragment4Binding

//    private val chartData1 = ArrayList<ChartData>()  // 꺾은선그래프1의 데이터
//    private val chartData2 = ArrayList<ChartData>()  // 꺾은선그래프2의 데이터

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    // 예시 데이터 클래스
    //data class ChartData(var labelData: String, var lineData: Double)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AchieveFragment4Binding.inflate(inflater, container, false)
        binding.gotoPreferenceActivity.setOnClickListener {
            activity?.let{
                val intent = Intent(context, PreferenceActivity::class.java)
                startActivity(intent)
            }
        }

//        val loveLinechart = binding.loveLinechart
//
//        // 차트 데이터 비우기
//        chartData1.clear()
//        chartData2.clear()
//
//        //chartData하나에 linedataset이 set1, set2로 두개의 라인을 가진 그래프
//        val entry1 = ArrayList<Entry>()
//        val entry2 = ArrayList<Entry>()
//
//        // 차트 데이터 넣기
//        chartData1.add(ChartData("1월", 8.3))
//        chartData1.add(ChartData("2월", 8.9))
//        chartData1.add(ChartData("3월", 8.8))
//        chartData1.add(ChartData("4월", 9.3))
//        chartData1.add(ChartData("5월", 9.2))
//        chartData1.add(ChartData("6월", 9.9))
//
//        chartData2.add(ChartData("1월", 7.9))
//        chartData2.add(ChartData("2월", 7.9))
//        chartData2.add(ChartData("3월", 8.5))
//        chartData2.add(ChartData("4월", 8.3))
//        chartData2.add(ChartData("5월", 9.0))
//        chartData2.add(ChartData("6월", 9.3))
//
//        dataToEntry(chartData1, entry1)
//        dataToEntry(chartData2, entry2)
//
//        val chartData = LineData() // 꺾은선그래프 여러 개를 담음
//
//        val set1 = LineDataSet(entry1, "유수")
//        set1.color = Color.BLUE
//        set1.setCircleColor(Color.DKGRAY)
//        set1.circleHoleColor = Color.WHITE
//        chartData.addDataSet(set1)  // 데이터셋1 추가
//
//        val set2 = LineDataSet(entry2, "유즈")
//        set2.color = Color.YELLOW
//        set2.setCircleColor(Color.RED)
//        set2.circleHoleColor = Color.WHITE
//        chartData.addDataSet(set2)  // 데이터셋2 추가
//
//        loveLinechart.setData(chartData)  // 차트에 데이터들 추가
//        loveLinechart.invalidate()
//
       return binding.root
    }
//
//
//    private fun dataToEntry(chartdata: ArrayList<ChartData>, entry: ArrayList<Entry>){
//        for(i in 0 until chartdata.size) {
//            val e = Entry(chartdata[i].labelData.replace(("[^\\d.]").toRegex(),"").toFloat(), chartdata[i].lineData.toFloat())
//            entry.add(e)
//        }
//
//    }

        companion object {
            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @param param1 Parameter 1.
             * @param param2 Parameter 2.
             * @return A new instance of fragment Fragment3.
             */
            // TODO: Rename and change types and number of parameters
            @JvmStatic
            fun newInstance(param1: String, param2: String) =
                AchieveFragment4().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
        }
}