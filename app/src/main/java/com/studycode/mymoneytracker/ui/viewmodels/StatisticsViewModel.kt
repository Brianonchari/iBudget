package com.studycode.mymoneytracker.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.studycode.mymoneytracker.repositories.MainRepository

class StatisticsViewModel @ViewModelInject constructor(
    val mainRepository: MainRepository
) : ViewModel() {
    var totalMonthlyIncome = mainRepository.getMonthlyTotalIncome()
    var totalMonthlyBudget = mainRepository.getMonthlyTotalBudget()
    var totalMonthlyTransactions = mainRepository.getTotalMonthlyTransaction()
    var totalDebts = mainRepository.totalDebts()
    var totalYearIncome = mainRepository.getTotalYearIncome()
    var totalYearBudget = mainRepository.getTotalYearBudget()
    var totalYearTransactions = mainRepository.getTotalYearTransactions()

    //Budget
    var foodBudget = mainRepository.getFoodBudget()
    var healthBudget = mainRepository.getHealthBudget()
    var rentBudget = mainRepository.getRentBudget()
    var educationBudget = mainRepository.getEducationBudget()
    var miscBudget = mainRepository.getMiscBudget()
    var transportBudget = mainRepository.getTransportBudget()
    var electricityBudget = mainRepository.getElectricityBudget()
    var waterBudget = mainRepository.getWaterBudget()
    var fitnessBudget = mainRepository.getFitnessbudget()
    var otherBudget = mainRepository.getOtherBudget()
    val summary = MediatorLiveData<Float>()

    init {
        summary.addSource(totalMonthlyIncome) { result ->
            result.let { summary.value = it }
        }
        summary.addSource(totalMonthlyBudget) { result ->
            result.let { summary.value = it }
        }
        summary.addSource(totalDebts) { result ->
            result.let { summary.value = it }
        }
        summary.addSource(totalMonthlyTransactions) { result ->
            result.let { summary.value = it }
        }
        summary.addSource(totalYearBudget) { result ->
            result.let { summary.value = it }
        }
        summary.addSource(totalYearIncome) { result ->
            result.let { summary.value = it }
        }
        summary.addSource(totalYearTransactions) { result ->
            result.let { summary.value = it }
        }
        summary.addSource(foodBudget) { result ->
            result.let { summary.value = it }
        }
        summary.addSource(healthBudget) { result ->
            result.let { summary.value = it }
        }
        summary.addSource(educationBudget) { result ->
            result.let { summary.value = it }
        }
        summary.addSource(rentBudget) { result ->
            result.let { summary.value = it }
        }
        summary.addSource(miscBudget) { result ->
            result.let { summary.value = it }
        }
        summary.addSource(transportBudget) { result ->
            result.let { summary.value = it }
        }
        summary.addSource(electricityBudget) { result ->
            result.let { summary.value = it }
        }
        summary.addSource(waterBudget) { result ->
            result.let { summary.value = it }
        }
        summary.addSource(fitnessBudget) { result ->
            result.let { summary.value = it }
        }
        summary.addSource(otherBudget) { result ->
            result.let { summary.value = it }
        }
    }
}