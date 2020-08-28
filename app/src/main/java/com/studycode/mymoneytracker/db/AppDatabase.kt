package com.studycode.mymoneytracker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.studycode.mymoneytracker.db.dao.*
import com.studycode.mymoneytracker.db.models.*


@Database(
    entities = [Budget::class, Expenses::class, Income::class, Transactions::class],
    version = 1
)
abstract class AppDatabase:RoomDatabase() {
    abstract fun getIncomeDao():IncomeDao
    abstract fun getBudgetDao():BudgetDao
    abstract fun getExpenseDao():ExpenseDao
    abstract fun getFundsDao():FundDao
    abstract fun getTransactionsDao():TransactionDao

}