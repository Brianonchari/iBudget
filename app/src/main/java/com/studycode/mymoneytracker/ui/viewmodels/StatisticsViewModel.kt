package com.studycode.mymoneytracker.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.studycode.mymoneytracker.repositories.MainRepository

class StatisticsViewModel @ViewModelInject constructor(
   val mainRepository: MainRepository
):ViewModel(){
   var totalIncome = mainRepository.getMonthlyTotalIncome()
   var totalBudget = mainRepository.getMonthlyTotalBudget()
   var totalMonthlyTransactions = mainRepository.getTotalMonthlyTransaction()
   var totalDebts = mainRepository.totalDebts()

   val summary = MediatorLiveData<Float>()

   init {
      summary.addSource(totalIncome) { result ->
         result.let { summary.value = it }
      }
      summary.addSource(totalBudget) { result ->
         result.let { summary.value = it }
      }
      summary.addSource(totalDebts){result->
         result.let { summary.value=it }
      }
      summary.addSource(totalMonthlyTransactions){result ->
         result.let { summary.value = it }
      }
   }

}