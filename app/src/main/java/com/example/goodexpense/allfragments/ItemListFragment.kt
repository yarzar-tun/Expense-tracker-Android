package com.example.goodexpense.allfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goodexpense.R
import com.example.goodexpense.data.Transaction
import com.example.goodexpense.data.TransactionViewModel


class ItemListFragment : Fragment() {

    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var recyclerView: RecyclerView

    // List to hold the transactions
    private var transactions: ArrayList<Transaction> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_item_list, container, false)

        recyclerView = rootView.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the ViewModel
        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        // Observe transactions data
        transactionViewModel.allTransactions.observe(viewLifecycleOwner) { transactionList ->
            transactions = ArrayList(transactionList) // Update the transaction list

            // Create and set the adapter with the list of transactions
            transactionAdapter = TransactionAdapter(transactions)
            recyclerView.adapter = transactionAdapter

            // Update the dashboard
            updateDashboard(rootView)
        }

        return rootView
    }

    private fun updateDashboard(rootView: View) {
        // Filter transactions by type and calculate amounts
        val budgetAmount = transactions.filter { it.type.equals("Income", ignoreCase = true) }
            .map { it.amount }
            .sum()
        val expenseAmount = transactions.filter { it.type.equals("Expense", ignoreCase = true) }
            .map { it.amount }
            .sum()

        // Calculate total amount as the difference between budget and expense
        val totalAmount = budgetAmount - expenseAmount

        // Update the TextViews for balance, income, and expense
        val balance = rootView.findViewById<TextView>(R.id.balance)
        balance.text = "%.2f".format(totalAmount)

        val budget = rootView.findViewById<TextView>(R.id.income)
        budget.text = "%.2f".format(budgetAmount)

        val expense = rootView.findViewById<TextView>(R.id.expense)
        expense.text = "%.2f".format(expenseAmount)
    }

}
