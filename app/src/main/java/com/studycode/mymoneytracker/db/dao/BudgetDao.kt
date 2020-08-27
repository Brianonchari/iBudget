package com.studycode.mymoneytracker.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studycode.mymoneytracker.db.models.Budget

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBudget(budget: Budget)
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun inserBudgetList(budget: LiveData<List<Budget>>)

    @Query("SELECT * FROM Budget")
    suspend fun getBudgetList():List<Budget>

    @Delete
    fun deleteBudget(budget: Budget): Int

    @Query("DELETE from budget")
    fun deleteBudget()



}