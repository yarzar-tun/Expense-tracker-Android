package com.example.goodexpense.allfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.goodexpense.R
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

        // Observe the specific transaction and update the UI
        if (transactionId != -1) {
            transactionViewModel.getTransactionById(transactionId).observe(viewLifecycleOwner) { transaction ->
                if (transaction != null) {
                    rootView.findViewById<EditText>(R.id.labelInputEdit).setText(transaction.label)
                    rootView.findViewById<EditText>(R.id.amountInputEdit).setText(transaction.amount.toString())
                    // Populate other fields as necessary
                }
            }
        }

        // Handle the update and delete operations here

        return rootView
    }
}
