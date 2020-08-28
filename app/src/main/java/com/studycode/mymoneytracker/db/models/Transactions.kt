package com.studycode.mymoneytracker.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Transactions(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val transactionName: String,
    var trasactionAmount: Float,
    var date: String
) {
    constructor(
        transactionName: String,
        trasactionAmount: Float,
        date: String
    ) : this(null, transactionName, trasactionAmount,date)
}