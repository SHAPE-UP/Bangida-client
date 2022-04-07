package com.example.shape_up_2022

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shape_up_2022.R
import com.example.shape_up_2022.databinding.BudgetAddBinding
import com.example.shape_up_2022.databinding.BudgetMainBinding

class BudgetActivity : AppCompatActivity() {
    lateinit var binding: BudgetMainBinding
    var datas_itemname: MutableList<String>? = mutableListOf<String>()
    lateinit var adapter: BudgetAdapter
    lateinit var budgetAdd: BudgetAddBinding

    // lateinit var intent_budget: Intent
    var intent_budget = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        datas_itemname?.add("그냥 목록")


        binding = BudgetMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        budgetAdd = BudgetAddBinding.inflate(layoutInflater)

        datas_itemname?.add("그냥 목록2") // 얜 안 뜨네 어떻게 업데이트시키지 - 리사이클러뷰 업데이트 찾아보는중...
        // 어댑터.notifyDataSetChanged() 이게 왜 안먹지
        // 엥???? 뭐가 늘어나긴함


        val layoutManager = LinearLayoutManager(this)
        binding.budgetRecyclerView.layoutManager = layoutManager // (3) 레이아웃 매니저
        adapter = BudgetAdapter(datas_itemname)
        binding.budgetRecyclerView.adapter = adapter // (2) 어댑터 설정

        // 사후 처리용 메소드... 인데 필요할 것 같지 않음
        val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            it.data!!.getStringExtra("itemname")?.let {
                datas_itemname?.add(it)
                adapter.notifyDataSetChanged()
            }
        }

        // 모달창에서 저장/취소 버튼 눌렀을 때 발생하는 이벤트
        val save = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if (p1==DialogInterface.BUTTON_POSITIVE) { // [저장] 버튼을 눌렀을 경우

                    // 입력값을 id로 하나하나 찾아와서 [데이터]에 저장해야 함
                    // 현재: data_itemlist에 상품명만 저장해볼 것
                    // 최종: json 형태로
                    Log.d("budgetApp", "행 추가 저장하기")
                    datas_itemname?.add("예시")
                    //datas_itemname?.add(budgetAdd.itemname.text.toString())
                    // intent_budget.putExtra("price", budgetAdd.price.text.toString())
                    // 카테고리는 어떻게 전달하지 array에서 꺼내와야 하는데

                    // 간격+단위 어떻게 가져오지


                    adapter.notifyDataSetChanged()
//                    setResult(Activity.RESULT_OK, intent) // 액티비티를 전환하는 게 아닌데 이런 식으로 해야 됨?


                }
                else if (p1==DialogInterface.BUTTON_NEGATIVE) {
                    Log.d("budgetApp", "행 추가 취소")
                }
            }
        }

        /*
            // 13장 실습의 메인액티비티에 있던 코드 - 무슨 뜻인지 모르고 그대로 가져옴
            datas_itemname = savedInstanceState?.let {
                it.getStringArrayList("datas_itemname")?.toMutableList()
            } ?: let {
                mutableListOf<String>()
            }

         */
        // 그냥 초기화해버리는데..?

        val alert = AlertDialog.Builder(this)
            .setTitle("분류 선택")
            .setView(budgetAdd.root)
            .setPositiveButton("저장", save)
            .setNegativeButton("취소", save)
            .create()

        binding.budgetAdd.setOnClickListener {
            alert.show()
        }


        /* 통계 - 라디오버튼 선택 */

        binding.sumMode.setOnCheckedChangeListener { group, chekedId ->
            when(chekedId) {
                R.id.sum_essential -> binding.sumResult.text = "필수금액"
                R.id.sum_extra -> binding.sumResult.text = "여유금액"
            }
        }

        // 각각의 선택사항에 맞춰서 계산하는 함수 짜야 함
        // 카테고리별 필터, 카테고리를 묶어서 (간식, 사료 -> 식비) category_total
        binding.categoryTotal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2) {
                    0 -> binding.sumResult.text = "0"
                    1 -> binding.sumResult.text = "1"
                    2 -> binding.sumResult.text = "2"
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                true
            }
        }
        // 기간별 필터 period
        binding.period.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2) {
                    0 -> binding.sumResult.text = "모든 기간"
                    1 -> binding.sumResult.text = "1개월"
                    2 -> binding.sumResult.text = "3개월"
                    3 -> binding.sumResult.text = "6개월"
                    4 -> binding.sumResult.text = "1년"
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                true
            }
        }

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("datas_itemname", ArrayList(datas_itemname))
    }
}