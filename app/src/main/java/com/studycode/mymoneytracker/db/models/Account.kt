package com.studycode.mymoneytracker.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(
    val accountName:String?,
    val amount:Float?,
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
)