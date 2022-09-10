package com.example.shape_up_2022.todo

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shape_up_2022.R
import com.example.shape_up_2022.common.MyPageActivity
import com.example.shape_up_2022.common.SimulationActivity
import com.example.shape_up_2022.achieve.AchieveActivity
import com.example.shape_up_2022.adapter.TodoAdapter
import com.example.shape_up_2022.adapter.TodoViewHolder
import com.example.shape_up_2022.common.MainActivity
import com.example.shape_up_2022.common.SaveSharedPreference
import com.example.shape_up_2022.data.TodoItem
import com.example.shape_up_2022.data.Todorole
import com.example.shape_up_2022.databinding.ActivityToDoBinding
import com.example.shape_up_2022.databinding.TodoAddBinding

class TodoActivity : AppCompatActivity() {
    companion object {
        var datas: MutableList<TodoItem>? = mutableListOf<TodoItem>(
            TodoItem("목욕하기", Todorole("성민언니"), "22시", "목욕하기"),
            TodoItem("간식 주기", Todorole("영주"), "17시", "간식 주기")
        )  // 샘플데이터 목록(1)
        lateinit var adapter: TodoAdapter  // (2)리사이클러뷰.어댑터
        lateinit var todoRecyclerView: RecyclerView  // (3)리사이클러뷰

        fun updateTodoList(array: MutableList<TodoItem>?) {
            datas = array  // 단순 데이터 배열 저장(1)
            adapter = TodoAdapter(datas)  // 리사이클러뷰 어댑터 형태로 변환해 저장(2)
            todoRecyclerView.adapter = adapter  // 리사이클러뷰에 반영
            adapter.notifyDataSetChanged()  // 리사이클러뷰 업데이트
        }
    }

    lateinit var binding: ActivityToDoBinding
    lateinit var todoAdd: TodoAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 뷰바인딩 초기화
        binding = ActivityToDoBinding.inflate(layoutInflater)  // 액티비티 레이아웃
        todoRecyclerView = binding.todoRecyclerView  // 리사이클러뷰 companion object에 저장(3)
        todoAdd = TodoAddBinding.inflate(layoutInflater)  // 투두 추가 모달
        setContentView(binding.root)

        // 프래그먼트 연결 - 캘린더 프래그먼트
        viewFragment(CalendarFragment(), R.id.todo_calendar)

        // 리사이클러뷰 설정 및 초기화
        val layoutManager = LinearLayoutManager(this)
        todoRecyclerView.layoutManager = layoutManager
        adapter = TodoAdapter(datas)  // 초기값 데이터 저장(2)
        todoRecyclerView.adapter = adapter  // 초기값 설정(3)

        // 모달창에서 저장/취소 버튼 눌렀을 때 발생하는 이벤트
        val save = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if (p1 == DialogInterface.BUTTON_POSITIVE) { // [저장] 버튼을 눌렀을 경우

                    // 새 입력값을 datas(리사이클러뷰 데이터 배열)(1)에 추가
                    datas?.add(
                        TodoItem(
                            todoAdd.todowork.text.toString(),
                            Todorole(todoAdd.todorole.text.toString()),
                            todoAdd.todotime.text.toString()
                        )
                    )

                    // 리사이클러뷰 업데이트
                    adapter.notifyItemInserted(adapter.itemCount)

                    // DB에 추가
                    // 서버 요청 registerTodo


                    // 입력 폼(다이얼로그) 초기화 - null이 제출되면 오류남, 처리 필요
                    todoAdd.todowork.setText("")
                    todoAdd.todorole.setText("")
                    todoAdd.todotime.setText("")

                } else if (p1 == DialogInterface.BUTTON_NEGATIVE) {

                }
            }
        }

        // 새 항목 추가 버튼 - 대화상자 열림
        val alert = AlertDialog.Builder(this)
            .setTitle("ToDoList 추가")
            .setView(todoAdd.root)
            .setPositiveButton("저장", save)
            .setNegativeButton("취소", save)
            .create()

        binding.todoAdd.setOnClickListener {
            alert.show()
        }

        // 탭바 연결
        binding.navHome.setOnClickListener {
            val intent_home = Intent(this, MainActivity::class.java)
            startActivity(intent_home)
            overridePendingTransition(0, 0);
            finish()
        }

        binding.navTodo.setOnClickListener {
            val intent_todo = Intent(this, TodoActivity::class.java)
            startActivity(intent_todo)
            overridePendingTransition(0, 0);
            finish()
        }

        binding.navSimulation.setOnClickListener {
            val intent_simul = Intent(this, SimulationActivity::class.java)
            startActivity(intent_simul)
            overridePendingTransition(0, 0);
            finish()
        }

        binding.navAchievement.setOnClickListener {
            val intent_achieve = Intent(this, AchieveActivity::class.java)
            startActivity(intent_achieve)
            overridePendingTransition(0, 0)
            finish()
        }

        binding.navMypage.setOnClickListener {
            val intent_mypage = Intent(this, MyPageActivity::class.java)
            startActivity(intent_mypage)
            overridePendingTransition(0, 0);
            finish()
        }
    }

    // 프래그먼트 연결
    private fun viewFragment(fragment: Fragment, location: Int) {
        Log.d("app_test", "viewFragment start")
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        transaction.add(location, fragment)
        transaction.commit()

    }

    override fun onStart() {
        super.onStart()

        /* 가족 그룹 없는 사용자의 접근을 제한 */
        val eventHandler = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(p1 == DialogInterface.BUTTON_POSITIVE) {
                    val intent = Intent(this@TodoActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        var builder = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("가족 그룹에 가입되어 있지 않은 사용자")
            .setIcon(R.drawable.maltese)
            .setMessage("가족 그룹에 먼저 가입해주세요.")
            .setPositiveButton("확인", eventHandler)
            .setCancelable(false)

        if(SaveSharedPreference.getFamliyID(this)!! == ""){
            builder.show()
        }
    }

}
