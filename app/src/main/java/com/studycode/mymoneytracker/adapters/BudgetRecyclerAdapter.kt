package com.studycode.mymoneytracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.studycode.mymoneytracker.R
import com.studycode.mymoneytracker.db.models.Budget
import com.studycode.mymoneytracker.ui.fragments.CreateBudgetFragment
import kotlinx.android.synthetic.main.item_budget.view.*

class BudgetRecyclerAdapter : RecyclerView.Adapter<BudgetRecyclerAdapter.BudgetViewHolder>() {

    private var onItemClickListener:((Budget)->Unit)? = null
    inner class BudgetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    val diffCallBack = object : DiffUtil.ItemCallback<Budget>() {
        override fun areItemsTheSame(oldItem: Budget, newItem: Budget): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Budget, newItem: Budget): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    val differ = AsyncListDiffer(this, diffCallBack)
    fun submitList(list: List<Budget>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        return BudgetViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_budget,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        val budget = differ.currentList[position]
        holder.itemView.apply {
            budgetTv.text ="${budget.category}"
            tvPlannedBudget.text = "Amount :${budget.amount} "
            tvRemainingBudget.text = "Balance :${budget.balance}"
            setOnClickListener {
                onItemClickListener?.let { it(budget) }
            }
        }

//        holder.itemView.setOnClickListener {v->
//
//            v.findNavController().navigate(R.id.createTransactionFragment)
//        }
    }
    fun setOnItemClickListener(listener:(Budget) -> Unit){
        onItemClickListener = listener
    }
}