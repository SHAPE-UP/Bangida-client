package com.example.shape_up_2022

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shape_up_2022.databinding.FragmentBreed1Binding
import com.example.shape_up_2022.databinding.ItemBreedBinding
import com.example.shape_up_2022.simulation.SimStartNamingActivity
import com.example.shape_up_2022.simulation.SimWalkSearchAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

// 데이터 클래스 DogInfo
data class DogInfo(var breed:String, var size:String, var group:String, var coat:String,
                   var height:String, var weight:String, var character:String, var body:String)
// 뷰 홀더
class MyBreedViewHolder(val binding : ItemBreedBinding) : RecyclerView.ViewHolder(binding.root)
// 리사이클러뷰 어댑터
class MyBreedAdapter(val datas: MutableList<DogInfo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyBreedViewHolder(ItemBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    // 데이터와 뷰 홀더를 연결
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // position은 뷰 홀더가 가진 뷰 중 몇 번째인지
        val binding = (holder as MyBreedViewHolder).binding // 타입 캐스팅 필요
        binding.breedBreed.text = datas[position].breed
        binding.breedCoat.text = datas[position].coat
        binding.breedSize.text = datas[position].size
        binding.breedGroup.text = datas[position].group
        binding.breedCharacter.text = datas[position].character

        // (1) 리스트 내 항목 클릭 시 onClick() 호출
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    // (2) 리스너 인터페이스
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener

}

/**
 * A simple [Fragment] subclass.
 * Use the [BreedFragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class BreedFragment1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentBreed1Binding.inflate(inflater, container, false)

        /* 리사이클러뷰 */
        val sampledatas = mutableListOf<DogInfo>()
        sampledatas.add(DogInfo("말티즈", "소형견", "토이 그룹", "장모종", "20~25cm", "3~4kg", "온순한, 장난스러운, 총명한", "둥근 머리, 짧은 주둥이, 까만코와 갈색 눈이 특징이다. 몸통은 작고 길이는 키와 비슷하다. 귀는 긴 털과 함께 쳐져있고, 약간 어두운 피부색을 가졌다. 햇빛에 노출이 적은 경우, 코의 색이 바래거나 분홍색 혹은 밝은 갈색으로 변할 수 있으나 햇빛을 쐼으로써 종종 다시 검은색으로 돌아온다."))
        sampledatas.add(DogInfo("진돗개", "중대형견", "워킹 그룹", "이중모", "cm", "kg", "깔끔한, 영리한, 충성스러운", "신체적 특징"))
        sampledatas.add(DogInfo("래브라도 리트리버", "대형견", "스포팅 그룹", "단모종", "50~60cm", "kg", "성격", "신체적 특징"))
        val layoutManager = GridLayoutManager(activity, 2) // 그리드레이아웃매니저(액티비티, column)
        binding.breedRecyclerview1.layoutManager = layoutManager // 레이아웃 설정
        var adapter = MyBreedAdapter(sampledatas)
        binding.breedRecyclerview1.adapter = adapter // 어댑터 설정

        // 리사이클러뷰 이벤트
        adapter.setItemClickListener(object: MyBreedAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                // 클릭했을 때 액티비티 이동
                // 현재는 말티즈 입양 과정만 있음, 추후에 정보를 추가할 때 position을 사용한 관리 필요
                if(position == 0) {
                    val intent = Intent(context, SimStartNamingActivity::class.java)
                    startActivity(intent)
                    // 프래그먼트 떼어내기
                }
            }
        })

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BreedFragment1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BreedFragment1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}