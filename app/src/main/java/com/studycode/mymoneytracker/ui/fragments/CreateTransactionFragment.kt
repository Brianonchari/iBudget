package com.studycode.mymoneytracker.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.studycode.mymoneytracker.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_transaction.*

@AndroidEntryPoint
class CreateTransactionFragment : Fragment(R.layout.fragment_create_transaction) {


    val args: CreateTransactionFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var budget = args.budget


        budget_name.text = budget.category

    }



}