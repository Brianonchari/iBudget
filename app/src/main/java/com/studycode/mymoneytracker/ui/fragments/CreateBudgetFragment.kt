package com.studycode.mymoneytracker.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.db.models.Budget
import com.studycode.mymoneytracker.db.models.Fund
import com.studycode.mymoneytracker.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_budget.*
import kotlinx.android.synthetic.main.fragment_create_budget.amountEt

@AndroidEntryPoint
class CreateBudgetFragment : Fragment(R.layout.fragment_create_budget) {
    private val viewModel: MainViewModel by viewModels()
    var budgetId: Int = 0
    var mbudget: Budget? = null

    companion object {
        private const val TAG = "CreateBudgetFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_save_budget.setOnClickListener {
            saveBudget()
            findNavController().navigate(R.id.budgetFragment)
        }
    }

    private fun saveBudget() {
        val budgetName = budgetName.text.toString()
        val budgetAmount = amountEt.text.toString()

        if (budgetName.isEmpty() || budgetAmount.isEmpty()) {
            Snackbar.make(requireView(), "All fields are required", Snackbar.LENGTH_LONG).show()
        } else {
            val budget = Budget(budgetName, budgetAmount.toFloat(), 0.toFloat(), 0.toFloat() )
            val catId = viewModel.saveBudget(budget)


            Log.d(TAG, "saveBudget: $catId")
            Snackbar.make(requireView(), "Budget saved succesfully", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun updateBudget(){

        val budgetName = budgetName.text.toString()
        val budgetAmount = amountEt.text.toString()

        if (budgetName.isEmpty() || budgetAmount.isEmpty()) {
            Snackbar.make(requireView(), "All fields are required", Snackbar.LENGTH_LONG).show()
        } else {
            val budget = Budget(budgetName, budgetAmount.toFloat(), 0.toFloat(), 0.toFloat())
            val catId = viewModel.updateBudget(budgetId)
            Log.d(TAG, "saveBudget: $catId")
            Snackbar.make(requireView(), "Budget saved succesfully", Snackbar.LENGTH_LONG).show()
        }


    }
}