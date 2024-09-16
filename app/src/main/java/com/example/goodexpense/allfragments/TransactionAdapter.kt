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
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import com.example.goodexpense.allfragments.TransactionClickListener



class TransactionAdapter(
    private val transactions: ArrayList<Transaction>,
    private val listener: TransactionClickListener // Add this parameter
) : RecyclerView.Adapter<TransactionAdapter.TransactionHolder>() {

    class TransactionHolder(view: View) : RecyclerView.ViewHolder(view) {
        val label: TextView = view.findViewById(R.id.label)
        val amount: TextView = view.findViewById(R.id.amount)
        // Add a reference to the root view
        val rootView: View = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_layout, parent, false)
        return TransactionHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        val transaction = transactions[position]
        val context: Context = holder.amount.context

        // Set the text and color based on the transaction type
        if (transaction.type.equals("Income", ignoreCase = true)) {
            holder.amount.text = "+ $%.2f".format(transaction.amount)
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.green))
        } else {
            holder.amount.text = "- $%.2f".format(transaction.amount)
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red))
        }
        holder.label.text = transaction.label

        // Set a click listener to handle item clicks
        holder.rootView.setOnClickListener {
            listener.onTransactionClick(transaction.id) // Use the listener to pass the transaction ID
        }
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}

