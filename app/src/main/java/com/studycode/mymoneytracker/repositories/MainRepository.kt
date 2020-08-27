package com.studycode.mymoneytracker.repositories

import com.studycode.mymoneytracker.db.dao.IncomeDao
import com.studycode.mymoneytracker.db.models.Income
import java.time.Month
import javax.inject.Inject

class MainRepository @Inject constructor(
    val incomeDao: IncomeDao
){
    suspend fun addIncome(income: Income) = incomeDao.insertIncome(income)
    fun getAllSourcesOfIncome() = incomeDao.getAllIncome()
    suspend fun getTotalOfMonth(month: Int) = incomeDao.getTotalMonthlyIncome(month)
}