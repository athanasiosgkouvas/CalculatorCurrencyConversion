package com.example.calculatorcurrencyconversion.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ConvertResponse(val success: Boolean, val info: Info, val result: String)

@Serializable
data class Info(val rate: Double)