package com.studycode.mymoneytracker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.studycode.mymoneytracker.db.dao.*
import com.studycode.mymoneytracker.db.models.*


@Database(
    entities = [Budget::class, Expenses::class, Income::class, Transactions::class, Fund::class, MyDebts::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase:RoomDatabase() {
    abstract fun getIncomeDao():IncomeDao
    abstract fun getBudgetDao():BudgetDao
    abstract fun getExpenseDao():ExpenseDao
    abstract fun getTransactionsDao():TransactionDao
    abstract fun getFundsDao():FundsDao
    abstract fun myDebtsDao():MyDebtsDao

}