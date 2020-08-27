package com.studycode.mymoneytracker.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studycode.mymoneytracker.db.models.Income
import com.studycode.mymoneytracker.repositories.MainRepository
import kotlinx.coroutines.launch
import java.time.Month

class MainViewModel @ViewModelInject constructor(
    val mainRepository: MainRepository
) : ViewModel() {

    var income = mainRepository.getAllSourcesOfIncome()
    var totalIncome = mainRepository.getTotalOfMonth()
    val summary = MediatorLiveData<Float>()

    init {
        summary.addSource(totalIncome) { result ->
            result.let { summary.value = it }
        }
    }

    fun addIncome(income: Income) = viewModelScope.launch {
        mainRepository.addIncome(income)
    }


}