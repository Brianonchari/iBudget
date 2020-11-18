package com.studycode.mymoneytracker.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.studycode.mymoneytracker.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_terms.*

@AndroidEntryPoint
class TermsFragment :Fragment(R.layout.fragment_terms){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        terms_webview.apply {
            webViewClient = WebViewClient()
            terms_webview.loadUrl("file:///android_asset/privacy_policy.html")
        }
    }
}