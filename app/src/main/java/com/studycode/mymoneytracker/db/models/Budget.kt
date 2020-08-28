package com.studycode.mymoneytracker.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Budget(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    val category: String,
    var amount: Float?
) {
    constructor(
        category: String,
        amount: Float?

    ) : this(null, category, amount)
}