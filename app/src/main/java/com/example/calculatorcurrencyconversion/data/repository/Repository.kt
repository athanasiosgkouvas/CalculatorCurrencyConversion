package com.example.calculatorcurrencyconversion.data.repository

import com.example.calculatorcurrencyconversion.BuildConfig
import com.example.calculatorcurrencyconversion.data.models.ConvertResponse
import com.example.calculatorcurrencyconversion.data.models.SymbolsResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get

interface Repository{
    suspend fun getSymbols(): SymbolsResponse
    suspend fun convert(from: String, to: String, amount: Double): ConvertResponse
}

class RepositoryImpl(private val httpClient: HttpClient, private val baseUrl: String): Repository{
    override suspend fun getSymbols(): SymbolsResponse =  httpClient.get("$baseUrl/${BuildConfig.API_KEY}/codes")

    override suspend fun convert(from: String, to: String, amount: Double): ConvertResponse = httpClient.get("$baseUrl/${BuildConfig.API_KEY}/pair/$from/$to/$amount")
}
