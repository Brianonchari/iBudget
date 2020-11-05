package com.studycode.mymoneytracker.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.ui.viewmodels.StatisticsViewModel
import com.studycode.mymoneytracker.utils.NumberUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.Observer


@AndroidEntryPoint
class AnalyticsFragment : Fragment(R.layout.fragment_analytics) {

    companion object {
        private const val TAG = "AnalyticsFragment"
    }

    private val viewModel: StatisticsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBarchart()
        setupPieChart()
    }

    private fun setupPieChart(){
        viewModel.summary.observe(viewLifecycleOwner, Observer {
            val totalExpenses = viewModel.totalMonthlyTransactions.value
            val totalBudget = viewModel.totalMonthlyBudget.value
            val spannable1 = SpannableString("Total Expenses: ${totalExpenses?.let { it1 ->  NumberUtils.getFormattedAmount(it1) }}")
            spannable1.setSpan(ForegroundColorSpan(Color.BLACK), 0,15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            tvExpenses.text = spannable1
            val spannable2 = SpannableString("Total Budget: ${totalBudget?.let { it1 ->  NumberUtils.getFormattedAmount(it1) }}")
            spannable2.setSpan(ForegroundColorSpan(Color.BLACK), 0,13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            tv_budget.text=spannable2
            val totalYearExpenses = viewModel.totalMonthlyTransactions.value
            val totalYearIncome = viewModel.totalYearIncome.value
            val totalYearBudget = viewModel.totalYearBudget.value

            val entries: ArrayList<PieEntry> = ArrayList()
            val colors = ArrayList<Int>()
            colors.add(Color.YELLOW)
            colors.add(Color.GREEN)
            colors.add(R.color.purple)
            colors.add(R.color.pink)

            totalYearIncome?.let { it1 -> PieEntry(it1, "Total Year Income") }
                ?.let { it2 -> entries.add(it2) }
            totalYearBudget?.let { it1 -> PieEntry(it1, "Total Year Budget") }
                ?.let { it2 -> entries.add(it2)
                }
            totalYearExpenses?.let { it1 -> PieEntry(it1,"Total Expense" ) }?.let { it2 -> entries.add(it2) }

            val pieDataSet = PieDataSet(entries, "Expenditure  Summary")

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

    private fun setupBarchart() {
        viewModel.summary.observe(viewLifecycleOwner, Observer {
            val monthlyTransactions = viewModel.totalMonthlyTransactions.value
            val totalMonthlyBudget = viewModel.totalMonthlyBudget.value
            val totalDebts = viewModel.totalDebts.value
            val totalIncome = viewModel.totalMonthlyIncome.value
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