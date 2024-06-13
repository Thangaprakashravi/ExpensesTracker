package com.example.myexpensestracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "expenses_table")

data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,

    val title: String,
    val amount: Double,
    val date: Long,
    val category: String?,
    val type:String

)
