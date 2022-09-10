package com.example.shape_up_2022.simulation

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.shape_up_2022.R
import com.example.shape_up_2022.databinding.ActivitySimBeutySelfBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class SimBeutySelfActivity : YouTubeBaseActivity() {
    lateinit var binding: ActivitySimBeutySelfBinding
    val videoIdA = "7Ax2wDCyJIE"
    val viedoIdC = "Of8VH6sMnLY"
    val viedoIdB = "17avshn9HQ0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimBeutySelfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_sim_beuty_self)

        // 튜토리얼
        binding.tutoSimBeauty.setOnClickListener {
            binding.tutoSimBeauty.visibility = View.GONE
        }

        binding.simEx.setOnClickListener{
            AlertDialog.Builder(this).run {
                setTitle("위생 미용 설명서")
                //setIcon(R.drawable.puppy)
                setMessage("미용된 강아지를 누르면 셀프미용 영상이 보입니다.")
                var view_listener = object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        when (which) {
                            DialogInterface.BUTTON_POSITIVE ->
                                viewmovie()
                        }
                    }
                }
                setPositiveButton("확인",view_listener )
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(false) // 메시지 값 출력
            true

        }

        val btnA : Button = findViewById<Button>(R.id.btnA)
        btnA.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("위생 미용 가이드")
                //setIcon(R.drawable.puppy)
                setMessage("체온조절 및 진드기 방지를 위해 미용이 필요합니다. 배꼽부터 넓적다리 안쪽까지 미용합니다.")
                //영상 넣고 싶어
                var dialog_listenerA = object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        when (which) {
                            DialogInterface.BUTTON_POSITIVE ->
                                toast_a()
                        }
                    }
                }
                setPositiveButton("확인", dialog_listenerA)
                //setPositiveButton("확인", null)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(false) // 메시지 값 출력
            true
        }

        val btnB : Button = findViewById<Button>(R.id.btnB)
        btnB.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("위생 미용 가이드")
                //setIcon(R.drawable.puppy)
                setMessage("배변을 본 뒤 배설물이 컬에 묻는 것을 방지하기 위해 항문 주변 털을 정리합니다.")
                var dialog_listenerB = object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        when (which) {
                            DialogInterface.BUTTON_POSITIVE ->
                                toast_b()
                        }
                    }
                }
                setPositiveButton("확인",dialog_listenerB )
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(false) // 메시지 값 출력
            true
        }

        val btnC : Button = findViewById<Button>(R.id.btnC)
        btnC.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("위생 미용 가이드")
                var dialog_listenerC = object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        when (which) {
                            DialogInterface.BUTTON_POSITIVE ->
                                toast_c()
                        }
                    }
                }
                setMessage("발바탁 털을 미용하지 않으면 밟을 때 미끄러지기 쉬워 관절에 무리를 주고 골절을 유발합니다.")
                setPositiveButton("확인", dialog_listenerC)
                setCancelable(false)
                show()
            }.setCanceledOnTouchOutside(false) // 메시지 값 출력
            true
        }

    }
    //배열 사용해서 코드 단순화 작업 필요..-> item배열 , 리스너 함수 재사용
    private fun toast_a() {
        Toast.makeText(this, "셀프 미용 영상 시청", Toast.LENGTH_SHORT).show()
        val youtubeView = findViewById<YouTubePlayerView>(R.id.youtubeView)

        youtubeView.initialize("develop", object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider,
                player: YouTubePlayer,
                wasRestored: Boolean
            ) {
                if (!wasRestored) {
                    player.cueVideo(videoIdA)
                }
                player.setPlayerStateChangeListener(object :
                    YouTubePlayer.PlayerStateChangeListener {
                    override fun onAdStarted() {}
                    override fun onLoading() {}
                    override fun onVideoStarted() {}
                    override fun onVideoEnded() {}
                    override fun onError(p0: YouTubePlayer.ErrorReason) {}
                    override fun onLoaded(videoId: String) {
                        player.play()
                    }
                })

            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider?,
                result: YouTubeInitializationResult?
            ) {

            }
        })
    }

    private fun toast_b() {
        Toast.makeText(this, "셀프 미용 영상 시청", Toast.LENGTH_SHORT).show()
        val youtubeView = findViewById<YouTubePlayerView>(R.id.youtubeView)

        youtubeView.initialize("develop", object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider,
                player: YouTubePlayer,
                wasRestored: Boolean
            ) {
                if (!wasRestored) {
                    player.cueVideo(viedoIdB)
                }
                player.setPlayerStateChangeListener(object :
                    YouTubePlayer.PlayerStateChangeListener {
                    override fun onAdStarted() {}
                    override fun onLoading() {}
                    override fun onVideoStarted() {}
                    override fun onVideoEnded() {}
                    override fun onError(p0: YouTubePlayer.ErrorReason) {}
                    override fun onLoaded(videoId: String) {
                        player.play()
                    }
                })

            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider?,
                result: YouTubeInitializationResult?
            ) {

            }
        })
    }
    private fun toast_c() {
        Toast.makeText(this, "셀프 미용 영상 시청", Toast.LENGTH_SHORT).show()
        val youtubeView = findViewById<YouTubePlayerView>(R.id.youtubeView)

        youtubeView.initialize("develop", object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider,
                player: YouTubePlayer,
                wasRestored: Boolean
            ) {
                if (!wasRestored) {
                    player.cueVideo(viedoIdC)
                }
                player.setPlayerStateChangeListener(object :
                    YouTubePlayer.PlayerStateChangeListener {
                    override fun onAdStarted() {}
                    override fun onLoading() {}
                    override fun onVideoStarted() {}
                    override fun onVideoEnded() {}
                    override fun onError(p0: YouTubePlayer.ErrorReason) {}
                    override fun onLoaded(videoId: String) {
                        player.play()
                    }
                })

            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider?,
                result: YouTubeInitializationResult?
            ) {

            }
        })
    }

    private fun viewmovie(){
        binding.youtubeView.setEnabled(true)
        binding.youtubeView.setVisibility(View.VISIBLE)

    }


}
