package com.example.shape_up_2022.todo

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shape_up_2022.R
import com.example.shape_up_2022.achieve.AchieveActivity
import com.example.shape_up_2022.adapter.TodoAdapter
import com.example.shape_up_2022.common.*
import com.example.shape_up_2022.data.TodoItem
import com.example.shape_up_2022.data.Todorole
import com.example.shape_up_2022.data.User
import com.example.shape_up_2022.databinding.ActivityToDoBinding
import com.example.shape_up_2022.databinding.TodoAddBinding
import com.example.shape_up_2022.retrofit.*
import com.example.shape_up_2022.simulation.SimStartActivity
import com.google.android.youtube.player.internal.i
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class TodoActivity : AppCompatActivity() {
    companion object {
        var datas: MutableList<TodoItem>? = mutableListOf<TodoItem>(
            //TodoItem("목욕하기", Todorole("성민언니"), 22, 3),
            //TodoItem("간식 주기", Todorole("영주"), 17, 2)
        )  // 샘플데이터 목록(1)
        lateinit var adapter: TodoAdapter  // (2)리사이클러뷰.어댑터
        lateinit var todoRecyclerView: RecyclerView  // (3)리사이클러뷰

        lateinit var dateString: String  // "yyyy-MM-dd"

        fun updateTodoList(array: MutableList<TodoItem>?) {
            datas = array  // 단순 데이터 배열 저장(1)
            adapter = TodoAdapter(datas)  // 리사이클러뷰 어댑터 형태로 변환해 저장(2)
            todoRecyclerView.adapter = adapter  // 리사이클러뷰에 반영
            adapter.notifyDataSetChanged()  // 리사이클러뷰 업데이트
        }
    }

    lateinit var binding: ActivityToDoBinding  // 액티비티 레이아웃 뷰 바인딩
    lateinit var todoDialog: TodoAddBinding  // 항목 추가 다이얼로그 뷰 바인딩
    lateinit var calendar: CalendarFragment  // 달력 프래그먼트
    lateinit var familyFragment: FamilyFragment  // 가족 목록 프래그먼트

    lateinit var alert: AlertDialog  // 항목 추가 다이얼로그

    lateinit var family: MutableList<User>  // 가족 데이터 배열
    lateinit var nameArray : List<String>  // todorole 담당자 드롭다운
    lateinit var userIdArray : List<String>  // todorole 담당자 드롭다운
    var simArray: List<String> = listOf("",
        "강아지 관리", "먹이", "위생 관리/목욕", "배변 관리", "건강 관리", "훈련",
        "산책", "병원", "미용") // todoref 시뮬레이션 드롭다운

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 뷰바인딩 초기화
        binding = ActivityToDoBinding.inflate(layoutInflater)  // 액티비티 레이아웃
        todoRecyclerView = binding.todoRecyclerView  // 리사이클러뷰 companion object에 저장(3)
        todoDialog = TodoAddBinding.inflate(layoutInflater)  // 투두 추가 모달
        setContentView(binding.root)

        // 프래그먼트 연결 - 캘린더 프래그먼트
        calendar = CalendarFragment()
        viewFragment(calendar, R.id.todo_calendar)

        // 프래그먼트 연결 - 가족 프래그먼트
        familyFragment = FamilyFragment()
        viewFragment(familyFragment, R.id.fragment_family_todo)

        // 리사이클러뷰 설정 및 초기화
        val layoutManager = LinearLayoutManager(this)
        todoRecyclerView.layoutManager = layoutManager
        adapter = TodoAdapter(datas)  // 초기값 데이터 저장(2)
        todoRecyclerView.adapter = adapter  // 초기값 설정(3)
        todoToday()  // 오늘 날짜 투두리스트 요청(초기화)

        // 새 항목 추가 버튼 - 대화상자 열림
        alert = AlertDialog.Builder(this)
            .setTitle("TODO 추가")
            .setView(todoDialog.root)
            .setPositiveButton("저장", save)
            .setNegativeButton("취소", save)
            .create()

        // 투두리스트 항목 추가 버튼 리스너
        binding.todoAdd.setOnClickListener(addListener)

        // 탭바 연결
        setTabBar()
    }

    // 모달창에서 저장/취소 버튼 눌렀을 때 발생하는 이벤트
    val save = object : DialogInterface.OnClickListener {
        override fun onClick(p0: DialogInterface?, p1: Int) {
            if (p1 == DialogInterface.BUTTON_POSITIVE) { // [저장] 버튼을 눌렀을 경우

                val todoroleindex = todoDialog.todorole.selectedItemPosition
                val todorefindex = todoDialog.todoref.selectedItemPosition
                /* 화면 업데이트 */
                // 새 입력값을 datas(리사이클러뷰 데이터 배열)(1)에 추가
                datas?.add(
                    TodoItem(
                        todowork = todoDialog.todowork.text.toString(),
                        todorole = Todorole(nameArray[todoroleindex]),
                        todotime = todoDialog.todotime.text.toString().toInt(),
                        todoref = todorefindex
                    )
                )
                // 리사이클러뷰 업데이트
                adapter.notifyItemInserted(adapter.itemCount)

                /* DB에 추가 */
                // 서버 요청 registerTodo
                val call: Call<RegisterTodoRes> = MyApplication.networkServiceTodo.registerTodo(
                    RegisterTodoReq(
                        familyID = SaveSharedPreference.getFamliyID(this@TodoActivity)!!,
                        date = dateString,
                        todowork = todoDialog.todowork.text.toString(),
                        todorole = userIdArray[todoroleindex],
                        todotime = todoDialog.todotime.text.toString().toInt(),
                        todoref = todorefindex
                    )
                )
                call?.enqueue(object : Callback<RegisterTodoRes> {
                    override fun onResponse(call: Call<RegisterTodoRes>, response: Response<RegisterTodoRes>) {
                        if(response.isSuccessful){
                            Log.d("mobileApp", "registerTodo $response ${response.body()}")
                        }
                    }
                    override fun onFailure(call: Call<RegisterTodoRes>, t: Throwable) {
                        Log.d("mobileApp", "registerTodo onFailure $t")
                        Toast.makeText(this@TodoActivity, "등록 실패", Toast.LENGTH_SHORT).show()
                    }
                })

                // 입력 폼(다이얼로그) 초기화 - null이 제출되면 오류남, 처리 필요
                todoDialog.todowork.setText("")
                todoDialog.todorole.setSelection(0)
                todoDialog.todotime.setText("")
                todoDialog.todoref.setSelection(0)

            } else if (p1 == DialogInterface.BUTTON_NEGATIVE) {

            }
        }
    }

    // 투두리스트 항목 추가 버튼 리스너
    val addListener = object: View.OnClickListener {
        override fun onClick(it: View?) {
            // todorole: 가족 목록을 드롭다운 spinner와 연결
            family = familyFragment.familyArray
            nameArray = family.map { it.name }
            userIdArray = family.map { it._id }
            val spinnerAdapter = ArrayAdapter(this@TodoActivity, android.R.layout.simple_spinner_item, nameArray)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            todoDialog.todorole.adapter = spinnerAdapter

            // todoref: 시뮬레이션 목록을 드롭다운 spinner와 연결
            val todorefAdapter = ArrayAdapter(this@TodoActivity, android.R.layout.simple_spinner_item, simArray)
            todorefAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            todoDialog.todoref.adapter = todorefAdapter

            // 다이얼로그 창 표시
            alert.show()
        }
    }

    // 탭바 연결
    private fun setTabBar() {
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

    // 오늘 날짜 투두리스트 요청 (초기화)
    @RequiresApi(Build.VERSION_CODES.O)
    private fun todoToday() {
        // 오늘 날짜에 해당하는 To-do 불러오기 (DB 요청)
        val formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val call: Call<GetTodoRes> = MyApplication.networkServiceTodo.getTodo(  // familyID, yyyy-mm-dd
            GetTodoReq(SaveSharedPreference.getFamliyID(this)!!, formatDate)
        )
        call?.enqueue(object : Callback<GetTodoRes> {
            override fun onResponse(call: Call<GetTodoRes>, response: Response<GetTodoRes>) {
                if(response.isSuccessful){
                    // 리사이클러뷰에 할 일 목록 표시
                    TodoActivity.updateTodoList((response.body()!!.todoInfo).toMutableList())  // Array<TodoItem>
                }
            }
            override fun onFailure(call: Call<GetTodoRes>, t: Throwable) {
                Log.d("mobileApp", "onFailure $t")
            }
        })
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
                    val intent = Intent(this@TodoActivity, MyPageActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);  // 액티비티 화면 전환 애니메이션 제거
                    finish()
                }
            }
        }

        val petEventHandler = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(p1 == DialogInterface.BUTTON_POSITIVE) {
                    val intent = Intent(this@TodoActivity, SimulationActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0);  // 액티비티 화면 전환 애니메이션 제거
                    finish()
                }
            }
        }

        var builder = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("가족 그룹에 가입되어 있지 않은 사용자")
            .setIcon(R.drawable.ic_main)
            .setMessage("가족 그룹에 먼저 가입해주세요.")
            .setPositiveButton("확인", eventHandler)
            .setCancelable(false)

        var builder_pet = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("아직 강아지를 데려오지 않았어요!")
            .setIcon(R.drawable.ic_main)
            .setMessage("시뮬레이션과 todo 리스트를 사용하려면 먼저 강아지를 입양해주세요.")
            .setPositiveButton("확인", petEventHandler)
            .setCancelable(false)

        if(SaveSharedPreference.getFamliyID(this)!! == ""){
            builder.show()
        } else { // 가족 그룹 o, 강아지 입양 x
            if(SaveSharedPreference.getPetID(this)!! == ""){
                builder_pet.show()
            }
        }
    }
}
