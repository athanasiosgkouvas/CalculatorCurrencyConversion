package com.example.calculatorcurrencyconversion.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SymbolsResponse(
    val result: String,
    val supported_codes: List<List<String>>
)