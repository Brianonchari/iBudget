package com.studycode.mymoneytracker.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.studycode.mymoneytracker.repositories.StatisticsRepository

class StatisticsViewModel @ViewModelInject constructor(
    val statisticsRepository: StatisticsRepository
):ViewModel(){

}