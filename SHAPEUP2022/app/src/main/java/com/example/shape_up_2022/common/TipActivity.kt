package com.example.shape_up_2022.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.shape_up_2022.R
import com.example.shape_up_2022.databinding.ActivitySimStartBinding

class TipActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tip)

        /*webview.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }

        webview.loadUrl("https://www.naver.co.kr/")*/
    }
/*
    override fun onBackPressed() {
        if (webview.canGoBack())
        {
            webview.goBack()
        }
        else
        {
            finish()
        }
    }*/
}