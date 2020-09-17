package com.studycode.mymoneytracker.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.studycode.mymoneytracker.repositories.MainRepository

class StatisticsViewModel @ViewModelInject constructor(
   val mainRepository: MainRepository
):ViewModel(){
   var totalMonthlyIncome = mainRepository.getMonthlyTotalIncome()
   var totalMonthlyBudget = mainRepository.getMonthlyTotalBudget()
   var totalMonthlyTransactions = mainRepository.getTotalMonthlyTransaction()
   var totalDebts = mainRepository.totalDebts()
   var totalYearIncome = mainRepository.getTotalYearIncome()
   var totalYearBudget = mainRepository.getTotalYearBudget()
   var totalYearTransactions = mainRepository.getTotalYearTransactions()


   val summary = MediatorLiveData<Float>()

   init {
      summary.addSource(totalMonthlyIncome) { result ->
         result.let { summary.value = it }
      }
      summary.addSource(totalMonthlyBudget) { result ->
         result.let { summary.value = it }
      }
      summary.addSource(totalDebts){result->
         result.let { summary.value=it }
      }
      summary.addSource(totalMonthlyTransactions){result ->
         result.let { summary.value = it }
      }
      summary.addSource(totalYearBudget){result ->
         result.let { summary.value = it }
      }

      summary.addSource(totalYearIncome){result ->
         result.let { summary.value = it }
      }
      summary.addSource(totalYearTransactions){result ->
         result.let{summary.value = it}
      }
   }

}