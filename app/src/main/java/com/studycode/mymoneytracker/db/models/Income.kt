package com.studycode.mymoneytracker.db.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class Income(
    val source:String?,
    val receiptImg:Bitmap? = null,
    val amount:Float?,
    var month: String,
    var year: Int
){
    @PrimaryKey(autoGenerate = true)
    var id :Int? = null
}