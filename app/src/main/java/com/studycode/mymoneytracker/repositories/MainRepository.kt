package com.studycode.mymoneytracker.repositories

import com.studycode.mymoneytracker.db.dao.BudgetDao
import com.studycode.mymoneytracker.db.dao.IncomeDao
import com.studycode.mymoneytracker.db.models.Budget
import com.studycode.mymoneytracker.db.models.Income
import java.time.Month
import javax.inject.Inject

class MainRepository @Inject constructor(
    val incomeDao: IncomeDao,
    val budgetDao: BudgetDao
) {
    suspend fun addIncome(income: Income) = incomeDao.insertIncome(income)
    suspend fun saveBudget(budget: Budget) = budgetDao.insertBudget(budget)
    fun getAllSourcesOfIncome() = incomeDao.getAllIncome()
    fun getAllBudgets() = budgetDao.getBudgetList()
    fun getTotalOfMonth() = incomeDao.getTotalMonthlyIncome()
}