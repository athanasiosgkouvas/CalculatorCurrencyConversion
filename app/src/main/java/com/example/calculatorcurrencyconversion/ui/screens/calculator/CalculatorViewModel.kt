package com.example.calculatorcurrencyconversion.ui.screens.calculator

import com.example.calculatorcurrencyconversion.ui.base.BaseViewModel
import com.example.calculatorcurrencyconversion.ui.base.UiEffect
import com.example.calculatorcurrencyconversion.ui.base.UiEvent
import com.example.calculatorcurrencyconversion.ui.base.UiState

interface CalculatorContract {
    sealed interface Event : UiEvent {
        data class NumberClicked(val number: String) : Event
        data class OperatorClicked(val operator: Operator) : Event
        object DeleteClicked : Event
        object ClearClicked : Event
        object EqualsClicked : Event
    }

    data class State(
        val number1: String = "",
        val number2: String = "",
        val operator: Operator? = null,
        val result: String = "",
        val displayed: String = ""
    ) : UiState

    sealed interface Effect : UiEffect
}

sealed class Operator(val symbol: String) {
    object Plus : Operator("+")
    object Minus : Operator("-")
    object Multiply : Operator("x")
    object Divide : Operator("/")
    object Percent : Operator("%")
}

class CalculatorViewModel :
    BaseViewModel<CalculatorContract.Event, CalculatorContract.State, CalculatorContract.Effect>() {

    override fun createInitialState(): CalculatorContract.State = CalculatorContract.State()

    override fun handleEvent(event: CalculatorContract.Event) {
        when (event) {
            CalculatorContract.Event.ClearClicked -> {
                setState {
                    copy(
                        number1 = "",
                        number2 = "",
                        operator = null,
                        displayed = "",
                        result = ""
                    )
                }
                updateDisplayed("", "", null, "")
            }

            CalculatorContract.Event.DeleteClicked -> {
                setState { copy(result = "", displayed = "") }
                if (currentState.number2.isNotEmpty()) {
                    if (currentState.number2.length == 1) {
                        updateDisplayed(currentState.number1, "", currentState.operator, "")
                        setState { copy(number2 = "") }
                    } else {
                        updateDisplayed(
                            currentState.number1,
                            currentState.number2.take(currentState.number2.length - 1),
                            currentState.operator,
                            ""
                        )
                        setState { copy(number2 = currentState.number2.take(currentState.number2.length - 1)) }
                    }
                } else if (currentState.operator != null && currentState.number1.isNotEmpty() && currentState.number2.isEmpty()) {
                    updateDisplayed(currentState.number1, "", null, "")
                    setState { copy(operator = null) }
                } else if (currentState.number1.isNotEmpty()) {
                    if (currentState.number1.length == 1) {
                        updateDisplayed("", "", null, "")
                        setState { copy(number1 = "") }
                    } else {
                        updateDisplayed(
                            currentState.number1.take(currentState.number1.length - 1),
                            currentState.number2,
                            currentState.operator,
                            ""
                        )
                        setState { copy(number1 = currentState.number1.take(currentState.number1.length - 1)) }
                    }

                }
            }

            CalculatorContract.Event.EqualsClicked -> {
                calculateResult()
            }

            is CalculatorContract.Event.NumberClicked -> {
                setState { copy(result = "") }
                if (currentState.operator == null) {
                    if (event.number != "." || (currentState.number1.contains(".")
                            .not() && currentState.number1.isNotEmpty())
                    ) {
                        updateDisplayed(
                            currentState.number1 + event.number,
                            currentState.number2,
                            null,
                            ""
                        )
                        setState { copy(number1 = currentState.number1 + event.number) }
                    }
                } else {
                    if (event.number != "." || (currentState.number2.contains(".")
                            .not() && currentState.number1.isNotEmpty())
                    ) {
                        updateDisplayed(
                            currentState.number1,
                            currentState.number2 + event.number,
                            currentState.operator,
                            ""
                        )
                        setState { copy(number2 = currentState.number2 + event.number) }
                    }
                }
            }

            is CalculatorContract.Event.OperatorClicked -> {
                if(currentState.result.isNotEmpty()){
                    setState { copy(number1 = currentState.result) }
                }
                setState { copy(result = "") }
                if (currentState.number1.isNotEmpty() && currentState.number2.isEmpty()) {
                    if (event.operator is Operator.Percent) {
                        calculateResult(true)
                    } else {
                        setState { copy(operator = event.operator) }
                        updateDisplayed(
                            currentState.number1,
                            currentState.number2,
                            event.operator,
                            ""
                        )
                    }
                }
            }
        }
    }

    private fun updateDisplayed(
        number1: String,
        number2: String,
        operator: Operator?,
        result: String
    ) {
        if(number1.isEmpty() && number2.isEmpty() && operator == null && result.isEmpty()){
            setState { copy(displayed = "") }
        }else{
            setState { copy(displayed = "$number1 ${operator?.symbol ?: ""} $number2 $result") }
        }
    }

    private fun calculateResult(percent: Boolean = false) {
        if (currentState.number1.isEmpty()) return
        if (currentState.number2.isEmpty()) {
            if(percent.not()) return
            val result = currentState.number1.toDouble() / 100.0
            setState {
                copy(
                    number1 = "",
                    number2 = "",
                    operator = null,
                    result = result.toString()
                )
            }
            updateDisplayed("", "", null, result.toString())
        } else {
            val n1 = currentState.number1.toDouble()
            val n2 = currentState.number2.toDouble()
            var result: Double = n1
            when (currentState.operator) {
                Operator.Divide -> {
                    if (n2 != 0.0) {
                        result = n1 / n2
                    }
                }

                Operator.Minus -> {
                    result = n1 - n2
                }

                Operator.Multiply -> {
                    result = n1 * n2
                }

                Operator.Plus -> {
                    result = n1 + n2
                }

                else -> {}// do nothing
            }

            if (percent) {
                result /= 100.0
            }

            setState {
                copy(
                    number1 = "",
                    number2 = "",
                    operator = null,
                    result = result.toString()
                )
            }
            updateDisplayed("", "", null, result.toString())

        }
    }
}