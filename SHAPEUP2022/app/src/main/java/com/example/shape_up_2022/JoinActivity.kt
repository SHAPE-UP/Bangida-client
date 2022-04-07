package com.example.shape_upapptest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.shape_upapptest.databinding.AccountJoinBinding
import kotlinx.android.synthetic.main.account_join.*

class JoinActivity : AppCompatActivity() {
    private val binding by lazy {AccountJoinBinding.inflate(layoutInflater)}
    private val fragment1 by lazy{JoinCreate() };
    private val fragment2 by lazy{ JoinShare()};
    // 프레그먼트 변수 선언


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root) // account_create에 대한 바인딩

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        // 프레그먼트 화면 보이게 구현하기
        val fragment1 = JoinCreate()
        Log.d("app_test","$fragment1")
        transaction.replace(R.id.join_fragment, fragment1)
        transaction.commit()

        // JoinCreate Fragment: "다음으로" 버튼 이벤트
        //val fragment2 = JoinShare()
        // 프레그먼트의 onClick 이벤트가 발생하면 다음 프레그먼트로 전환
        //fragment1.ButtonInFragment?.setOnClickListener {
        //    transaction.replace(R.id.join_fragment, fragment2)
        //    transaction.commit()
        //}

        /*fragment2.ButtonInFragment?.setOnClickListener {
            // 입력창에 정보를 입력하지 않았을 때
            Log.d("app_test", "프레그먼트 적용")

            // 메인 액티비티로 이동
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()

            // DB에 데이터 저장
                // if inputSharNum == null 일때 처리를 따로 해야하나?
                // else { 데이터 인풋하기 }
        }*/

    }
    // 프레그먼트 replace
    fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        Log.d("app_test", "replaceFragment $fragment")
        transaction.replace(R.id.join_fragment, fragment)
        Log.d("app_test", "replaceFragment_replace $fragment")
        transaction.commit()

    }

    fun fragment(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }

}


