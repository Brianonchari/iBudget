package com.studycode.mymoneytracker.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Income(
    @PrimaryKey(autoGenerate = true)
    var id :Int? = null,
    val source:String,
    val amount:Double,
    var month: Int,
    var year: Int
){
    constructor(
        source: String,
        amount: Double,
        month: Int,
        year: Int
    ):this(null, source, amount, month, year)
}