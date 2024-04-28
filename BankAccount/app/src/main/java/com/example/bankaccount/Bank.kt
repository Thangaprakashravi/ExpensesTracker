package com.example.bankaccount

fun main(){
val bellybucher = BankAccount("BellyBucher" , 1400.00)
    bellybucher.deposit(400.00)
    bellybucher.withdraw(300.0)
    bellybucher.deposit(5000.00)
    bellybucher.deposit(3000.0)
    bellybucher.withdraw(4000.29)
    bellybucher.trancation()
    println("${bellybucher.accountholder}'s balance is  ${bellybucher.balance}")
}