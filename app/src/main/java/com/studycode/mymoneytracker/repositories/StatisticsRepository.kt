package com.studycode.mymoneytracker.repositories

import com.studycode.mymoneytracker.db.dao.BudgetDao
import com.studycode.mymoneytracker.db.dao.IncomeDao
import com.studycode.mymoneytracker.db.dao.MyDebtsDao
import javax.inject.Inject

class StatisticsRepository @Inject constructor(
    val myDebtsDao: MyDebtsDao,
    val incomeDao: IncomeDao,
    val budgetDao: BudgetDao

) {

}