package com.example.calculatorcurrencyconversion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.calculatorcurrencyconversion.ui.navigation.Navigation
import com.example.calculatorcurrencyconversion.ui.theme.CalculatorCurrencyConversionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorCurrencyConversionTheme {
                Navigation()
            }
        }
    }
}