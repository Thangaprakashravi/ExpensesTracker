package com.example.myexpensestracker.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myexpensestracker.R
import com.example.myexpensestracker.Utilis
import com.example.myexpensestracker.data.ExpenseDataBase
import com.example.myexpensestracker.data.dao.ExpenseDao
import com.example.myexpensestracker.data.model.ExpenseEntity

class HomeViewModel(dao: ExpenseDao): ViewModel() {
    val expenses = dao.getAllExpenses()


    fun  getBalance(list:List<ExpenseEntity>):String{
        var balance= 0.0
       for (expense in list){
           if(expense.type== "Income"){
               balance -=expense.amount
           }else{
               balance -=expense.amount
           }
       }
        return "$ ${balance}"
    }

    fun  getTotalExpense(list:List<ExpenseEntity>):String{
        var total= 0.0
        list.forEach{
           for (expsense in list){
               total += expsense.amount
           }
        }
        return "$ ${total}"
    }


    fun  getTotalIncome(list:List<ExpenseEntity>):String{
        var totalIncome= 0.0
        for (expenses in list){
            if(expenses.type == "Income"){
                totalIncome += expenses.amount
            }
        }
        return "$ ${Utilis.formatToDecimalValue(totalIncome)}"

    }
    fun getIconItem(item:ExpenseEntity):Int{
        return when (item.category) {
            "Netflix" -> R.drawable.netflix
            "Amazon" -> R.drawable.amazon
            "Crunchyroll" -> R.drawable.crunchyroll
            "Hotstar" -> R.drawable.hotstar
            else -> {
                Log.w("getIconItem", "Unknown category: ${item.category}")
                R.drawable.h_m // Replace with a meaningful default icon
            }
        }
    }


}

class HomeViewModelFactory(private val context: Context):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            val dao= ExpenseDataBase.getDatabase(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unkown ViewModel class")
    }
}