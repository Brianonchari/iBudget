package com.studycode.mymoneytracker.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.db.models.Account
import com.studycode.mymoneytracker.db.models.Budget
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
        val adapter:ArrayAdapter<*> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.accounts,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        select_acc.adapter = adapter
        select_acc.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    0->{
                        btn_save_budget.setOnClickListener {

                            viewModel.cashBalance.observe(viewLifecycleOwner, Observer {
                                val totalIncome = viewModel.totalIncome.value
                                val cashBalance = viewModel.cashBalance.value
                                Log.d(TAG, "saveBudget: $cashBalance")
                                if(!TextUtils.isEmpty(budgetName.selectedItem.toString())){
                                    if(!TextUtils.isEmpty(amountEt.text)){
                                        if(amountEt.text.toString().toFloat() <= cashBalance!!){
                                            val budgetName = budgetName.selectedItem.toString()
                                            val budgetAmount = amountEt.text.toString().toFloat()
                                            val balance = amountEt.text.toString().toFloat()
                                            val budget = Budget(budgetName, budgetAmount, 0.toFloat(), balance)
                                            val cashBalance = (cashBalance-budgetAmount)
                                            val account =select_acc.selectedItem.toString()
                                            viewModel.updateCashAccount(account,cashBalance)
                                            viewModel.saveBudget(budget)
                                            findNavController().navigate(R.id.budgetFragment)
                                        }else{
                                            Toast.makeText(requireContext(), "Insufficient Amount", Toast.LENGTH_SHORT).show()
                                        }
                                    }else{
                                        Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
                                    }
                                }else{
                                    Toast.makeText(requireContext(), "All Fields are required", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                    }

                    1-> {
                        btn_save_budget.setOnClickListener {

                            viewModel.bankBalance.observe(viewLifecycleOwner, Observer {
                                val bankBalance = viewModel.bankBalance.value
                                Log.d(TAG, "saveBudget: $bankBalance")
                                if(!TextUtils.isEmpty(budgetName.selectedItem.toString())){
                                    if(!TextUtils.isEmpty(amountEt.text)){
                                        if(amountEt.text.toString().toFloat() <= bankBalance!!){
                                            val budgetName = budgetName.selectedItem.toString()
                                            val budgetAmount = amountEt.text.toString().toFloat()
                                            val balance = amountEt.text.toString().toFloat()
                                            val budget = Budget(budgetName, budgetAmount, 0.toFloat(), balance)
                                            val bankBalance = (bankBalance-budgetAmount)
                                            val account =select_acc.selectedItem.toString()
                                            viewModel.saveBudget(budget)
                                            viewModel.updateBankAccount(account,bankBalance)
                                            findNavController().navigate(R.id.budgetFragment)
                                        }else{
                                            Toast.makeText(requireContext(), "Insufficient Amount", Toast.LENGTH_SHORT).show()
                                        }
                                    }else{
                                        Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
                                    }
                                }else{
                                    Toast.makeText(requireContext(), "All Fields are required", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                    }
                }
            }

        }






    }


    private fun saveBudget() {



    }
}