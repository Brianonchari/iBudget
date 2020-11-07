package com.studycode.mymoneytracker.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.adapters.SourceOfIncomeAdapter
import com.studycode.mymoneytracker.ui.viewmodels.MainViewModel
import com.studycode.mymoneytracker.utils.NumberUtils.getFormattedAmount
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.home_toolbar.*


@AndroidEntryPoint
class DashBoardFragment : Fragment(R.layout.fragment_dashboard) {
    private val viewModel: MainViewModel by viewModels()
    lateinit var incomeAdapter: SourceOfIncomeAdapter
    private var menu: Menu? = null

    companion object {
        private const val TAG = "DashBoardFragment"
    }

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
        setupPieChart()

        viewModel.income.observe(viewLifecycleOwner, Observer {
            incomeAdapter.submitList(it)
        })

        viewModel.totalIncome.observe(viewLifecycleOwner, Observer {
            val totalMonthlyIncome = viewModel.totalIncome.value
            val totalMonthlyBudget = viewModel.totalBudget.value

            Log.d(TAG, "onViewCreated: $totalMonthlyBudget")
            Log.d(TAG, "onViewCreated: $totalMonthlyIncome")
            val spannable1 = SpannableString("Total : ${totalMonthlyIncome?.let { it1 ->  getFormattedAmount(it1) }}")
            spannable1.setSpan(ForegroundColorSpan(Color.BLACK), 0,7,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            netIncomeTv.text = spannable1
            val spannable2 = SpannableString("Total Budget: ${totalMonthlyBudget?.let { it1 ->  getFormattedAmount(it1) }}")
            spannable2.setSpan(ForegroundColorSpan(Color.BLACK), 0,14,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            tvBudget.text = spannable2
            val spannable3 = SpannableString("Net Income : ${totalMonthlyIncome?.let { it1 ->  getFormattedAmount(it1) }}")
            spannable3.setSpan(ForegroundColorSpan(Color.BLACK), 0,12,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            tvIncome.text = spannable3
        })

        add_income.setOnClickListener {
            findNavController().navigate(R.id.addIncomeFragement)
        }
    }

    private fun setupRecyclerview() = incomeRV.apply {
        incomeAdapter = SourceOfIncomeAdapter()
        adapter = incomeAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupPieChart() {
        viewModel.summary.observe(viewLifecycleOwner, Observer {
            val totalMonthlyIncome = viewModel.totalIncome.value
            val totalMonthlyBudget = viewModel.totalBudget.value
            val totalDebts = viewModel.totalDebts.value
            val entries: ArrayList<PieEntry> = ArrayList()
            val colors = java.util.ArrayList<Int>()
            colors.add(Color.YELLOW)
            colors.add(Color.GREEN)
            colors.add(R.color.purple)
            colors.add(R.color.pink)

            totalMonthlyIncome?.let { it1 -> PieEntry(it1, "Total Monthly Income") }
                ?.let { it2 -> entries.add(it2) }
            totalMonthlyBudget?.let { it1 -> PieEntry(it1, "Total Budget") }
                ?.let { it2 ->
                    entries.add(it2)
                }
            totalDebts?.let { it1 -> PieEntry(it1, "Total Debts") }?.let { it2 -> entries.add(it2) }

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