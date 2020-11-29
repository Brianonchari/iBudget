package com.studycode.mymoneytracker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.studycode.mymoneytracker.db.dao.*
import com.studycode.mymoneytracker.db.models.*


@Database(
    entities = [Budget::class, Income::class, Transactions::class, MyDebts::class, Account::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase:RoomDatabase() {
    abstract fun getIncomeDao():IncomeDao
    abstract fun getBudgetDao():BudgetDao
    abstract fun getTransactionsDao():TransactionDao
    abstract fun myDebtsDao():MyDebtsDao
    abstract fun getAccountDao() :AccountDao
}