package com.studycode.mymoneytracker.db.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Income(
    val source: String?,
    val amount: Float?,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
)
