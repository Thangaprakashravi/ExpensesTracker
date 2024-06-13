package com.example.myexpensestracker

import java.text.SimpleDateFormat
import java.util.Locale

object Utilis {
    fun formatDateToHumanRedableForm(dateInMillis: Long): String {
        val dateFormatter = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault())
        return dateFormatter.format(dateInMillis)
    }
    fun formatToDecimalValue(d: Double): String{
        return String.format("%.2f", d)
    }
}