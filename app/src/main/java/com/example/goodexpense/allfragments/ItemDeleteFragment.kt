package com.example.goodexpense.allfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.goodexpense.R
import com.example.goodexpense.data.Transaction
import com.example.goodexpense.data.TransactionViewModel

class ItemDeleteFragment : Fragment() {

    private lateinit var transactionViewModel: TransactionViewModel
    private var transactionId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            transactionId = it.getInt("transactionId", -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_item_delete, container, false)

        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        // Initialize UI components
        val labelInputEdit: EditText = rootView.findViewById(R.id.labelInputEdit)
        val amountInputEdit: EditText = rootView.findViewById(R.id.amountInputEdit)
        val descriptionInputEdit: EditText = rootView.findViewById(R.id.descriptionInputEdit)
        val calendarDateEdit: EditText = rootView.findViewById(R.id.calendarDateEdit)
        val updateButton: Button = rootView.findViewById(R.id.updateBtnEdit)
        val deleteButton: Button = rootView.findViewById(R.id.deleteBtnEdit)

        // Observe the specific transaction and update the UI
        if (transactionId != -1) {
            transactionViewModel.getTransactionById(transactionId).observe(viewLifecycleOwner) { transaction ->
                if (transaction != null) {
                    labelInputEdit.setText(transaction.label)
                    amountInputEdit.setText(transaction.amount.toString())
                    descriptionInputEdit.setText(transaction.description)
                    calendarDateEdit.setText(transaction.date)
                    // Set the RadioButton based on the transaction type
                    val radioGroup = rootView.findViewById<RadioGroup>(R.id.radioGroupEdit)
                    if (transaction.type == "Income") {
                        radioGroup.check(R.id.incomeEdit)
                    } else {
                        radioGroup.check(R.id.expenseEdit)
                    }
                }
            }
        }

        updateButton.setOnClickListener {
            val label = labelInputEdit.text.toString()
            val amount = amountInputEdit.text.toString().toDoubleOrNull() ?: 0.0
            val description = descriptionInputEdit.text.toString()
            val date = calendarDateEdit.text.toString()
            val type = if (rootView.findViewById<RadioButton>(R.id.incomeEdit).isChecked) "Income" else "Expense"

            if (transactionId != -1) {
                val updatedTransaction = Transaction(
                    id = transactionId,
                    label = label,
                    amount = amount,
                    description = description,
                    type = type,
                    date = date
                    // Include other fields if necessary
                )
                transactionViewModel.update(updatedTransaction)
                // Optionally navigate back or show a confirmation message
                findNavController().popBackStack()
            }
        }
        deleteButton.setOnClickListener {
            if (transactionId != -1) {
                transactionViewModel.deleteTransaction(transactionId)
                // Optionally navigate back or show a confirmation message
                findNavController().popBackStack()
            }
        }


        return rootView
    }
}
