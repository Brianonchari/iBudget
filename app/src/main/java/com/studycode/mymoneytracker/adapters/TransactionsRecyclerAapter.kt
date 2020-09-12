package com.studycode.mymoneytracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.db.models.Transactions
import kotlinx.android.synthetic.main.item_transactions.view.*

class TransactionsRecyclerAapter :RecyclerView.Adapter<TransactionsRecyclerAapter.TransactionsViewHolder>(){
    inner class TransactionsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    var diffCallBack = object :DiffUtil.ItemCallback<Transactions>(){
        override fun areItemsTheSame(oldItem: Transactions, newItem: Transactions): Boolean {
            return  oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Transactions, newItem: Transactions): Boolean {
            return oldItem.hashCode()==newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallBack)
    fun submitList(list: List<Transactions>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
       return TransactionsViewHolder(
           LayoutInflater.from(parent.context)
               .inflate(R.layout.item_transactions,
                   parent,
                   false
               )
       )
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
      val transactions = differ.currentList[position]
        holder.itemView.apply {
            transaction_details.text = "${transactions.transactionName} \n ${transactions.date}"
            transacted_amount.text = "${transactions.trasactionAmount}"
        }
    }

}