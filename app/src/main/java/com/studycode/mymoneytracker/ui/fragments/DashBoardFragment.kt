package com.studycode.mymoneytracker.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.adapters.SourceOfIncomeAdapter
import com.studycode.mymoneytracker.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.*
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
        setupPieChart()
        viewModel.income.observe(viewLifecycleOwner, Observer {
            incomeAdapter.submitList(it)
        })

        viewModel.totalIncome.observe(viewLifecycleOwner, Observer {
            val totalMonthlyIncome = viewModel.totalIncome.value
            Log.d(TAG, "onViewCreated: $totalMonthlyIncome")
            netIncomeTv.text = "Total : $totalMonthlyIncome"
            tvIncome.text = "Net Income : ${totalMonthlyIncome}"

        })
    }

    private fun setupRecyclerview() = incomeRV.apply {
        incomeAdapter = SourceOfIncomeAdapter()
        adapter = incomeAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupPieChart() {
        viewModel.summary.observe(viewLifecycleOwner, Observer {
            val totalMonthly = viewModel.totalIncome.value
            val entries: ArrayList<PieEntry> = ArrayList()
            val colors = java.util.ArrayList<Int>()
            colors.add(Color.GRAY)
            colors.add(Color.BLUE)
            colors.add(Color.RED)
            colors.add(Color.GREEN)

            totalMonthly?.let { it1 -> PieEntry(it1, "Total Monthly Income") }
                ?.let { it2 -> entries.add(it2) }

            val pieDataSet = PieDataSet(entries, "Expenses Summary")

            pieDataSet.setColors(colors)
            pieDataSet.valueTextSize = 14f
            pieDataSet.valueTextColor = Color.BLACK
            pieDataSet.valueFormatter = PercentFormatter(pie_chart)

            val pieData = PieData(pieDataSet)
            val legend = pie_chart.legend
            legend.textSize = 14f
            pie_chart.holeRadius = 58f
            pie_chart.transparentCircleRadius = 61f
            pie_chart.setDrawCenterText(true)
            pie_chart.rotationAngle = 0f
            pie_chart.legend.isEnabled = false
            pie_chart.isDrawHoleEnabled = true
            pie_chart.description.isEnabled = false
            pie_chart.isHighlightPerTapEnabled = true
            pie_chart.setEntryLabelColor(Color.BLACK)
            pie_chart.setEntryLabelTextSize(12f)
            pie_chart.data = pieData
            pie_chart.setUsePercentValues(true)
            pie_chart.invalidate()
            pie_chart.animateY(1500, Easing.EaseInOutSine)
        })

    }
}