package com.example.shape_up_2022.simulation

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.shape_up_2022.R
import java.util.*
import android.os.Environment

class SimVoiveActivity : AppCompatActivity() {
    private var output: String? = null

    private val soundVisualizerView: SoundVisualizerView by lazy {
        findViewById(R.id.soundVisualizerView)
    }

    private val recordTimeTextView: CountUpView by lazy {
        findViewById(R.id.recordTimeTextView)
    }

    private val resetButton: Button by lazy {
        findViewById(R.id.resetButton)
    }

    private val recordButton: RecordButton by lazy {
        findViewById(R.id.recordButton)
    }

    private val requiredPermissions = arrayOf(
        android.Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    private val recordingFilePath: String by lazy {
        val fileName: String = Date().getTime().toString() + ".wav"
        Environment.getExternalStorageDirectory().absolutePath + "/Download/" + fileName //내장메모리 밑에 위치

        //"${externalCacheDir?.absolutePath}/recording.3gp"
    }
    private var state = State.BEFORE_RECORDING
        set(value) { // setter 설정
            field = value // 실제 프로퍼티에 대입
            resetButton.isEnabled = (value == State.AFTER_RECORDING || value == State.ON_PLAYING)
            recordButton.updateIconWithState(value)
        }

    private var recorder: MediaRecorder? = null // 사용 하지 않을 때는 메모리해제 및  null 처리
    private var player: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sim_voive)

        requestAudioPermission()
        initViews()
        bindViews()
        initVariables()
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // 요청한 권한에 대한 결과

        val audioRecordPermissionGranted =
            requestCode == REQUEST_RECORD_AUDIO_PERMISSION &&
                    grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED

        if (!audioRecordPermissionGranted) {
            finish() // 거절 할 경우 앱 종료
        }
    }

    private fun requestAudioPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(requiredPermissions, REQUEST_RECORD_AUDIO_PERMISSION)
        }
    }

    private fun initViews() {
        recordButton.updateIconWithState(state)
    }

    private fun bindViews() {

        soundVisualizerView.onRequestCurrentAmplitude = {
            recorder?.maxAmplitude ?: 0
        }


        recordButton.setOnClickListener {
            when (state) {
                State.BEFORE_RECORDING -> {
                    startRecoding()
                }
                State.ON_RECORDING -> {
                    stopRecording()
                }
                State.AFTER_RECORDING -> {
                    startPlaying()
                }
                State.ON_PLAYING -> {
                    stopPlaying()
                }
            }
        }

        resetButton.setOnClickListener {
            stopPlaying()
            // clear
            soundVisualizerView.clearVisualization()
            recordTimeTextView.clearCountTime()
            state = State.BEFORE_RECORDING
        }
    }

    private fun initVariables() {
        state = State.BEFORE_RECORDING
    }

    private fun startRecoding() {
        // 녹음 시작 시 초기화
        recorder = MediaRecorder()
            .apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP) // 포멧
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // 엔코더
                setOutputFile(recordingFilePath)
                //setOutputFile(output)
                prepare()
            }
        recorder?.start()
        recordTimeTextView.startCountup()
        soundVisualizerView.startVisualizing(false)
        state = State.ON_RECORDING
    }

    private fun stopRecording() {
        recorder?.run {
            stop()
            release()
        }
        recorder = null
        soundVisualizerView.stopVisualizing()
        recordTimeTextView.stopCountup()
        state = State.AFTER_RECORDING
    }


    private fun startPlaying() {
        // MediaPlayer
        player = MediaPlayer()
            .apply {
                setDataSource(recordingFilePath)
                prepare() // 재생 할 수 있는 상태 (큰 파일 또는 네트워크로 가져올 때는 prepareAsync() )
            }

        // 전부 재생 했을 때
        player?.setOnCompletionListener {
            stopPlaying()
            state = State.AFTER_RECORDING
        }

        player?.start() // 재생
        recordTimeTextView.startCountup()

        soundVisualizerView.startVisualizing(true)

        state = State.ON_PLAYING
    }

    private fun stopPlaying() {
        player?.release()
        player = null
        soundVisualizerView.stopVisualizing()
        recordTimeTextView.stopCountup()

        state = State.AFTER_RECORDING
    }

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 201
    }
}