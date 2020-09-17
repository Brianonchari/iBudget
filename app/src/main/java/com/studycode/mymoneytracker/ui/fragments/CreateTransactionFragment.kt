package com.studycode.mymoneytracker.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.db.models.Budget
import com.studycode.mymoneytracker.db.models.Transactions
import com.studycode.mymoneytracker.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_transaction.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreateTransactionFragment : Fragment(R.layout.fragment_create_transaction) {

    private val viewModel: MainViewModel by viewModels()
    val args: CreateTransactionFragmentArgs by navArgs()

    companion object{
        private const val TAG = "CreateTransactionFragme"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var budget = args.budget

        payee_container.text = budget.category


        btn_save_transaction.setOnClickListener {
//
            saveTransaction()
            updateBudget()
            findNavController().navigate(R.id.transactionsFragment)
        }

    }

    private  fun saveTransaction(){
        var curBudget = args.budget
        val sdf = SimpleDateFormat("dd/M/yyyy  hh:mm:ss")
        val currentDate = sdf.format(Date())
        val transactionName = transaction.text.toString()
        val amount = transaction_amount.text

        val budgetAmount = curBudget.amount
        Log.d(TAG, "saveTransaction: $budgetAmount")

        
        if(transactionName.isEmpty() || amount.isNullOrEmpty() ){
            Snackbar.make(requireView(), "All fields required" , Snackbar.LENGTH_LONG).show()

        }else{
            val transaction = Transactions(transactionName, amount.toString().toFloat(), currentDate)
            viewModel.saveTransaction(transaction)
            Toast.makeText(requireContext(), "Transaction saved successfully", Toast.LENGTH_LONG).show()
        }

    }

    private fun updateBudget() {
        val _budget = args.budget
        val budgetName = payee_container.text.toString()
        val amount = transaction_amount.text
        val balance = (_budget.amount - amount.toString().toFloat())
        Log.d(TAG, "updateBudget: $balance")
        if (budgetName.isEmpty() || amount.isNullOrEmpty()) {
            Snackbar.make(requireView(), "All Fields are required", Snackbar.LENGTH_LONG).show()
        } else {
            val budget = Budget(budgetName, _budget.amount, amount.toString().toFloat(), balance)
            viewModel.updateBudget(budget)
            Log.d(TAG, "updateBudget: $budget")
//            _budget.id?.let { viewModel.updateBudget(budget) }
            val id = _budget.id
            if (_budget.id == id){
                viewModel.updateBudget(budget)
            }
            else{
                Toast.makeText(requireContext(), "Unable to update", Toast.LENGTH_LONG).show()
            }
        }
    }


}