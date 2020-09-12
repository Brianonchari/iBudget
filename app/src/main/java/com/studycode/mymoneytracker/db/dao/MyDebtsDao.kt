package com.studycode.mymoneytracker.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.studycode.mymoneytracker.db.models.MyDebts

@Dao
interface MyDebtsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDebt(debts: MyDebts)

    @Query("SELECT * FROM MyDebts")
    fun getMyDebts():LiveData<List<MyDebts>>

    @Query("SELECT SUM(amount) FROM MyDebts ")
    fun totalDebts():LiveData<Float>
}