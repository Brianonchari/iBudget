package com.studycode.mymoneytracker.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studycode.mymoneytracker.db.models.Income
import com.studycode.mymoneytracker.repositories.MainRepository
import kotlinx.coroutines.launch
import java.time.Month

class MainViewModel @ViewModelInject constructor(
    val mainRepository: MainRepository
) :ViewModel(){

    var income = mainRepository.getAllSourcesOfIncome()
    fun totalMonthlyIncome(month: Int) =viewModelScope.launch {
        mainRepository.getTotalOfMonth(month)
    }
    fun addIncome(income: Income) = viewModelScope.launch {
        mainRepository.addIncome(income)
    }



}