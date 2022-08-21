package com.example.shape_up_2022.common

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.shape_up_2022.achieve.AchieveActivity
import com.example.shape_up_2022.databinding.ActivityMyPageBinding
import com.example.shape_up_2022.retrofit.LogoutRes
import com.example.shape_up_2022.retrofit.MyApplication
import com.example.shape_up_2022.todo.TodoActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MyPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("mobileApp", "${SaveSharedPreference.getUserName(this)}")
        binding.mypageUsername.text = SaveSharedPreference.getUserName(this)
        binding.mypageUseremail.text = SaveSharedPreference.getUserEmail(this)

        // 프로필 사진 변경
        val requestGalleryLauncher=registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            try{
                val calRatio=calculateInSampleSize(
                    it.data!!.data!!, 150, 150
                )
                val option = BitmapFactory.Options()
                //option.inSampleSize=4
                var inputStream=contentResolver.openInputStream(it.data!!.data!!)
                val bitmap= BitmapFactory.decodeStream(inputStream, null, option)
                inputStream!!.close()
                inputStream=null

                bitmap?.let {
                    binding.userIdImg.setImageBitmap(bitmap)
                }?: let{
                    Log.d("mobileApp", "bitmap null")
                }
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }

        // 프로필 사진 변경
        binding.changeprofile.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type="image/*"
            requestGalleryLauncher.launch(intent)
        }

        // 로그아웃
        val email = SaveSharedPreference.getUserEmail(this)!!.toString()
        Log.d("mobileApp", "${email}")
        binding.btnLogout.setOnClickListener {
            val call: Call<LogoutRes> = MyApplication.networkServiceAuth.logout(
            )

            call?.enqueue(object : Callback<LogoutRes> {
                override fun onResponse(call: Call<LogoutRes>, response: Response<LogoutRes>) {
                    if(!response.isSuccessful){
                        Log.d("mobileApp", "${response}")
                    }
                    if(response.isSuccessful){
                        Log.d("mobileApp", "$response ${response.body()}")

                        // 저장했던 preference clear
                        SaveSharedPreference.clearUserEmail(baseContext)
                        SaveSharedPreference.clearUserName(baseContext)
                        SaveSharedPreference.clearUserTested(baseContext)
                        SaveSharedPreference.clearUserID(baseContext)

                        // StartActivity로 이동
                        val intent = Intent(baseContext, TempMainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<LogoutRes>, t: Throwable) {
                    Log.d("mobileApp", "onFailure $t")
                    Toast.makeText(baseContext, "로그아웃에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            })
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

    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = contentResolver.openInputStream(fileUri)

            //inJustDecodeBounds 값을 true 로 설정한 상태에서 decodeXXX() 를 호출.
            //로딩 하고자 하는 이미지의 각종 정보가 options 에 설정 된다.
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //비율 계산........................
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        //inSampleSize 비율 계산
        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    override fun onStart() {
        super.onStart()

        if(SaveSharedPreference.getFamliyID(this)!! == ""){ // familyID == ""
            binding.noFamFamilyBtns.visibility = View.VISIBLE
            binding.mypageFamilyList.visibility = View.GONE
        } else{
            binding.noFamFamilyBtns.visibility = View.GONE
            binding.mypageFamilyList.visibility = View.VISIBLE
        }
    }
}