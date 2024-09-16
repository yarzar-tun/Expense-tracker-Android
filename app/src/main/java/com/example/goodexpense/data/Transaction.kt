package com.example.goodexpense.data

import android.icu.text.SimpleDateFormat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // Auto-generated ID with a default value
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "label") val label: String,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "description") val description: String
)

