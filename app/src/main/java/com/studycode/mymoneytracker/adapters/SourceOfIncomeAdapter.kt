package com.studycode.mymoneytracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.db.models.Income
import kotlinx.android.synthetic.main.income_item.view.*

class SourceOfIncomeAdapter : RecyclerView.Adapter<SourceOfIncomeAdapter.IncomeViewHolder>() {
    inner class IncomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    val diffCallBack = object : DiffUtil.ItemCallback<Income>() {
        override fun areItemsTheSame(oldItem: Income, newItem: Income): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Income, newItem: Income): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    val differ = AsyncListDiffer(this, diffCallBack)
    fun submitList(list: List<Income>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeViewHolder {
        return IncomeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.income_item,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        val income = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(income.receiptImg).into(receipt)
            income_name.text ="${income.source}"
            income_amount.text = "Amount :${income.amount}"
        }
    }
}