package com.studycode.mymoneytracker.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Budget(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    val category: String,
    var amount:Double?
) {
    constructor(
        category: String,
        amount: Double?
    ) : this(null, category,amount)
}