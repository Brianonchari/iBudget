package com.studycode.mymoneytracker.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studycode.mymoneytracker.db.models.MyDebts

@Dao
interface MyDebtsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDebt(debts: MyDebts)

    @Query("SELECT * FROM MyDebts")
    fun getMyDebts():LiveData<List<MyDebts>>

    @Query("SELECT SUM(amount) FROM MyDebts WHERE strftime('%Y', date('now')) ")
    fun totalMonthlyDebts():LiveData<Float>

    @Query("SELECT SUM(amount)  FROM MyDebts WHERE strftime('%Y', date('now'))")
    fun getTotalYearDebts(): LiveData<Float>

    @Delete
    suspend fun deleteDebt(myDebts: MyDebts)


}