package com.studycode.mymoneytracker.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.adapters.DebtsRecyclerAdapter
import com.studycode.mymoneytracker.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_debts.*

@AndroidEntryPoint
class DebtsFragments : Fragment(R.layout.fragment_debts) {
    private val viewModel: MainViewModel by viewModels()
    lateinit var debtsRecyclerAdapter: DebtsRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()
        viewModel.debts.observe(viewLifecycleOwner, Observer {
            debtsRecyclerAdapter.submitList(it)
        })

        floatingButton.setOnClickListener{
            findNavController().navigate(R.id.action_debtsFragments_to_addDebtsFragment)
        }
    }


    private fun setupRecyclerview() = debtsRv.apply {
        debtsRecyclerAdapter = DebtsRecyclerAdapter()
        adapter = debtsRecyclerAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

}