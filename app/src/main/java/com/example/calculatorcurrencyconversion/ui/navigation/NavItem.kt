package com.example.calculatorcurrencyconversion.ui.navigation

import com.example.calculatorcurrencyconversion.R

sealed class NavItem(
    val route: String,
    val iconResource: Int,
    val label: String
) {
    object Calculator : NavItem("splash", R.drawable.calculator, "Calculator" )

    object Converter : NavItem("home", R.drawable.converter, "Converter")
}