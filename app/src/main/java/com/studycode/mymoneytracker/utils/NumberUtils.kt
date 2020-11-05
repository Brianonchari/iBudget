package com.studycode.mymoneytracker.utils

import java.text.NumberFormat
import java.util.*

object NumberUtils {
    @JvmStatic
    fun getFormattedAmount(amount: Float): String {
        return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(amount)
    }
}