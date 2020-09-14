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
import com.studycode.mymoneytracker.ui.viewmodels.MainViewModel
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
//            val transactions = viewModel.transactions
//            val entries:ArrayList<Entry> = ArrayList()
//
//            entries.add(Entry(0f, transactions.value.to))

            it.let {
                val transactions =it.indices.map { i -> Entry(i.toFloat(), it[i].trasactionAmount) }
                val lineDataSet =LineDataSet(transactions, "Transactions Over Time ").apply {
                    valueTextColor = Color.BLACK
                    color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
                }

                lineChart.data = LineData(lineDataSet)
                lineChart.invalidate()
            }


        })



//        val set1: LineDataSet
//        set1 = LineDataSet(yVals, "DataSet 1")

        // set1.fillAlpha = 110
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        // set1.enableDashedLine(5f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
//        set1.color = Color.BLUE
//        set1.setCircleColor(Color.BLUE)
//        set1.lineWidth = 1f
//        set1.circleRadius = 3f
//        set1.setDrawCircleHole(false)
//        set1.valueTextSize = 0f
//        set1.setDrawFilled(false)

//        val dataSets = ArrayList<ILineDataSet>()
//        dataSets.add(set1)
//        val data = LineData(dataSets)

        // set data
        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = false
        lineChart.setPinchZoom(true)
        lineChart.xAxis.enableGridDashedLine(5f, 5f, 0f)
        lineChart.axisRight.enableGridDashedLine(5f, 5f, 0f)
        lineChart.axisLeft.enableGridDashedLine(5f, 5f, 0f)
        //lineChart.setDrawGridBackground()
        lineChart.xAxis.labelCount = 11
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM


    }

    fun setupRecyclerView() = transactionsRv.apply {
        transactionsAdapter = TransactionsRecyclerAapter()
        adapter = transactionsAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

}