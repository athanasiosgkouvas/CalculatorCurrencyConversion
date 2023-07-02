package com.example.calculatorcurrencyconversion.ui.screens.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculatorcurrencyconversion.ui.components.CalculatorButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun Calculator(
    padding: PaddingValues,
    viewModel: CalculatorViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .align(Alignment.TopCenter)
        ) {
            val listState = rememberLazyListState()
            LazyColumn(verticalArrangement = Arrangement.spacedBy(15.dp), state = listState){
                items(state.history){ item ->
                    Text(text = item)
                }
            }
            Text(
                text = state.displayed,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CalculatorButton("AC", MaterialTheme.colorScheme.secondary) {

                }
                CalculatorButton("Del", MaterialTheme.colorScheme.secondary) {

                }
                CalculatorButton("%", MaterialTheme.colorScheme.secondary) {

                }
                CalculatorButton("/", MaterialTheme.colorScheme.secondary) {

                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CalculatorButton("7") {

                }
                CalculatorButton("8") {

                }
                CalculatorButton("9") {

                }
                CalculatorButton("X", MaterialTheme.colorScheme.secondary) {

                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CalculatorButton("4") {

                }
                CalculatorButton("5") {

                }
                CalculatorButton("6") {

                }
                CalculatorButton("+", MaterialTheme.colorScheme.secondary) {

                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CalculatorButton("1") {

                }
                CalculatorButton("2") {

                }
                CalculatorButton("3") {

                }
                CalculatorButton("-", MaterialTheme.colorScheme.secondary) {

                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CalculatorButton("( )") {

                }
                CalculatorButton("0") {

                }
                CalculatorButton(".") {

                }
                CalculatorButton("=", MaterialTheme.colorScheme.inversePrimary) {

                }
            }
        }
    }

}