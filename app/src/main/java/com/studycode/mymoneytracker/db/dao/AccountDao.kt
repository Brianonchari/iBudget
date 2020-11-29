package com.studycode.mymoneytracker.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studycode.mymoneytracker.db.models.Account

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoAcc(account: Account)

    @Query("SELECT SUM(amount) FROM Account")
    fun getTotalBalance(): LiveData<Float>

    @Query("SELECT SUM(amount) FROM Account WHERE accountName='Bank'")
    fun getBankAccbalance(): LiveData<Float>

    @Query("SELECT SUM(amount) FROM Account WHERE accountName='Cash'")
    fun getCashAccbalance(): LiveData<Float>

    @Query("SELECT * FROM Account")
    fun getAllCounts(): LiveData<List<Account>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAccount(account: Account)

    @Query("UPDATE Account SET accountName = :accountName , amount = :amt WHERE accountName = 'Cash'")
    suspend fun updateCashAccount(accountName: String, amt: Float)

    @Query("UPDATE Account SET accountName = :accountName , amount = :amt WHERE accountName = 'Bank' ")
    suspend fun updateBankAccount(accountName: String, amt: Float)
}