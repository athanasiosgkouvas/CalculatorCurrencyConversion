package com.example.calculatorcurrencyconversion.data.repository

import com.example.calculatorcurrencyconversion.data.models.SymbolsResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get

interface Repository{
    suspend fun getSymbols(): SymbolsResponse
}

class RepositoryImpl(private val httpClient: HttpClient): Repository{
    override suspend fun getSymbols(): SymbolsResponse = httpClient.get("symbols")
}
