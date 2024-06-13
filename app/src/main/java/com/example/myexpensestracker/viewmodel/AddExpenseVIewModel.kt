package com.example.myexpensestracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myexpensestracker.data.ExpenseDataBase
import com.example.myexpensestracker.data.dao.ExpenseDao
import com.example.myexpensestracker.data.model.ExpenseEntity
import java.lang.Exception

class AddExpenseViewModel(val dao: ExpenseDao): ViewModel() {

    suspend fun addExpense(expenseEntity: ExpenseEntity): Boolean{
        return try {
            dao.insertExpense(expenseEntity)
            true
        }catch(ex:Throwable){
            false

        }
    }
}

class AddExpensesViewModelFactory(private val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExpenseViewModel::class.java)){
            val dao= ExpenseDataBase.getDatabase(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return AddExpenseViewModel(dao) as T
        }
        throw IllegalArgumentException("Unkown ViewModel class")
    }
}