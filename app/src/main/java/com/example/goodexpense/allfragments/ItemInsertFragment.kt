package com.example.goodexpense.allfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.goodexpense.R
import com.example.goodexpense.data.Transaction
import com.example.goodexpense.data.TransactionViewModel
import com.google.android.material.textfield.TextInputEditText

class ItemInsertFragment : Fragment() {

    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_item_insert, container, false)

        // Define the labels for the drop-down
        val labels = listOf("Food", "Transport", "Shopping", "Entertainment", "Bills", "Miscellaneous", "Salary", "Pocket Money")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, labels)

        // Set up AutoCompleteTextView for label input
        val labelDropdown: AutoCompleteTextView = rootView.findViewById(R.id.labelInput)
        labelDropdown.setAdapter(adapter)

        // Initialize ViewModel
        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        // Set up the Add Transaction button click listener
        rootView.findViewById<Button>(R.id.addTransactionBtn).setOnClickListener {
            val type = if (rootView.findViewById<RadioButton>(R.id.expense).isChecked) "Expense" else "Income"
            val date = rootView.findViewById<TextInputEditText>(R.id.calendarDate).text.toString()
            val label = labelDropdown.text.toString()
            val amount = rootView.findViewById<TextInputEditText>(R.id.amountInput).text.toString().toDoubleOrNull() ?: 0.0
            val description = rootView.findViewById<TextInputEditText>(R.id.descriptionInput).text.toString()

            // Create Transaction object
            val transaction = Transaction(
                type = type,
                date = date,
                label = label,
                amount = amount,
                description = description
            )

            // Insert the new transaction into the database via ViewModel
            transactionViewModel.insert(transaction)

            // Provide feedback to the user
            Toast.makeText(requireContext(), "Transaction added successfully", Toast.LENGTH_SHORT).show()

            // Clear the input fields after submission
            rootView.findViewById<TextInputEditText>(R.id.calendarDate).text?.clear()
            labelDropdown.text.clear()
            rootView.findViewById<TextInputEditText>(R.id.amountInput).text?.clear()
            rootView.findViewById<TextInputEditText>(R.id.descriptionInput).text?.clear()
        }

        // Observe transactions if needed to update UI
        transactionViewModel.allTransactions.observe(viewLifecycleOwner) { transactions ->
            // Update UI based on the list of transactions, if necessary
        }

        return rootView
    }
}
