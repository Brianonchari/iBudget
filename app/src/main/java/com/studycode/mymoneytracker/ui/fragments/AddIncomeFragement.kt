package com.studycode.mymoneytracker.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.db.models.Income
import com.studycode.mymoneytracker.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_income.*

@AndroidEntryPoint
class AddIncomeFragement : Fragment(R.layout.fragment_add_income) {
    private val viewModel: MainViewModel by viewModels()
    companion object {
        private const val TAG = "AddIncomeFragement"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveIncome.setOnClickListener {
            if (source.text!!.isEmpty() || amountEt.text!!.isEmpty()) {
                Snackbar.make(requireView(), "All fields are required", Snackbar.LENGTH_LONG).show()
            } else {
                addIncome()
                findNavController().navigate(R.id.dashBoardFragment)
            }
        }
    }

    private fun addIncome() {
        val income = source.text.toString()
        val amount = amountEt.text.toString()
        val source_of_income = Income(income,  amount.toFloat())
        viewModel.addIncome(source_of_income)
        Snackbar.make(requireView(), "Source saved successfully", Snackbar.LENGTH_LONG).show()
        Log.d(TAG, "addIncome: $source_of_income")
    }
}