package com.example.shape_up_2022

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shape_up_2022.databinding.BudgetAddBinding
import com.example.shape_up_2022.databinding.BudgetMainBinding

class BudgetActivity : AppCompatActivity() {
    lateinit var binding: BudgetMainBinding
    var datas: MutableList<BudgetItem>? = mutableListOf<BudgetItem>()
    lateinit var adapter: BudgetAdapter
    lateinit var budgetAdd: BudgetAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var categoryString = getResources().getStringArray(R.array.category) // res/values/array.xml에 선언한 배열 가져오기

        binding = BudgetMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        budgetAdd = BudgetAddBinding.inflate(layoutInflater)

        // 리사이클러뷰 설정
        val layoutManager = LinearLayoutManager(this)
        binding.budgetRecyclerView.layoutManager = layoutManager
        adapter = BudgetAdapter(datas)
        binding.budgetRecyclerView.adapter = adapter

        // 모달창에서 저장/취소 버튼 눌렀을 때 발생하는 이벤트
        val save = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if (p1==DialogInterface.BUTTON_POSITIVE) { // [저장] 버튼을 눌렀을 경우

                    // 입력값을 id로 하나하나 찾아와서 [데이터]에 저장해야 함
                    Log.d("budgetApp", "행 추가 저장하기")
                    datas?.add(BudgetItem(budgetAdd.itemname.text.toString(),
                        budgetAdd.price.text.toString().toInt(),
                        categoryString[budgetAdd.category.selectedItemId.toInt()],
                        budgetAdd.term.text.toString().toInt()
                    ))

                    // 간격+단위 어떻게 가져오지

                    // 리사이클러뷰 업데이트
                    adapter.notifyItemInserted(adapter.itemCount)
                    
                    // 초기화 - null이 입력되면 오류남, 처리 필요
                    budgetAdd.itemname.setText("")
                    budgetAdd.price.setText("")
                    budgetAdd.category.setSelection(0)
                    budgetAdd.term.setText("7")

                    // 합계 칸 업데이트
                    binding.sumResult.setText(getSum().toString())
                }
                else if (p1==DialogInterface.BUTTON_NEGATIVE) {
                    Log.d("budgetApp", "행 추가 취소")
                }
            }
        }

        // 새 항목 추가 버튼 - 대화상자 열림
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

        // 각각의 선택사항에 맞춰서 계산하는 함수 짜야 함 (이 소스파일 하단 참고)
        // 카테고리별 필터, 카테고리를 묶어서 (간식, 사료 -> 식비) category_total
        binding.categoryTotal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2) {
                    // 임시 코드
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
                    // 임시 코드
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

    // 필터링 함수 구현 필요
    fun getSum(): Int {
        var sum = 0
        if (binding.sumEssential.isSelected) {

        }
        else if (binding.sumExtra.isSelected) {

        }
        return sum
    }


}