package com.yoon.nodeproject2.ui.syllabus

import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.yoon.nodeproject2.R
import com.yoon.nodeproject2.base.BaseActivity
import com.yoon.nodeproject2.databinding.ActivitySyllabusBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SyllabusActivity : BaseActivity<ActivitySyllabusBinding>(R.layout.activity_syllabus) {

    override fun init() {
        val subjectNumber = intent.getStringExtra("subjectNumber")
        if (subjectNumber != null) {
            binding.apply {
                webView.apply {
                    settings.apply {
                        javaScriptEnabled = true
                        loadWithOverviewMode = true
                        builtInZoomControls = true
                        setSupportZoom(true)
                        displayZoomControls = false
                    }
                    webViewClient = WebViewClient()
                    webChromeClient = WebChromeClient()
                    loadUrl("https://kupis.konkuk.ac.kr/sugang/acd/cour/plan/CourLecturePlanInq.jsp?ltYy=2022&ltShtm=B01012&sbjtId=$subjectNumber")
                }
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }

}