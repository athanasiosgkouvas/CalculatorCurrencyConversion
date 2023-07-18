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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .align(Alignment.TopCenter)
        ) {
            Text(
                text = state.displayed,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.CenterEnd),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CalculatorButton("AC", MaterialTheme.colorScheme.secondary) {
                    viewModel.setEvent(CalculatorContract.Event.ClearClicked)
                }
                CalculatorButton("Del", MaterialTheme.colorScheme.secondary) {
                    viewModel.setEvent(CalculatorContract.Event.DeleteClicked)
                }
                CalculatorButton("%", MaterialTheme.colorScheme.secondary) {
                    viewModel.setEvent(CalculatorContract.Event.OperatorClicked(Operator.Percent))
                }
                CalculatorButton("/", MaterialTheme.colorScheme.secondary) {
                    viewModel.setEvent(CalculatorContract.Event.OperatorClicked(Operator.Divide))
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CalculatorButton("7") {
                    viewModel.setEvent(CalculatorContract.Event.NumberClicked("7"))
                }
                CalculatorButton("8") {
                    viewModel.setEvent(CalculatorContract.Event.NumberClicked("8"))
                }
                CalculatorButton("9") {
                    viewModel.setEvent(CalculatorContract.Event.NumberClicked("9"))
                }
                CalculatorButton("x", MaterialTheme.colorScheme.secondary) {
                    viewModel.setEvent(CalculatorContract.Event.OperatorClicked(Operator.Multiply))
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CalculatorButton("4") {
                    viewModel.setEvent(CalculatorContract.Event.NumberClicked("4"))
                }
                CalculatorButton("5") {
                    viewModel.setEvent(CalculatorContract.Event.NumberClicked("5"))
                }
                CalculatorButton("6") {
                    viewModel.setEvent(CalculatorContract.Event.NumberClicked("6"))
                }
                CalculatorButton("+", MaterialTheme.colorScheme.secondary) {
                    viewModel.setEvent(CalculatorContract.Event.OperatorClicked(Operator.Plus))
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CalculatorButton("1") {
                    viewModel.setEvent(CalculatorContract.Event.NumberClicked("1"))
                }
                CalculatorButton("2") {
                    viewModel.setEvent(CalculatorContract.Event.NumberClicked("2"))
                }
                CalculatorButton("3") {
                    viewModel.setEvent(CalculatorContract.Event.NumberClicked("3"))
                }
                CalculatorButton("-", MaterialTheme.colorScheme.secondary) {
                    viewModel.setEvent(CalculatorContract.Event.OperatorClicked(Operator.Minus))
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                CalculatorButton("0", isDoubleWidth = true) {
                    viewModel.setEvent(CalculatorContract.Event.NumberClicked("0"))
                }
                CalculatorButton(".") {
                    viewModel.setEvent(CalculatorContract.Event.NumberClicked("."))
                }
                CalculatorButton("=", MaterialTheme.colorScheme.inversePrimary) {
                    viewModel.setEvent(CalculatorContract.Event.EqualsClicked)
                }
            }
        }
    }

}