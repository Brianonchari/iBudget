package com.studycode.mymoneytracker.db.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Transactions(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val transactionName: String,
    var trasactionAmount: Float,
    var transactionReceipt:Bitmap,
    var date: String
) {

    constructor(
        transactionName: String,
        trasactionAmount: Float,
        date: String,
        transactionReceipt: Bitmap
    ) : this(null, transactionName, trasactionAmount,transactionReceipt,date)
}