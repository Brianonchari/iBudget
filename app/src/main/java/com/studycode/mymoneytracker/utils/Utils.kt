package com.studycode.mymoneytracker.utils

import android.content.Context
import android.content.SharedPreferences
import com.studycode.mymoneytracker.R

object InjectorUtils {
    fun provideSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            context.getString(R.string.newly_install), Context.MODE_PRIVATE)
    }
}