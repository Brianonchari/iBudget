package com.studycode.mymoneytracker.repositories

import com.studycode.mymoneytracker.db.dao.*
import com.studycode.mymoneytracker.db.models.*
import javax.inject.Inject

class MainRepository @Inject constructor(
    val incomeDao: IncomeDao,
    val budgetDao: BudgetDao,
    val transactionDao: TransactionDao,
    val fundsDao: FundsDao,
    val myDebtsDao: MyDebtsDao

) {
    //Income
    suspend fun addIncome(income: Income) = incomeDao.insertIncome(income)
    fun getAllSourcesOfIncome() = incomeDao.getAllIncome()
    fun getMonthlyTotalIncome() = incomeDao.getTotalMonthlyIncome()
    fun getTotalYearIncome() = incomeDao.getTotalYearIncome()

    //Budget
    suspend fun saveBudget(budget: Budget) = budgetDao.insertBudget(budget)
    fun getAllBudgets() = budgetDao.getBudgetList()
    fun getMonthlyTotalBudget() = budgetDao.totalMonthlyBudget()
    fun getTotalYearBudget() = budgetDao.getTotalYearBudget()
    suspend fun updateBudget(budget: Budget) = budgetDao.updateBudget(budget)

    //Transactons
    suspend fun saveTransaction(transaction: Transactions) =
        transactionDao.saveTransaction(transaction)

    fun getAllTransactions() = transactionDao.getAllTransactions()
    fun getTotalMonthlyTransaction() = transactionDao.totalMonthlyTransactions()
    fun getTotalYearTransactions()  = transactionDao.totalYearTransactions()

    //Debts
    fun getAllDebts() = myDebtsDao.getMyDebts()
    fun totalDebts() = myDebtsDao.totalMonthlyDebts()
    suspend fun saveDebt(debts: MyDebts) = myDebtsDao.insertDebt(debts)


}