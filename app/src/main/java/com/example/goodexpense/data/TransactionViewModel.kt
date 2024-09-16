package com.example.goodexpense.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TransactionRepository
    val allTransactions: LiveData<List<Transaction>>

    init {
        val transactionDao = TransactionDatabase.getDatabase(application).transactionDao()
        repository = TransactionRepository(transactionDao)
        allTransactions = repository.allTransactions
    }

    fun insert(transaction: Transaction) = viewModelScope.launch {
        repository.insert(transaction)
    }

    fun getTransactionById(id: Int): LiveData<Transaction?> {
        return repository.getTransactionById(id)
    }
}
