package com.studycode.mymoneytracker.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.studycode.mymoneytracker.db.models.Transactions

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveTransaction(transactions: Transactions)

    @Query("SELECT * FROM Transactions")
    fun getAllTransactions(): LiveData<List<Transactions>>


}