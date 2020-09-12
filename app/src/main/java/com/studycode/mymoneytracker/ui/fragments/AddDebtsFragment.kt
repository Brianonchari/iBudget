package com.studycode.mymoneytracker.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.db.models.MyDebts
import com.studycode.mymoneytracker.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_debts.*
import kotlinx.android.synthetic.main.fragment_add_income.*
import kotlinx.android.synthetic.main.fragment_debts.*
import kotlinx.android.synthetic.main.item_debts.*
import kotlinx.android.synthetic.main.item_debts.payee_amount
import kotlinx.android.synthetic.main.item_debts.payee_name
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddDebtsFragment :Fragment(R.layout.fragment_add_debts){

    private val viewModel:MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveDebt.setOnClickListener {
            saveDebt()
            findNavController().navigate(R.id.debtsFragments)
        }

    }

    private fun saveDebt(){
        // TODO: 12/09/2020 Update date with date picker
        val payee = payeeName.text.toString()
        val payeeAmount = payeeamountEt.text.toString()

        val sdf = SimpleDateFormat("dd/M/yyyy  hh:mm:ss")
        val currentDate = sdf.format(Date())

        if(payee.isEmpty()|| payeeAmount.isEmpty()){
            Snackbar.make(requireView(), "All Fields are required", Snackbar.LENGTH_LONG).show()
        }else{
            val debts = MyDebts(payee, payeeAmount.toFloat(), currentDate )
            viewModel.saveDebt(debts)
            Snackbar.make(requireView(), "Added Successfully", Snackbar.LENGTH_LONG).show()
        }
    }

}