package com.studycode.mymoneytracker.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studycode.mymoneytracker.db.models.Budget
import com.studycode.mymoneytracker.db.models.Income
import com.studycode.mymoneytracker.db.models.Transactions
import com.studycode.mymoneytracker.repositories.MainRepository
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    val mainRepository: MainRepository
) : ViewModel() {
    //Income
    var income = mainRepository.getAllSourcesOfIncome()
    var totalIncome = mainRepository.getMonthlyTotalIncome()
    fun addIncome(income: Income) = viewModelScope.launch {
        mainRepository.addIncome(income)
    }

    //Budget
    var budgets = mainRepository.getAllBudgets()
    var totalBudget = mainRepository.getMonthlyTotalBudget()

    //    fun updateBudget(budget: Budget) {
//        mainRepository.updateBudget(budget)
//    }
    fun updateBudget(budget: Budget) = viewModelScope.launch {
        mainRepository.updateBudget(budget)
    }

    fun saveBudget(budget: Budget) = viewModelScope.launch {
        mainRepository.saveBudget(budget)
    }

    //Transactions
    var transactions = mainRepository.getAllTransactions()
    fun saveTransaction(transaction: Transactions) = viewModelScope.launch {
        mainRepository.saveTransaction(transaction)
    }

    val summary = MediatorLiveData<Float>()

    //
    init {
        summary.addSource(totalIncome) { result ->
            result.let { summary.value = it }
        }
        summary.addSource(totalBudget) { result ->
            result.let { summary.value = it }
        }
    }


}