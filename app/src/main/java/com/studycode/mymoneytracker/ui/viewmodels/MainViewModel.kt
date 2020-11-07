package com.studycode.mymoneytracker.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studycode.mymoneytracker.db.models.Budget
import com.studycode.mymoneytracker.db.models.Income
import com.studycode.mymoneytracker.db.models.MyDebts
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
    fun updateBudget(budget: Budget) = viewModelScope.launch {
        mainRepository.updateBudget(budget)
    }
    fun saveBudget(budget: Budget) = viewModelScope.launch {
        mainRepository.saveBudget(budget)
    }
    //Transactions
    fun getTransaction() = mainRepository.getAllTransactions()
    var transactions = mainRepository.getAllTransactions()
    var totalMonthlyTransactions = mainRepository.getTotalMonthlyTransaction()
    fun saveTransaction(transaction: Transactions) = viewModelScope.launch {
        mainRepository.saveTransaction(transaction)
    }
    //Debts
    var debts = mainRepository.getAllDebts()
    var totalDebts = mainRepository.totalDebts()
    fun saveDebt(debts: MyDebts) = viewModelScope.launch {
        mainRepository.saveDebt(debts)
    }
    //Mediators
    val summary = MediatorLiveData<Float>()
    val _transactions = MediatorLiveData<List<Transactions>>()
    val _income = MediatorLiveData<List<Income>>()
    //
    init {
        summary.addSource(totalIncome) { result ->
            result.let { summary.value = it }
        }
        summary.addSource(totalBudget) { result ->
            result.let { summary.value = it }
        }
        summary.addSource(totalDebts) { result ->
            result.let { summary.value = it }
        }
        _transactions.addSource(transactions) { result ->
            result.let { _transactions.value = it }
        }
        summary.addSource(totalMonthlyTransactions) { result ->
            result.let { summary.value = it }
        }
        _income.addSource(income) { result ->
            result.let { _income.value = it }
        }
    }
}