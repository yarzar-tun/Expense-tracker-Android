package com.example.goodexpense.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction)

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE id = :id")
    fun getTransactionById(id: Int): LiveData<Transaction?>
}
