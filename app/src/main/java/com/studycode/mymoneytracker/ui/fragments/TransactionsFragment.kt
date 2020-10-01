package com.studycode.mymoneytracker.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.adapters.TransactionsRecyclerAapter
import com.studycode.mymoneytracker.db.models.Transactions
import com.studycode.mymoneytracker.ui.viewmodels.MainViewModel
import com.studycode.mymoneytracker.utils.CustomMarkerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_transactions.*
import java.util.ArrayList

@AndroidEntryPoint
class TransactionsFragment : Fragment(R.layout.fragment_transactions) {
    private val viewModel: MainViewModel by viewModels()
    lateinit var transactionsAdapter: TransactionsRecyclerAapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupLineChart()
        viewModel.transactions.observe(viewLifecycleOwner, Observer {
            transactionsAdapter.submitList(it)
        })
    }

    private fun setupLineChart() {
        viewModel._transactions.observe(viewLifecycleOwner, Observer {
            it.let {
                val transactions =it.indices.map { i -> Entry(i.toFloat(), it[i].trasactionAmount) }
                val lineDataSet =LineDataSet(transactions, "Transactions Over Time ").apply {
                    valueTextColor = Color.BLACK
                    color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
                }

                lineDataSet.setDrawFilled(true)
                lineDataSet.fillDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.fill_drawable)
                lineChart.data = LineData(lineDataSet)
                lineChart.marker = CustomMarkerView(it.reversed(), requireContext(), R.layout.marker_view)
                lineChart.moveViewToX(transactions.size.toFloat())
                lineChart.invalidate()
            }
        })

        // set data
        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = false
        lineChart.setPinchZoom(true)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.animateXY(300,300)
        lineChart.setDrawBorders(false)
        lineChart.setDrawGridBackground(false)
        lineChart.isAutoScaleMinMaxEnabled = false
        lineChart.setTouchEnabled(true)
        lineChart.xAxis.setDrawLabels(false)
        lineChart.axisRight.setDrawLabels(false)


    }

    fun setupRecyclerView() = transactionsRv.apply {
        transactionsAdapter = TransactionsRecyclerAapter()
        adapter = transactionsAdapter
        isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(requireContext())
    }

}