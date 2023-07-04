package com.example.calculatorcurrencyconversion.data.models

import kotlinx.serialization.Serializable

@Serializable
data class LatestResponse(val success: Boolean, val base: String, val rates: Map<String, Double>)
