package com.studycode.mymoneytracker.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class Income(
    @PrimaryKey(autoGenerate = true)
    var id :Int? = null,
    val source:String?,
    val amount:Float?,
    var month: String,
    var year: Int
){
    constructor(
        source: String?,
        amount: Float?,
        month: String?,
        year: Int?
    ):this(null,
        source,
        amount,
        Calendar.getInstance().get(Calendar.MONTH).toString(),
        Calendar.getInstance().get(Calendar.YEAR))
}