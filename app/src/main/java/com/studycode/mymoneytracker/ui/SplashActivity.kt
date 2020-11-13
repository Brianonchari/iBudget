package com.studycode.mymoneytracker.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.onboard.OnBoardActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val splashTime = 3000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val isFirstRun = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
            .getBoolean("isFirstRun", true)
        Handler(Looper.getMainLooper()).postDelayed({
            if (isFirstRun) {
                startActivity(Intent(this@SplashActivity, OnBoardActivity::class.java))
                Toast.makeText(this@SplashActivity, "First Run", Toast.LENGTH_LONG)
                    .show()
            }else{
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
            getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply()
        }, splashTime)
    }
}