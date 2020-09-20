package com.studycode.mymoneytracker.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.utils.SharedPrefs

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIMEOUT = 3000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            if(SharedPrefs(this).getBoolean("LOGGEDIN",false)){
                clearAllGoToActivity(MainActivity::class.java)
            }else{
                clearAllGoToActivity(MainActivity::class.java)
//                OnboardingFragment().show(childFragmentManager, "foo")

            }
        },SPLASH_TIMEOUT)
    }
}

fun Context.clearAllGoToActivity(activity:Class<out Activity>){
    val intent= Intent(this,activity)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
}