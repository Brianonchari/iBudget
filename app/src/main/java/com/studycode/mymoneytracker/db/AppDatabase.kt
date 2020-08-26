package com.studycode.mymoneytracker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.studycode.mymoneytracker.db.dao.BudgetDao
import com.studycode.mymoneytracker.db.dao.ExpenseDao
import com.studycode.mymoneytracker.db.dao.IncomeDao
import com.studycode.mymoneytracker.db.models.Budget
import com.studycode.mymoneytracker.db.models.Expenses
import com.studycode.mymoneytracker.db.models.Income


@Database(
    entities = [Budget::class, Expenses::class, Income::class],
    version = 1
)
abstract class AppDatabase:RoomDatabase() {
    abstract fun getIncomeDao():IncomeDao
    abstract fun getBudgetDao():BudgetDao
    abstract fun getExpenseDao():ExpenseDao

}