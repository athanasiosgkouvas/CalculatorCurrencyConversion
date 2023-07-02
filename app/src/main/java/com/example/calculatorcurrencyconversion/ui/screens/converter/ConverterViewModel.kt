package com.example.calculatorcurrencyconversion.ui.screens.converter

import com.example.calculatorcurrencyconversion.data.repository.Repository
import com.example.calculatorcurrencyconversion.ui.base.BaseViewModel
import com.example.calculatorcurrencyconversion.ui.base.UiEffect
import com.example.calculatorcurrencyconversion.ui.base.UiEvent
import com.example.calculatorcurrencyconversion.ui.base.UiState
import org.koin.core.component.inject

interface ConverterContract{
    sealed interface Event: UiEvent{

    }

    data class State(val amount: Double = 0.0): UiState

    sealed interface Effect: UiEffect{

    }
}

class ConverterViewModel: BaseViewModel<ConverterContract.Event, ConverterContract.State, ConverterContract.Effect>(){

    private val repository: Repository by inject()

    init {

    }

    override fun createInitialState(): ConverterContract.State = ConverterContract.State()

    override fun handleEvent(event: ConverterContract.Event) {
        TODO("Not yet implemented")
    }

}