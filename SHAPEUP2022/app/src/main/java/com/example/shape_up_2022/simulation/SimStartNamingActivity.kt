package com.example.shape_up_2022.simulation

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.shape_up_2022.achieve.AchieveActivity
import com.example.shape_up_2022.common.SaveSharedPreference
import com.example.shape_up_2022.databinding.ActivitySimStartNamingBinding
import com.example.shape_up_2022.retrofit.*
import com.example.shape_up_2022.todo.TodoActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SimStartNamingActivity : AppCompatActivity() {

    lateinit var binding : ActivitySimStartNamingBinding
    var petName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimStartNamingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nameStep1.visibility = View.VISIBLE
        binding.nameStep2.visibility = View.GONE
        binding.nameStep3.visibility = View.GONE
        binding.nameStep4.visibility = View.GONE

        binding.nameBtn1.setOnClickListener {
            binding.nameStep1.visibility = View.GONE
            binding.nameStep2.visibility = View.VISIBLE
            binding.nameStep3.visibility = View.GONE
            binding.nameStep4.visibility = View.GONE
        }

        binding.nameBtn2.setOnClickListener {
            binding.nameStep1.visibility = View.GONE
            binding.nameStep2.visibility = View.GONE
            binding.nameStep3.visibility = View.VISIBLE
            binding.nameStep4.visibility = View.GONE
        }

        binding.btnRegisterInfoLink.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.animal.go.kr/front/community/show.do;jsessionid=Lg5YgpnbEoP5liudNsFMSicezdue41UcTvIrz0zd46ptK48F7PERl09jE3i0g3a8.aniwas2_servlet_front?boardId=contents&seq=66&menuNo=2000000016"))
            startActivity(browserIntent)
        }

        binding.nameBtn4.setOnClickListener {
            // To-do 페이지로 이동
            val intent = Intent(this, TodoActivity::class.java)
            startActivity(intent)
            finish()
        }

        /* 강아지 입양 및 등록 */


        // 사용자에게 강아지 이름 확인: alert 창
        val eventHandler = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(p1== DialogInterface.BUTTON_POSITIVE) {
                    // 강아지 입양 및 이름 등록
                    postRegisterPet(petName)

                    // 강아지 업적 달성 position: 0
                    val check = SaveSharedPreference.getAchieve(baseContext)!![0] // 업적 달성 여부 확인

                    if(!check){ // 업적을 1번도 달성하지 않았었다면
                        AchieveActivity().clearAchieve(this@SimStartNamingActivity, 0) // 업적 달성 업데이트 실행
                    }

                    binding.nameStep1.visibility = View.GONE
                    binding.nameStep2.visibility = View.GONE
                    binding.nameStep3.visibility = View.GONE
                    binding.nameStep4.visibility = View.VISIBLE

                }
            }
        }


        binding.btnNamingFinish.setOnClickListener {
            petName = binding.inputPetName.text.toString()
            Log.d("mobileApp", petName)

            var builder = AlertDialog.Builder(this)
                .setTitle("강아지 이름 결정")
                .setMessage("반려견의 이름을 ${petName}(으)로 지을까요?\n※ 강아지의 이름은 변경할 수 없으니 신중히 결정해주세요.")
                .setPositiveButton("네!", eventHandler)
                .setNegativeButton("아니요", null)
                .setCancelable(false)

            builder.show()
        }
    }

    // call
    private fun postRegisterPet(petName: String){
        val familyID = SaveSharedPreference.getFamliyID(this)!!

        // DB에 저장된 아이디 값
        val call: Call<AdditionPetIdRes> = MyApplication.networkServiceFamily.additionPetId(
            AdditionPetIdReq(familyID, petName)
        )

        call?.enqueue(object : Callback<AdditionPetIdRes> {
            override fun onResponse(call: Call<AdditionPetIdRes>, response: Response<AdditionPetIdRes>) {
                if(response.isSuccessful){
                    Log.d("mobileApp", "$response ${response.body()}")
                    if(response.body()!!.success){
                        Toast.makeText(baseContext, "반가워, ${petName}!", Toast.LENGTH_SHORT).show()
                        
                        // Preference에 petID 저장
                        Log.d("mobileApp", "${SaveSharedPreference.getFamliyID(baseContext)!!}")
                        callGetPetID(SaveSharedPreference.getFamliyID(baseContext)!!)
                    }
                    else Toast.makeText(baseContext, "강아지 이름 정보 저장 오류", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AdditionPetIdRes>, t: Throwable) {
                Log.d("mobileApp", "onFailure $t")
                Toast.makeText(baseContext, "네트워크 오류 발생! 강아지를 입양하지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun callGetPetID(familyID: String){
        // DB에 저장된 아이디 값
        val call: Call<GetPetIDRes> = MyApplication.networkServiceFamily.getPetID(
            familyID = familyID
        )

        call?.enqueue(object : Callback<GetPetIDRes> {
            override fun onResponse(call: Call<GetPetIDRes>, response: Response<GetPetIDRes>) {
                if(response.isSuccessful){
                    Log.d("mobileApp", "$response ${response.body()}")
                    Log.d("mobileApp", "$response ${response.body()!!.petID}")
                    // Preference에 PetID 저장
                    SaveSharedPreference.setPetID(baseContext, response.body()!!.petID)
                }
            }

            override fun onFailure(call: Call<GetPetIDRes>, t: Throwable) {
                Log.d("mobileApp", "onFailure $t")
                Toast.makeText(baseContext, "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
            }
        })
    }
}