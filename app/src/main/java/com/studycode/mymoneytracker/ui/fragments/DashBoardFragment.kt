package com.studycode.mymoneytracker.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.PieEntry
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.adapters.SourceOfIncomeAdapter
import com.studycode.mymoneytracker.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class DashBoardFragment : Fragment(R.layout.fragment_dashboard) {

    private val viewModel: MainViewModel by viewModels()
    lateinit var incomeAdapter: SourceOfIncomeAdapter

    companion object {
        private const val TAG = "DashBoardFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()
        viewModel.income.observe(viewLifecycleOwner, Observer {
            incomeAdapter.submitList(it)
        })
    }

    private fun setupRecyclerview() = incomeRV.apply {
        incomeAdapter = SourceOfIncomeAdapter()
        adapter = incomeAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupPieChart() {
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH)

        val totalMonthly = viewModel.totalMonthlyIncome(month)

        val entries: ArrayList<PieEntry> = ArrayList()

        val colors = java.util.ArrayList<Int>()
        colors.add(Color.GRAY)
        colors.add(Color.BLUE)
        colors.add(Color.RED)
        colors.add(Color.GREEN)

    }
}