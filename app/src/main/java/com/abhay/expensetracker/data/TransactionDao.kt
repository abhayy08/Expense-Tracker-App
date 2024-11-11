package com.abhay.expensetracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.abhay.expensetracker.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Upsert
    suspend fun insertTransaction(transaction: Transaction)

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("SELECT * FROM all_transactions ORDER BY createdAt DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Query("SELECT * FROM all_transactions WHERE transactionType == :transactionType ORDER BY createdAt DESC")
    fun getAllSingleTransaction(transactionType: String): Flow<List<Transaction>>

    @Query("SELECT * FROM all_transactions WHERE id == :id")
    fun getTransactionById(id: Int): Flow<Transaction>

    @Query("DELETE FROM all_transactions WHERE id = :id")
    suspend fun deleteTransactionById(id: Int)

}