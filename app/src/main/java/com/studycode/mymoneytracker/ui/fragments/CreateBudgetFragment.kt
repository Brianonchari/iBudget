package com.studycode.mymoneytracker.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
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
        if(!TextUtils.isEmpty(budgetName.selectedItem.toString())){
            if(!TextUtils.isEmpty(amountEt.text)){
                val budgetName = budgetName.selectedItem.toString()
                val budgetAmount = amountEt.text.toString().toFloat()
                val budget = Budget(budgetName, budgetAmount, 0.toFloat(), 0.toFloat())
                viewModel.saveBudget(budget)
            }else{
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(requireContext(), "All Fields are required", Toast.LENGTH_SHORT).show()
        }
    }
}