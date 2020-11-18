package com.studycode.mymoneytracker.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class MyDebts(
    val payee: String,
    var amount: Float,
    val dueDate: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
) : Serializable
