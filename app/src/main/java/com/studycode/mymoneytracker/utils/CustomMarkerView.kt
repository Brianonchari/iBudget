package com.studycode.mymoneytracker.utils

import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.studycode.mymoneytracker.db.models.Transactions
import java.util.*

class CustomMarkerView(
    val transactions: List<Transactions>,
    c: Context,
    layoutId: Int
) : MarkerView(c, layoutId) {

    override fun getOffset(): MPPointF {
        return super.getOffset()
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        if (e == null) {
            return
        }
        val currentTransactionId = e.x.toInt()
        var transaction = transactions[currentTransactionId]
        val calender = Calendar.getInstance().apply {
          transaction.date
        }
    }
}