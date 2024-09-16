package com.example.goodexpense.allfragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.goodexpense.R
import com.example.goodexpense.data.Transaction

class TransactionAdapter(private val transaction: ArrayList<Transaction>):
    RecyclerView.Adapter<TransactionAdapter.TransactionHolder>() {

    class TransactionHolder(view: View):RecyclerView.ViewHolder(view){
        val label: TextView = view.findViewById(R.id.label)
        val amount: TextView = view.findViewById(R.id.amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_layout, parent, false)
        return TransactionHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        val transaction:Transaction = transaction[position]
        val context: Context = holder.amount.context

        // Set the text and color based on the transaction type (Income or Expense)
        if (transaction.type.equals("Income", ignoreCase = true)) {
            holder.amount.text = "+ $%.2f".format(transaction.amount) // For income, prefix with '+'
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.green)) // Set green color for income
        } else if (transaction.type.equals("Expense", ignoreCase = true)) {
            holder.amount.text = "- $%.2f".format(transaction.amount) // For expense, prefix with '-'
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red)) // Set red color for expense
        }
        holder.label.text = transaction.label
    }

    override fun getItemCount(): Int {
        return transaction.size
    }

}