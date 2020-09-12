package com.studycode.mymoneytracker.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class MyDebts(
    val payee: String,
    var amount: Float,
    val dueDate: String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}