package com.example.calculatorcurrencyconversion.tools

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

fun <T> T.toFlow(): StateFlow<T> =
    MutableStateFlow(this).asStateFlow()