package com.example.calculatorcurrencyconversion.data.repository

import com.example.calculatorcurrencyconversion.data.models.ConvertResponse
import com.example.calculatorcurrencyconversion.data.models.LatestResponse
import com.example.calculatorcurrencyconversion.data.models.SymbolsResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header

interface Repository{
    suspend fun getSymbols(): SymbolsResponse
    suspend fun convert(from: String, to: String, amount: Double): ConvertResponse
    suspend fun latest(base: String?, symbols: List<String>): LatestResponse
}

class RepositoryImpl(private val httpClient: HttpClient): Repository{
    override suspend fun getSymbols(): SymbolsResponse = httpClient.get("symbols")
    override suspend fun convert(from: String, to: String, amount: Double): ConvertResponse = httpClient.get("convert"){
        header("from", from)
        header("to", to)
        header("amount", amount)
    }

    override suspend fun latest(base: String?, symbols: List<String>): LatestResponse = httpClient.get("latest"){
        base?.let { header("base", base) }
        var symbolsHeader = ""
        symbols.forEach { symbolsHeader += ", $it" }
        header("symbols", symbolsHeader)
    }
}
