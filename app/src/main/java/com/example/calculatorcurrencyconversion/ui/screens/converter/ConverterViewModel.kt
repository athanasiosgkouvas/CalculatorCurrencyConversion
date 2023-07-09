package com.example.calculatorcurrencyconversion.ui.screens.converter

import androidx.lifecycle.viewModelScope
import com.example.calculatorcurrencyconversion.data.repository.Repository
import com.example.calculatorcurrencyconversion.ui.base.BaseViewModel
import com.example.calculatorcurrencyconversion.ui.base.UiEffect
import com.example.calculatorcurrencyconversion.ui.base.UiEvent
import com.example.calculatorcurrencyconversion.ui.base.UiState
import com.example.calculatorcurrencyconversion.util.isSuccess
import kotlinx.coroutines.launch

interface ConverterContract{
    sealed interface Event: UiEvent{
        object Init: Event
        object OnBaseSymbolClicked: Event
        object OnTargetSymbolClicked: Event
        data class OnBaseCurrencySelected(val symbol: String): Event
        data class OnTargetCurrencySelected(val symbol: String): Event
        data class OnBaseAmountChanged(val amount: String): Event
        data class OnTargetAmountChanged(val amount: String): Event
        object CalculateTargetAmount: Event
        object CalculateBaseAmount: Event
    }

    data class State(
        val isLoading: Boolean = false,
        val baseAmount: String = "",
        val targetAmount: String = "",
        val baseExpanded: Boolean = false,
        val baseSymbol: String = "",
        val targetSymbol: String = "",
        val currencies: List<List<String>> = emptyList(),
        val showError: Boolean = false
    ): UiState

    sealed interface Effect: UiEffect{
        object ShowBaseBottomSheet: Effect
        object HideBaseBottomSheet: Effect
        object ShowTargetBottomSheet: Effect
        object HideTargetBottomSheet: Effect
    }
}

class ConverterViewModel(private val repository: Repository): BaseViewModel<ConverterContract.Event, ConverterContract.State, ConverterContract.Effect>(){

    init {
        setEvent(ConverterContract.Event.Init)
    }

    override fun createInitialState(): ConverterContract.State = ConverterContract.State()

    override fun handleEvent(event: ConverterContract.Event) {
        when(event){
            ConverterContract.Event.Init -> {
                setState { copy(isLoading = true) }
                getSymbols()
            }
            ConverterContract.Event.OnBaseSymbolClicked -> {
                setEffect { ConverterContract.Effect.ShowBaseBottomSheet }
            }
            ConverterContract.Event.OnTargetSymbolClicked -> {
                setEffect { ConverterContract.Effect.ShowTargetBottomSheet }
            }
            is ConverterContract.Event.OnBaseCurrencySelected -> {
                setState { copy(baseSymbol = event.symbol) }
                setEffect { ConverterContract.Effect.HideBaseBottomSheet }
            }
            is ConverterContract.Event.OnTargetCurrencySelected -> {
                setState { copy(targetSymbol = event.symbol) }
                setEffect { ConverterContract.Effect.HideTargetBottomSheet }
            }
            is ConverterContract.Event.OnBaseAmountChanged -> {
                setState { copy(baseAmount = event.amount) }
            }
            is ConverterContract.Event.OnTargetAmountChanged -> {
                setState { copy(targetAmount = event.amount) }
            }
            ConverterContract.Event.CalculateTargetAmount -> {
                if(currentState.baseAmount.isEmpty().not()){
                    setState { copy(isLoading = true) }
                    convert(currentState.baseAmount, currentState.baseSymbol, currentState.targetSymbol)
                }
            }
            ConverterContract.Event.CalculateBaseAmount -> {
                if(currentState.targetAmount.isEmpty().not()){
                    setState { copy(isLoading = true) }
                    convert(currentState.targetAmount, currentState.targetSymbol, currentState.baseSymbol)
                }
            }
        }
    }

    private fun getSymbols(){
        viewModelScope.launch {
            try {
               val response = repository.getSymbols()
                if(response.result.isSuccess()){
                    setState {
                        copy(
                            currencies = response.supported_codes,
                            showError = false,
                            baseSymbol = response.supported_codes.flatten().firstOrNull { it == "EUR" }.orEmpty(),
                            targetSymbol = response.supported_codes.flatten().firstOrNull() { it == "USD" }.orEmpty()
                        )
                    }
                }else{
                    setState { copy(showError = true) }
                }
            }catch (e: Exception){
                setState { copy(showError = true) }
            }
            setState { copy(isLoading = false) }
        }
    }
    private fun convert(amount: String, from: String, to: String){
        viewModelScope.launch {
            try {
                val response = repository.convert(from, to, amount.toDouble())
                if(response.result.isSuccess()){
                    setState {
                        copy(
                            baseAmount = if(response.base_code == currentState.baseSymbol) amount else response.conversion_result.toString(),
                            targetAmount = if(response.target_code == currentState.targetSymbol) response.conversion_result.toString() else amount,
                            showError = false,
                        )
                    }
                }else{
                    setState { copy(showError = true) }
                }
            }catch (e: Exception){
                setState { copy(showError = true) }
            }
            setState { copy(isLoading = false) }
        }

    }
}