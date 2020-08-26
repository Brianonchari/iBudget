package com.studycode.mymoneytracker.db.dao

import androidx.room.*
import com.studycode.mymoneytracker.db.models.Expenses

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExpense(expense: Expenses):Long

    @Query("SELECT * from expenses where categoryId =:catId")
    fun getExpensesList(catId:Int?): List<Expenses>
    @Delete
    fun deleteExpense(expense: Expenses): Int

    @Query("DELETE FROM expenses where categoryId =:catId")
    fun deleteExpenses(catId: Int?): Int

    @Query("DELETE FROM expenses")
    fun deleteExpenses()

}