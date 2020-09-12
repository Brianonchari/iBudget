package com.studycode.mymoneytracker.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studycode.mymoneytracker.db.models.Budget


@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBudget(budget: Budget)

    @Query("SELECT * FROM Budget")
    fun getBudgetList(): LiveData<List<Budget>>

    @Query("SELECT  SUM(amount) FROM Budget where strftime('%m', date('now'))")
    fun getTotalBudget(): LiveData<Float>

    @Delete
    fun deleteBudget(budget: Budget): Int

    @Query("DELETE from budget")
    fun deleteBudget()

    @Update
    suspend fun updateBudget(budget: Budget)


}