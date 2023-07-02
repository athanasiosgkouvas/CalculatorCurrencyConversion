package com.example.calculatorcurrencyconversion.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SymbolsResponse(
    val success: Boolean,
    val symbols: Map<String, String>
)