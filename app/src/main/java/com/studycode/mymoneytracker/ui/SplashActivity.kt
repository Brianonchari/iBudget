package com.studycode.mymoneytracker.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.utils.InjectorUtils
import com.studycode.mymoneytracker.utils.OnBoardingManager

class SplashActivity : AppCompatActivity() {
    private val splashTime = 3000L
    private val sharedPreferences by lazy { InjectorUtils.provideSharedPreference(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val boardingManager = OnBoardingManager(sharedPreferences)

        Handler(Looper.getMainLooper()).postDelayed({
            if (boardingManager.isFirstLaunch()) {
                boardingManager.setAppsFirstLaunch(false)
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            } else {
            startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }, splashTime)
    }
}