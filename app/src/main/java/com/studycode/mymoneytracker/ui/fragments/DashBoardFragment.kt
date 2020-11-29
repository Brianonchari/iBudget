package com.studycode.mymoneytracker.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.android.material.snackbar.Snackbar
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.adapters.SourceOfIncomeAdapter
import com.studycode.mymoneytracker.ui.viewmodels.MainViewModel
import com.studycode.mymoneytracker.utils.NumberUtils.getFormattedAmount
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.*


@AndroidEntryPoint
class DashBoardFragment : Fragment(R.layout.fragment_dashboard) {
    private val viewModel: MainViewModel by viewModels()
    lateinit var incomeAdapter: SourceOfIncomeAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()
//        setupPieChart()
        viewModel.income.observe(viewLifecycleOwner, Observer {
            incomeAdapter.submitList(it)
        })
        viewModel.bankBalance.observe(viewLifecycleOwner, Observer {
            val balance = it?.let{ getFormattedAmount(it)}?:"$0.00"
            bank.text= balance
        })

        viewModel.cashBalance.observe(viewLifecycleOwner, Observer {
            val balance = it?.let { getFormattedAmount(it) }?:"$0.00"
            cash.text = balance
        })

        viewModel.totalAccBalance.observe(viewLifecycleOwner, Observer {
            val totalIncome = it?.let { getFormattedAmount(it) }?:"$0.00"
            totalInomeAmount.text = totalIncome
        })



        val itemTouchHelper = object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val income = incomeAdapter.differ.currentList[position]
                viewModel.deleteIncome(income)
                Snackbar.make(view, "Item Deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("UNDO" ) {
                        viewModel.addIncome(income)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(incomeRV)
        }
        fabAddIncome.setOnClickListener {
            findNavController().navigate(R.id.addIncomeFragement)
        }
    }

    private fun setupRecyclerview() = incomeRV.apply {
        incomeAdapter = SourceOfIncomeAdapter()
        adapter = incomeAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

}