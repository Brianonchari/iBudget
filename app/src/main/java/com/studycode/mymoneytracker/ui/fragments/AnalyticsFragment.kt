package com.studycode.mymoneytracker.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.ui.viewmodels.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_analytics.*


@AndroidEntryPoint
class AnalyticsFragment : Fragment(R.layout.fragment_analytics) {

    companion object {
        private const val TAG = "AnalyticsFragment"
    }

    private val viewModel: StatisticsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBarchart()
    }

    private fun setupBarchart() {
        viewModel.summary.observe(viewLifecycleOwner, Observer {
            val monthlyTransactions = viewModel.totalMonthlyTransactions.value
            val totalMonthlyBudget = viewModel.totalBudget.value
            val totalDebts = viewModel.totalDebts.value
            val totalIncome = viewModel.totalIncome.value
            val entries: ArrayList<BarEntry> = ArrayList()

            Log.d(TAG, "setupBarchart: $monthlyTransactions")
            monthlyTransactions?.let { it1 -> BarEntry(1f, it1, "Total Monthly Income") }
                ?.let { it2 -> entries.add(it2) }

            totalMonthlyBudget?.let { it1 -> BarEntry(2f, it1, "") }
                ?.let { it2 -> entries.add(it2) }
            totalDebts?.let { it1 -> BarEntry(3f, it1, "") }?.let { it2 -> entries.add(it2) }
            totalIncome?.let { it1 -> BarEntry(4f, it1, "") }?.let { it2 -> entries.add(it2) }


            val labels = arrayOf(
                "Dummy", "Total Transaction", "Total Budget", "Total Debts", "Total Income"
            )
            barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            barChart.xAxis.granularity = 1f
            barChart.xAxis.isGranularityEnabled = true

            val dataSet = BarDataSet(entries, "BarData")
            val barData = BarData(dataSet)
            barChart.setData(barData)
            barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            barChart.description.isEnabled = false
            barChart.animateY(1000)
            barChart.legend.isEnabled = false
            barChart.setPinchZoom(true)
            barChart.data.setDrawValues(false)
            barChart.axisRight.setDrawLabels(false)
            barData.barWidth = 0.2f
        })
    }


}