package com.studycode.mymoneytracker.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studycode.mymoneytracker.db.models.Income
import java.time.Month

@Dao
interface IncomeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIncome(income: Income)
    @Query("SELECT * FROM Income")
    fun getAllIncome(): LiveData<List<Income>>
    @Query("SELECT  SUM(amount) FROM Income where strftime('%m', date('now'))")
    fun getTotalMonthlyIncome(): LiveData<Float>
    @Query("SELECT SUM(amount)  FROM Income WHERE strftime('%Y', date('now'))")
    fun getTotalYearIncome(): LiveData<Float>
    @Delete
    suspend fun deleteIncome(income: Income)

}