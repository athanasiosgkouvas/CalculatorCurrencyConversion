package com.example.calculatorcurrencyconversion.ui.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calculatorcurrencyconversion.ui.components.BottomNavBar
import com.example.calculatorcurrencyconversion.ui.screens.calculator.Calculator
import com.example.calculatorcurrencyconversion.ui.screens.converter.Converter


@Composable
fun Navigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->

        NavHost(navController = navController, startDestination = NavItem.Calculator.route) {

            composable(NavItem.Calculator) {
                Calculator(padding)
            }

            composable(NavItem.Converter) {
                Converter(padding)
            }
        }
    }
}

private fun NavGraphBuilder.composable(
    navItem: NavItem,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = navItem.route,
    ) {
        content(it)
    }
}