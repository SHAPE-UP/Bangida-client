package com.example.shape_up_2022

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.shape_up_2022.databinding.AccountJoinBinding


class JoinActivity : AppCompatActivity() {
    private val binding by lazy { AccountJoinBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root) // account_create에 대한 바인딩

        // JoinCreate fragment : 회원가입1 보이게

        replaceFragment(JoinCreate())
    }

    // fragment replace: 프래그먼트 내에서 이벤트 발생 시 다른 프래그먼트로 이동
    fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.join_fragment, fragment)
        transaction.commit()

    }

    // fragment에서 인텐트 호출을 통한 액티비티 이동
    fun gotoMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }

}


