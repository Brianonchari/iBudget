package com.studycode.mymoneytracker.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.studycode.mymoneytracker.db.models.Income
import java.time.Month

@Dao
interface IncomeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIncome(income:Income)

    @Query("SELECT * FROM Income")
    fun getAllIncome():LiveData<List<Income>>

    @Query("SELECT SUM(amount) FROM  Income WHERE MONTH=:month")
    fun getTotalMonthlyIncome(month: Int):Double



}