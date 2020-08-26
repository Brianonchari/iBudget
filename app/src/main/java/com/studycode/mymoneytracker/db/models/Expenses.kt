package com.studycode.mymoneytracker.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Expenses(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val categoryId: Int?,
    val expenseName: String,
    val expense: Double,
    val image: String?,
    val dateTime: String
) {
    constructor(categoryId: Int?, expenseName: String, expense: Double, image: String?,dateTime: String) : this(
        null,
        categoryId,
        expenseName,
        expense,
        image,
        dateTime
    )


}