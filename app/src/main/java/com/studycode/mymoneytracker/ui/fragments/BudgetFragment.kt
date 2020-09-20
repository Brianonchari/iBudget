package com.studycode.mymoneytracker.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.adapters.BudgetRecyclerAdapter
import com.studycode.mymoneytracker.adapters.SourceOfIncomeAdapter
import com.studycode.mymoneytracker.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_budget.*

@AndroidEntryPoint
class BudgetFragment :Fragment(R.layout.fragment_budget){
    private val viewModel:MainViewModel by viewModels()
    lateinit var budgetAdapter:BudgetRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        budgetAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("budget",it)

            }
            findNavController().navigate(R.id.createTransactionFragment, bundle)

        }
        viewModel.totalBudget.observe(viewLifecycleOwner, Observer {
            val totalMonthlyBudget = viewModel.totalBudget.value
            total_budget.text = "$totalMonthlyBudget"
        })
        viewModel.budgets.observe(viewLifecycleOwner, Observer {
            budgetAdapter.submitList(it)
        })
    }

    private fun setupRecyclerView() = budget_rv.apply{
        budgetAdapter = BudgetRecyclerAdapter()
        adapter = budgetAdapter
        budgetAdapter.differ.currentList
        layoutManager = LinearLayoutManager(requireContext())
    }
}