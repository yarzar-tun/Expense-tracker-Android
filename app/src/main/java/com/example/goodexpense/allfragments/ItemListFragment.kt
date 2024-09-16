package com.example.goodexpense.allfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goodexpense.R
import com.example.goodexpense.data.Transaction
import com.example.goodexpense.data.TransactionViewModel
import com.example.goodexpense.allfragments.TransactionClickListener



class ItemListFragment : Fragment(), TransactionClickListener {

    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var recyclerView: RecyclerView

    private var transactions: ArrayList<Transaction> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_item_list, container, false)

        recyclerView = rootView.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        transactionViewModel.allTransactions.observe(viewLifecycleOwner) { transactionList ->
            transactions = ArrayList(transactionList)
            transactionAdapter = TransactionAdapter(transactions, this) // Pass the fragment as the listener
            recyclerView.adapter = transactionAdapter

            updateDashboard(rootView)
        }

        return rootView
    }

    override fun onTransactionClick(transactionId: Int) {
        val bundle = Bundle().apply {
            putInt("transactionId", transactionId)
        }
        findNavController().navigate(R.id.action_itemListFragment_to_itemDeleteFragment, bundle)
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
