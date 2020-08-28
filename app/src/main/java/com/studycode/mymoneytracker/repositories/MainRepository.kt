package com.studycode.mymoneytracker.repositories

import com.studycode.mymoneytracker.db.dao.BudgetDao
import com.studycode.mymoneytracker.db.dao.IncomeDao
import com.studycode.mymoneytracker.db.dao.TransactionDao
import com.studycode.mymoneytracker.db.models.Budget
import com.studycode.mymoneytracker.db.models.Income
import com.studycode.mymoneytracker.db.models.Transactions
import javax.inject.Inject

class MainRepository @Inject constructor(
    val incomeDao: IncomeDao,
    val budgetDao: BudgetDao,
    val transactionDao: TransactionDao

) {
    //Income
    suspend fun addIncome(income: Income) = incomeDao.insertIncome(income)
    fun getAllSourcesOfIncome() = incomeDao.getAllIncome()
    fun getMonthlyTotalIncome() = incomeDao.getTotalMonthlyIncome()

    //Budget
    suspend fun saveBudget(budget: Budget) = budgetDao.insertBudget(budget)
    fun getAllBudgets() = budgetDao.getBudgetList()
    fun getMonthlyTotalBudget() = budgetDao.getTotalBudget()

    //Transactons
    suspend fun saveTransaction(transaction: Transactions) = transactionDao.saveTransaction(transaction)
    fun getAllTransactions() = transactionDao.getAllTransactions()
}