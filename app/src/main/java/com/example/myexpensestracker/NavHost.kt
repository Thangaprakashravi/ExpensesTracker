package com.example.myexpensestracker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavHostScreen(){

    val navController= rememberNavController()
    
    NavHost(navController = navController, startDestination = "/home" ){
        composable(route="/home"){
            HomeScreen(navController)
        }
        composable(route= "/add"){
            AddExpenses(navController)
        }
    }
}