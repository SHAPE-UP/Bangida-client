package com.example.shape_up_2022.simulation

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.shape_up_2022.databinding.ActivitySimWalkReviewAddBinding
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception

class SimWalkReviewAddActivity : AppCompatActivity() {
    private var addreview_distance: TextView? = null
    private var  addreview_speed: TextView? = null
    private var  addreview_time: TextView? = null


    lateinit var binding : ActivitySimWalkReviewAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySimWalkReviewAddBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 산책 측정 액티비티에서 전달
        var data1:String?
        data1 = intent.getStringExtra("Key1")
        addreview_distance?.text= data1


        var data2:String?
        data2 = intent.getStringExtra("Key2")
        addreview_speed?.text= data2

        var data3:String?
        data3 = intent.getStringExtra("Key3")
        addreview_time?.text= data3
        Log.d("mobile","${addreview_time}")

        /* 이전 액티비티에서 전달받은 이미지 처리 */
        val byteArray: ByteArray? = intent.getByteArrayExtra("mapCapture")
        if (byteArray != null) {
            var bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            binding.addreviewImage.setImageBitmap(bitmap)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                saveImageOnAboveAndroidQ(bitmap)
            } else {
                saveImageOnUnderAndroidQ(bitmap)
            }
        }







        // 리뷰 저장
        binding.addreviewSave.setOnClickListener {
            // 리뷰 DB에 넘기기

            // 산책 main 화면으로 이동
            val intent = Intent(this, SimWalkMainActivity::class.java)
            startActivity(intent)
        }

    }


    /* 이미지 저장용 함수 */

    //Android Q (Android 10, API 29 이상에서는 이 메서드를 통해서 이미지를 저장한다.)
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageOnAboveAndroidQ(bitmap: Bitmap) {
        val fileName = System.currentTimeMillis().toString() + ".png" // 파일이름 현재시간.png

        /*
        * ContentValues() 객체 생성.
        * ContentValues는 ContentResolver가 처리할 수 있는 값을 저장해둘 목적으로 사용된다.
        * */
        val contentValues = ContentValues()
        contentValues.apply {
            put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/ImageSave") // 경로 설정
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName) // 파일이름을 put해준다.
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.IS_PENDING, 1) // 현재 is_pending 상태임을 만들어준다.
            // 다른 곳에서 이 데이터를 요구하면 무시하라는 의미로, 해당 저장소를 독점할 수 있다.
        }

        // 이미지를 저장할 uri를 미리 설정해놓는다.
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        try {
            if(uri != null) {
                val image = contentResolver.openFileDescriptor(uri, "w", null)
                // write 모드로 file을 open한다.

                if(image != null) {
                    val fos = FileOutputStream(image.fileDescriptor)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                    //비트맵을 FileOutputStream를 통해 compress한다.
                    fos.close()

                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0) // 저장소 독점을 해제한다.
                    contentResolver.update(uri, contentValues, null, null)
                }
            }
        } catch(e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveImageOnUnderAndroidQ(bitmap: Bitmap) {
        val fileName = System.currentTimeMillis().toString() + ".png"
        val externalStorage = Environment.getExternalStorageDirectory().absolutePath
        val path = "$externalStorage/DCIM/imageSave"
        val dir = File(path)

        if(dir.exists().not()) {
            dir.mkdirs() // 폴더 없을경우 폴더 생성
        }

        try {
            val fileItem = File("$dir/$fileName")
            fileItem.createNewFile()
            //0KB 파일 생성.

            val fos = FileOutputStream(fileItem) // 파일 아웃풋 스트림

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            //파일 아웃풋 스트림 객체를 통해서 Bitmap 압축.

            fos.close() // 파일 아웃풋 스트림 객체 close

            sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fileItem)))
            // 브로드캐스트 수신자에게 파일 미디어 스캔 액션 요청. 그리고 데이터로 추가된 파일에 Uri를 넘겨준다.
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}