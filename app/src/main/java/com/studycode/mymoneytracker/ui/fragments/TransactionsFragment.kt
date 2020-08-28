package com.studycode.mymoneytracker.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.adapters.TransactionsRecyclerAapter
import com.studycode.mymoneytracker.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_transactions.*

@AndroidEntryPoint
class TransactionsFragment :Fragment(R.layout.fragment_transactions){
    private val viewModel:MainViewModel by viewModels()
    lateinit var transactionsAdapter:TransactionsRecyclerAapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        viewModel.transactions.observe(viewLifecycleOwner, Observer {
            transactionsAdapter.submitList(it)
        })


    }

    private fun setupLineChart(){
    }

    fun setupRecyclerView() = transactionsRv.apply {
        transactionsAdapter = TransactionsRecyclerAapter()
        adapter = transactionsAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

}