package com.studycode.mymoneytracker.db.dao

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.room.*
import com.studycode.mymoneytracker.db.models.Budget


@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: Budget)

    @Query("SELECT * FROM Budget")
    fun getBudgetList(): LiveData<List<Budget>>

    @Query("SELECT  SUM(amount) FROM Budget where strftime('%m', date('now'))")
    fun totalMonthlyBudget(): LiveData<Float>

    @Delete
    fun deleteBudget(budget: Budget): Int

    @Query("DELETE from budget")
    fun deleteBudget()

    @Update
    suspend fun updateBudget(vararg budget: Budget)

    @Query("SELECT SUM(amount)  FROM Budget WHERE strftime('%Y', date('now'))")
    fun getTotalYearBudget(): LiveData<Float>

//    @Transaction
//    suspend fun upsert(budget: Budget) {
////        budget.id?.let { updateBudget(it) }
//        try {
//            insertBudget(budget)
//        }catch (ex:SQLiteConstraintException){
//            budget.id?.let { updateBudget(it) }
//        }
//    }


}