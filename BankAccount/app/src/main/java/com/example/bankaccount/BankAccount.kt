package com.example.bankaccount

class BankAccount (var accountholder : String, var balance: Double) {
    private val transactionHiatory = mutableListOf<String>()

    fun deposit(amount:Double){
        balance += amount
        transactionHiatory.add("$accountholder deposited $ $amount ")

    }

    fun withdraw(amount: Double){
        if (amount<= balance){
            balance-= amount
            transactionHiatory.add("$accountholder withdraw $ $amount")
        }else{
            println("you don't have fund to withdraw $ $amount")
        }


    }

    fun trancation(){
        println("transaction history for $accountholder")
        for (transaction in transactionHiatory){
            println(transaction)
        }

    }
}