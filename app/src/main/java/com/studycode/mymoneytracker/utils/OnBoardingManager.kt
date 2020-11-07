package com.studycode.mymoneytracker.utils

import android.content.SharedPreferences
import androidx.core.content.edit

class OnBoardingManager(private val sharedPref: SharedPreferences) {

    fun setAppsFirstLaunch(isFirstLaunch: Boolean) {
        sharedPref.edit(commit = true) { putBoolean(APP_FIRST_LAUNCH, isFirstLaunch) }
    }

    fun isFirstLaunch(): Boolean {
        return sharedPref.getBoolean(APP_FIRST_LAUNCH, true)
    }
}

private const val APP_FIRST_LAUNCH : String = "AppsFirstRun"