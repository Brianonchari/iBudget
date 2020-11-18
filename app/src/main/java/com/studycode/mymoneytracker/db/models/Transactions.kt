package com.studycode.mymoneytracker.db.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transactions(
    val transactionName: String,
    var trasactionAmount: Float,
    var transactedCategory:String,
    var transactionReceipt:Bitmap,
    var date: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
)