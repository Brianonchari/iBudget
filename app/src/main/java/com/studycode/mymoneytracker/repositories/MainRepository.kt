package com.studycode.mymoneytracker.repositories

import com.studycode.mymoneytracker.db.dao.*
import com.studycode.mymoneytracker.db.models.*
import javax.inject.Inject

class MainRepository @Inject constructor(
    val incomeDao: IncomeDao,
    val budgetDao: BudgetDao,
    val transactionDao: TransactionDao,
    val myDebtsDao: MyDebtsDao
) {
    //Income
    suspend fun addIncome(income: Income) = incomeDao.insertIncome(income)
    fun getAllSourcesOfIncome() = incomeDao.getAllIncome()
    fun getMonthlyTotalIncome() = incomeDao.getTotalMonthlyIncome()
    fun getTotalYearIncome() = incomeDao.getTotalYearIncome()
    suspend fun deleteIncome(income: Income) = incomeDao.deleteIncome(income)

    //Budget
    suspend fun saveBudget(budget: Budget) = budgetDao.insertBudget(budget)
    fun getAllBudgets() = budgetDao.getBudgetList()
    fun getMonthlyTotalBudget() = budgetDao.totalMonthlyBudget()
    fun getTotalYearBudget() = budgetDao.getTotalYearBudget()
    fun getFoodBudget() = budgetDao.getFoodBudget()
    fun getHealthBudget() = budgetDao.getHealthBudget()
    fun getRentBudget() = budgetDao.getRentBudget()
    fun getEducationBudget() = budgetDao.getEducationBudget()
    fun getMiscBudget() = budgetDao.getMiscPaymentsBudget()
    fun getTransportBudget() = budgetDao.getTransportBudget()
    fun getElectricityBudget() = budgetDao.getElectricityBudget()
    fun getWaterBudget() = budgetDao.getWaterBudget()
    fun getOtherBudget() = budgetDao.getOtherBudget()
    fun getFitnessbudget() = budgetDao.getFitnessBudget()
    suspend fun updateBudget(budget: Budget) = budgetDao.updateBudget(budget)
    suspend fun deleteBudget(budget: Budget) = budgetDao.deleteBudget(budget)

    //Transactons
    suspend fun saveTransaction(transaction: Transactions) =
        transactionDao.saveTransaction(transaction)
    fun getAllTransactions() = transactionDao.getAllTransactions()
    fun getTotalMonthlyTransaction() = transactionDao.totalMonthlyTransactions()
    fun getTotalYearTransactions()  = transactionDao.totalYearTransactions()
    suspend fun deleteTransaction(transaction: Transactions) = transactionDao.deleteTransaction(transaction)

    //Debts
    fun getAllDebts() = myDebtsDao.getMyDebts()
    fun totalDebts() = myDebtsDao.totalMonthlyDebts()
    suspend fun saveDebt(debts: MyDebts) = myDebtsDao.insertDebt(debts)
    suspend fun deleteDebt(debts: MyDebts) = myDebtsDao.deleteDebt(debts)
}