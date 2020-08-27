package com.studycode.mymoneytracker.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studycode.mymoneytracker.db.models.Budget
import com.studycode.mymoneytracker.db.models.Income

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBudget(budget: Budget)

//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun inserBudgetList(budget: LiveData<List<Budget>>)

    @Query("SELECT * FROM Budget")
     fun getBudgetList():LiveData<List<Budget>>

    @Delete
    fun deleteBudget(budget: Budget): Int

    @Query("DELETE from budget")
    fun deleteBudget()



}