package com.example.goodexpense.data

import android.content.Context
import androidx.room.*
import com.example.goodexpense.data.DateTypeConverter // Import your TypeConverter

@Database(entities = [Transaction::class], version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class) // Add TypeConverters annotation here
abstract class TransactionDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: TransactionDatabase? = null

        fun getDatabase(context: Context): TransactionDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TransactionDatabase::class.java,
                    "transaction_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
