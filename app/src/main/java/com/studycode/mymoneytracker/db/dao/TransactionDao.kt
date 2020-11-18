package com.studycode.mymoneytracker.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studycode.mymoneytracker.db.models.Transactions

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveTransaction(transactions: Transactions)

    @Delete
    suspend fun deleteTransaction(transactions: Transactions)
    @Query("SELECT * FROM Transactions")
    fun getAllTransactions(): LiveData<List<Transactions>>

    @Query("SELECT SUM(trasactionAmount) FROM Transactions WHERE strftime('%m', date('now')) ")
    fun totalMonthlyTransactions():LiveData<Float>

    @Query("SELECT SUM(trasactionAmount) FROM Transactions WHERE strftime('%Y', date('now')) ")
    fun totalYearTransactions():LiveData<Float>

}