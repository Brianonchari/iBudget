package com.studycode.mymoneytracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.db.models.MyDebts
import kotlinx.android.synthetic.main.item_debts.view.*

class DebtsRecyclerAdapter : RecyclerView.Adapter<DebtsRecyclerAdapter.DebtViewHolder>() {
    inner class DebtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    val diffCallBack = object : DiffUtil.ItemCallback<MyDebts>() {
        override fun areItemsTheSame(oldItem: MyDebts, newItem: MyDebts): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MyDebts, newItem: MyDebts): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    val differ = AsyncListDiffer(this, diffCallBack)
    fun submitList(list: List<MyDebts>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtViewHolder {
        return DebtViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_debts,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: DebtViewHolder, position: Int) {
        val debts = differ.currentList[position]
        holder.itemView.apply {
            payee_name.text ="${debts.payee}"
            payee_amount.text = "Amount :${debts.amount}"
        }
    }
}