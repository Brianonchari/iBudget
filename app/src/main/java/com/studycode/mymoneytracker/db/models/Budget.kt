package com.studycode.mymoneytracker.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Budget(
    val category: String,
    var amount: Float,
    var amountSpent: Float,
    var balance: Float
): Serializable{
    @PrimaryKey(autoGenerate = true)
    var id: Int ? = null
}
