package com.example.calculatorcurrencyconversion.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ConvertResponse(
    val result: String,
    val base_code: String,
    val target_code: String,
    val conversion_result: Double
)