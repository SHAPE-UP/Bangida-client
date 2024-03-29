package com.example.shape_up_2022.common

import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.shape_up_2022.achieve.AchieveActivity
import com.example.shape_up_2022.databinding.ActivityMyPageBinding
import com.example.shape_up_2022.databinding.MypageDialogJoinFamilyBinding
import com.example.shape_up_2022.retrofit.*
import com.example.shape_up_2022.todo.TodoActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
        binding.btnLogout.setOnClickListener {
            // 저장했던 preference clear
            SaveSharedPreference.clearAll(baseContext)

            Toast.makeText(baseContext, "로그아웃 성공", Toast.LENGTH_SHORT)

            // SplashActivity로 이동
            val intent = Intent(baseContext, SplashActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK) // 스택에 쌓인 액티비티를 모두 지우기
            startActivity(intent)
        }

        val eventhandler_save = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(p1== DialogInterface.BUTTON_POSITIVE) {
                    // retorfit 요청
                        Log.d("mobileApp", "${SaveSharedPreference.getUserID(baseContext)}")
                    val reqUserID = SaveSharedPreference.getUserID(baseContext)!!
                    val call: Call<AddFamilyRes> = MyApplication.networkServiceUsers.addFamliy(
                        AddFamilyReq(reqUserID)
                    )

                    call?.enqueue(object : Callback<AddFamilyRes> {
                        override fun onResponse(call: Call<AddFamilyRes>, response: Response<AddFamilyRes>) {
                            if(response.isSuccessful){
                                Log.d("mobileApp", "$response ${response.body()}")
                                // 프리퍼런스에 값 저장
                                SaveSharedPreference.setFamliyID(baseContext, response.body()!!.familyID)

                                // UI 변경하기
                                binding.noFamFamilyBtns.visibility = View.GONE
                                binding.mypageFamilyList.visibility = View.VISIBLE

                                // 토스트 출력하기
                                Toast.makeText(baseContext, "그룹 생성에 성공하셨습니다!", Toast.LENGTH_SHORT).show()

                            }
                       }

                        override fun onFailure(call: Call<AddFamilyRes>, t: Throwable) {
                            Log.d("mobileApp", "onFailure $t")
                            Toast.makeText(baseContext, "그룹 생성에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
        val dialogBinding = MypageDialogJoinFamilyBinding.inflate(layoutInflater)
        val eventhandler_join = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(p1== DialogInterface.BUTTON_POSITIVE) {
                    // retorfit 요청
                    val call: Call<JoinFamilyRes> = MyApplication.networkServiceUsers.joinFamliy(
                        JoinFamilyReq(dialogBinding.dialogInputFamilycode.text.toString(), SaveSharedPreference.getUserEmail(baseContext)!!)
                    )
                    // retrofit 요청
                    call?.enqueue(object : Callback<JoinFamilyRes> {
                        override fun onResponse(call: Call<JoinFamilyRes>, response: Response<JoinFamilyRes>) {
                            if(response.isSuccessful){
                                Log.d("mobileApp", "$response ${response.body()}")
                                // 프리퍼런스에 값 저장
                                SaveSharedPreference.setFamliyID(baseContext, response.body()!!.familyID)

                                // UI 변경하기
                                binding.noFamFamilyBtns.visibility = View.GONE
                                binding.mypageFamilyList.visibility = View.VISIBLE

                                // 토스트 출력하기
                                Toast.makeText(baseContext, "가족 그룹에 참여했어요!", Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onFailure(call: Call<JoinFamilyRes>, t: Throwable) {
                            Log.d("mobileApp", "onFailure $t")
                            Toast.makeText(baseContext, "그룹 참여에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }


        var builder_save = AlertDialog.Builder(this)
            .setTitle("가족 그룹 만들기")
            .setMessage("가족 그룹을 생성하시겠습니까?")
            .setPositiveButton("네!", eventhandler_save)
            .setNeutralButton("아니오", null)
            .setCancelable(true)

        var builder_join = AlertDialog.Builder(this)
            .setTitle("가족 그룹 가입")
            .setView(dialogBinding.root)
            .setPositiveButton("가입하기", eventhandler_join)
            .setCancelable(true)

        // 가족 그룹 생성
        binding.mypageSavefam.setOnClickListener {
            builder_save.show()
        }

        // 가족 그룹 참여
        binding.mypageJoinfam.setOnClickListener {
            if (dialogBinding.root.parent != null) (dialogBinding.root.parent as ViewGroup).removeView(
                dialogBinding.root
            )
            builder_join.setView(dialogBinding.root)
            builder_join.show()
        }

        // 탭바 연결
        setTabBar()
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