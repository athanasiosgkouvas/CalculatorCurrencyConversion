package com.example.calculatorcurrencyconversion.ui.screens.calculator

import com.example.calculatorcurrencyconversion.ui.base.BaseViewModel
import com.example.calculatorcurrencyconversion.ui.base.UiEffect
import com.example.calculatorcurrencyconversion.ui.base.UiEvent
import com.example.calculatorcurrencyconversion.ui.base.UiState

interface CalculatorContract{
    sealed interface Event: UiEvent{
        data class Add(val number: Double): Event
        data class Subtract(val number: Double): Event
        data class Multiply(val number: Double): Event
        data class Divide(val number: Double): Event
        object Percent: Event
        object Delete: Event
        object Clear: Event
        object Equals: Event
    }

    data class State(
        val amounts: List<Double> = emptyList(),
        val displayed: String = "",
        val history: List<String> = emptyList()
    ): UiState

    sealed interface Effect: UiEffect{

    }
}

class CalculatorViewModel: BaseViewModel<CalculatorContract.Event, CalculatorContract.State, CalculatorContract.Effect>(){

    override fun createInitialState(): CalculatorContract.State = CalculatorContract.State()

    override fun handleEvent(event: CalculatorContract.Event) {
        TODO("Not yet implemented")
    }

}