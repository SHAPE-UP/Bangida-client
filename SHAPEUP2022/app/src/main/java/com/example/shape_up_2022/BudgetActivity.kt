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
import com.example.shape_up_2022.databinding.BudgetEditBinding
import com.example.shape_up_2022.databinding.BudgetMainBinding

class BudgetActivity : AppCompatActivity() {
    lateinit var binding: BudgetMainBinding
    var datas: MutableList<BudgetItem>? = mutableListOf<BudgetItem>(
        BudgetItem("지출내역", 1234, 2, 8),
        BudgetItem("샘플 목록")
    )
    lateinit var adapter: BudgetAdapter
    lateinit var budgetAdd: BudgetAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BudgetMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        budgetAdd = BudgetAddBinding.inflate(layoutInflater)

        // 리사이클러뷰 설정
        val layoutManager = LinearLayoutManager(this)
        binding.budgetRecyclerView.layoutManager = layoutManager // (3) 레이아웃 매니저
        adapter = BudgetAdapter(datas)
        binding.budgetRecyclerView.adapter = adapter // (2) 어댑터 설정

        // [항목 추가] 모달창에서 저장/취소 버튼 눌렀을 때 발생하는 이벤트
        val add = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if (p1==DialogInterface.BUTTON_POSITIVE) { // [저장] 버튼을 눌렀을 경우

                    // 입력값을 id로 하나하나 찾아와서 [데이터]에 저장해야 함
                    Log.d("BudgetActivity", "행 추가 저장하기")
                    datas?.add(BudgetItem(budgetAdd.itemname.text.toString(),
                        budgetAdd.price.text.toString().toInt(),
                        budgetAdd.category.selectedItemId.toInt(),
                        budgetAdd.term.text.toString().toInt()
                    ))

                    // 간격+단위 어떻게 가져오지

                    adapter.notifyItemInserted(adapter.itemCount)

                    budgetAdd.itemname.setText("")
                    budgetAdd.price.setText("")
                    budgetAdd.category.setSelection(0)
                    budgetAdd.term.setText("7")

                    binding.sumResult.setText(getSum().toString())
                }
                else if (p1==DialogInterface.BUTTON_NEGATIVE) {
                    Log.d("BudgetActivity", "행 추가 취소")
                }
            }

        }

        // [항목 추가] 다이얼로그
        val alert = AlertDialog.Builder(this)
            .setTitle("항목 추가")
            .setView(budgetAdd.root)
            .setPositiveButton("저장", add)
            .setNegativeButton("취소", add)
            .create()

        binding.budgetAdd.setOnClickListener {
            alert.show()
        }

        //리사이클러뷰 아이템 클릭 이벤트
        adapter.setOnItemClickListener(object : BudgetAdapter.OnItemClickListener {
            override fun onItemClick(v: View?, position: Int) {
                // 아이템클릭이벤트
                Log.d("BudgetActivity", "클릭 "+position)
            }

            override fun onEditClick(v: View?, position: Int) {
                Log.d("BudgetActivity", "수정 "+position)
                editItem(datas!![position], position)
            }

            override fun onDeleteClick(v: View?, position: Int) {
                Log.d("BudgetActivity", "삭제 "+position)
                datas!!.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        })

        /* 통계 - 라디오버튼 선택 */

        binding.sumMode.setOnCheckedChangeListener { group, chekedId ->
            when(chekedId) {
//                R.id.sum_essential -> binding.sumResult.text = "필수금액"
//                R.id.sum_extra -> binding.sumResult.text = "여유금액"
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

    // 필터링 함수 구현 필요
    fun getSum(): Int {
        var sum = 0
        if (binding.sumEssential.isSelected) {

        }
        else if (binding.sumExtra.isSelected) {

        }
        return sum
    }

    private fun editItem(budgetitem:BudgetItem, position:Int) {
        var budgetEdit = BudgetEditBinding.inflate(layoutInflater, null, false) // 중요ㄴㅇ
        budgetEdit.itemname.setText(budgetitem.itemname)
        budgetEdit.price.setText(budgetitem.price.toString())
        budgetEdit.category.setSelection(budgetitem.category)
        budgetEdit.term.setText(budgetitem.term.toString())

        val edit = object : DialogInterface.OnClickListener {

            override fun onClick(p0: DialogInterface?, p1: Int) {
                if (p1==DialogInterface.BUTTON_POSITIVE) { // [저장] 버튼을 눌렀을 경우
                    Log.d("BudgetActivity", "수정 - 저장")
                    datas?.set(position, BudgetItem(budgetEdit.itemname.text.toString(),
                        budgetEdit.price.text.toString().toInt(),
                        budgetEdit.category.selectedItemId.toInt(),
                        budgetEdit.term.text.toString().toInt()
                    ))
                    adapter.notifyItemChanged(position)
                }
                else if (p1==DialogInterface.BUTTON_NEGATIVE) {
                    Log.d("BudgetActivity", "수정 - 삭제")
                    datas!!.removeAt(position)
                    adapter.notifyItemRemoved(position)
                }
                else if (p1==DialogInterface.BUTTON_NEUTRAL) {
                    Log.d("BudgetActivity", "수정 - 취소")
                }
            }
        }

        var builder = AlertDialog.Builder(this)
            .setTitle("항목 수정")
            .setView(budgetEdit.root)
            .setPositiveButton("저장", edit)
            .setNegativeButton("삭제", edit)
            .setNeutralButton("취소", edit)
            .setCancelable(true)
            .create()

        builder.show()
        Log.d("BudgetActivity", "수정 모달")
    }

}