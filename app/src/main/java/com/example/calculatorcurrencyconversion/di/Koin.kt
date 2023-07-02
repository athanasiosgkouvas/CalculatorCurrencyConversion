package com.example.calculatorcurrencyconversion.di

import android.util.Log
import com.example.calculatorcurrencyconversion.BuildConfig
import com.example.calculatorcurrencyconversion.data.repository.Repository
import com.example.calculatorcurrencyconversion.data.repository.RepositoryImpl
import com.example.calculatorcurrencyconversion.ui.screens.calculator.CalculatorViewModel
import com.example.calculatorcurrencyconversion.ui.screens.converter.ConverterViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{ CalculatorViewModel() }
    viewModel{ ConverterViewModel() }
}

val repositoryModule = module {

    single<Repository>{ RepositoryImpl(get()) }

    single {
        HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })

                engine {
                    connectTimeout = 60_000
                    socketTimeout = 60_000
                }
            }
            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("HTTP status:", "${response.status.value}")
                }
            }
            install(DefaultRequest) {
                url{
                    protocol = URLProtocol.HTTP
                    host = "data.fixer.io/api/"
                    parameters.append("access_key", BuildConfig.API_KEY)
                }
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }

        }
    }
}