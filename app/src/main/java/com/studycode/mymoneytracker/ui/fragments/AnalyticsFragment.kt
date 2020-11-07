package com.studycode.mymoneytracker.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View

import androidx.fragment.app.viewModels

import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.ui.viewmodels.StatisticsViewModel
import com.studycode.mymoneytracker.utils.NumberUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_analytics.*
import java.util.*
import androidx.lifecycle.Observer


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
        setupCategoryPieChart()
    }

    private fun setupCategoryPieChart() {
        viewModel.summary.observe(viewLifecycleOwner, Observer {
            val food = viewModel.foodBudget.value
            val rent = viewModel.rentBudget.value
            val health = viewModel.healthBudget.value
            val education = viewModel.educationBudget.value
            val transport = viewModel.transportBudget.value
            val water = viewModel.waterBudget.value
            val other = viewModel.otherBudget.value
            val electricity = viewModel.electricityBudget.value
            val fitness = viewModel.fitnessBudget.value
            val misc = viewModel.miscBudget.value

            val entries: ArrayList<PieEntry> = ArrayList()
            val colors = ArrayList<Int>()
            colors.add(Color.YELLOW)
            colors.add(Color.GREEN)
            colors.add(Color.WHITE)
            colors.add(Color.CYAN)
            colors.add(Color.YELLOW)
            colors.add(Color.RED)
            colors.add(Color.MAGENTA)
            colors.add(Color.BLACK)
            colors.add(Color.LTGRAY)
            colors.add(Color.BLUE)
            colors.add(R.color.purple)
            colors.add(R.color.pink)

            food?.let { it1 -> PieEntry(it1, "Food") }?.let { it2 -> entries.add(it2) }
            rent?.let { it1 -> PieEntry(it1, "Rent") }?.let { it2 -> entries.add(it2) }
            health?.let { it1 -> PieEntry(it1, "Health") }?.let { it2 -> entries.add(it2) }
            education?.let { it1 -> PieEntry(it1, "Education") }?.let { it2 -> entries.add(it2) }
            transport?.let { it1 -> PieEntry(it1, "Transport") }?.let { it2 -> entries.add(it2) }
            water?.let { it1 -> PieEntry(it1, "Water") }?.let { it2 -> entries.add(it2) }
            other?.let { it1 -> PieEntry(it1, "Other") }?.let { it2 -> entries.add(it2) }
            electricity?.let { it1 -> PieEntry(it1, "Electricity") }?.let { it2 -> entries.add(it2) }
            fitness?.let { it1 -> PieEntry(it1, "Fitness") }?.let { it2 -> entries.add(it2) }
            misc?.let { it1 -> PieEntry(it1, "Misc Payments") }?.let { it2 -> entries.add(it2) }

            val pieDataSet = PieDataSet(entries, "Budgets")

            pieDataSet.setColors(colors)
            pieDataSet.valueTextSize = 14f
            pieDataSet.valueTextColor = Color.BLACK
            pieDataSet.valueFormatter = PercentFormatter(categoty_piechart)

            val pieData = PieData(pieDataSet)
            val legend = pie_chart.legend
            legend.textSize = 14f
            categoty_piechart.holeRadius = 58f
            categoty_piechart.transparentCircleRadius = 61f
            categoty_piechart.setDrawCenterText(true)
            categoty_piechart.rotationAngle = 0f
            categoty_piechart.legend.isEnabled = false
            categoty_piechart.isDrawHoleEnabled = true
            categoty_piechart.description.isEnabled = false
            categoty_piechart.isHighlightPerTapEnabled = true
            categoty_piechart.setEntryLabelColor(Color.BLACK)
            categoty_piechart.setEntryLabelTextSize(12f)
            categoty_piechart.data = pieData
            categoty_piechart.setUsePercentValues(true)
            categoty_piechart.invalidate()
            categoty_piechart.animateY(1500, Easing.EaseInOutSine)
        })
    }

    private fun setupPieChart() {
        viewModel.summary.observe(viewLifecycleOwner, Observer {
            val totalExpenses = viewModel.totalMonthlyTransactions.value
            val totalBudget = viewModel.totalMonthlyBudget.value
            val spannable1 = SpannableString("Total Expenses: ${totalExpenses?.let { it1 ->
                NumberUtils.getFormattedAmount(it1)
            }}")
            spannable1.setSpan(
                ForegroundColorSpan(Color.BLACK),
                0,
                15,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tvExpenses.text = spannable1
            val spannable2 = SpannableString("Total Budget: ${totalBudget?.let { it1 ->
                NumberUtils.getFormattedAmount(it1)
            }}")
            spannable2.setSpan(
                ForegroundColorSpan(Color.BLACK),
                0,
                13,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tv_budget.text = spannable2
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
                ?.let { it2 ->
                    entries.add(it2)
                }
            totalYearExpenses?.let { it1 -> PieEntry(it1, "Total Expense") }
                ?.let { it2 -> entries.add(it2) }

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