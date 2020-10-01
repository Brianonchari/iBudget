package com.studycode.mymoneytracker.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.material.snackbar.Snackbar
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.db.models.Budget
import com.studycode.mymoneytracker.db.models.Transactions
import com.studycode.mymoneytracker.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_transaction.*
import kotlinx.android.synthetic.main.fragment_create_transaction.payee_container
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreateTransactionFragment : Fragment(R.layout.fragment_create_transaction) {
    private val viewModel: MainViewModel by viewModels()
    val args: CreateTransactionFragmentArgs by navArgs()

    companion object{
        private const val TAG = "CreateTransactionFragme"
    }
    private val mAppUnitId: String by lazy {
        "ca-app-pub-7628201468416367~8045665967"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var budget = args.budget

        val transactionName = transaction.text.toString()

        val budgetAmount = budget.amount
        val sdf = SimpleDateFormat("dd/M/yyyy  hh:mm:ss")
        val currentDate = sdf.format(Date())

        loadBannerAd()
        initializeBannerAd(mAppUnitId)

        payee_container.text = budget.category
        btn_save_transaction.setOnClickListener {
            if(!TextUtils.isEmpty(transaction.text)){
                if(!TextUtils.isEmpty(transaction_amount.text)){
                    val transactionAmount = transaction_amount.text.toString().toFloat()
                    if(transactionAmount < budgetAmount){
                        val transaction = Transactions(transactionName, transactionAmount.toString().toFloat(), currentDate)
                        viewModel.saveTransaction(transaction)
                        Snackbar.make(requireView(), "Transaction saved", Snackbar.LENGTH_LONG).show()
                        findNavController().navigate(R.id.transactionsFragment)
                    }else{
                        Snackbar.make(requireView(), "Insufficient Balance", Snackbar.LENGTH_LONG).show()
                    }
                }else{
                    Snackbar.make(requireView(), "This Field cant be empty", Snackbar.LENGTH_LONG).show()
                }
            }else{
                Snackbar.make(requireView(), "This Field cant be empty", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private  fun saveTransaction(){
        var curBudget = args.budget
        val sdf = SimpleDateFormat("dd/M/yyyy  hh:mm:ss")
        val currentDate = sdf.format(Date())
        val transactionName = transaction.text.toString()
        val amount = transaction_amount.text.toString().toFloat()
        val budgetAmount = curBudget.amount
        Log.d(TAG, "saveTransaction: $budgetAmount")

//        if(amount > budgetAmount ){
//            Snackbar.make(requireView(), "Insufficient Amount", Snackbar.LENGTH_SHORT).show()
//        }
        if(transactionName.isEmpty() || amount.equals("") || (amount > budgetAmount) ){
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
        }

        else {
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

    private fun initializeBannerAd(appUnitId: String) {
        MobileAds.initialize(requireContext())
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("BBCA5E24BC5636FC66C9E085A1DB6C0A"))
                .build()
        )
    }

    private fun loadBannerAd() {
        val adRequest = AdRequest.Builder()
//            .addTestDevice("BBCA5E24BC5636FC66C9E085A1DB6C0A")
            .build()
        adview.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        adview.resume()
    }

}