package com.example.calculatorcurrencyconversion.ui.screens.converter

import android.content.Context
import com.example.calculatorcurrencyconversion.data.models.SymbolsResponse
import com.example.calculatorcurrencyconversion.data.repository.Repository
import com.example.calculatorcurrencyconversion.tools.CoroutineTestRule
import com.example.calculatorcurrencyconversion.tools.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub

class ConverterViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private lateinit var repository: Repository

    @Mock
    private lateinit var context: Context

    private lateinit var viewModel: ConverterViewModel

    private val initialState = ConverterContract.State()

    @Before
    fun setUp() {
        repository = Mockito.spy(Repository::class.java)
        MockitoAnnotations.openMocks(this)

        viewModel = ConverterViewModel()

    }

    @Test
    fun `GIVEN getSymbols success WHEN viewModel init then state is updated`() = coroutineRule.runTest {

        Mockito.`when`(repository.getSymbols()).thenReturn(symbolsSuccessResponse)
        getSymbolsInterceptor(symbolsSuccessResponse)
        viewModel.setEvent(ConverterContract.Event.Init)



        assertEquals(
            initialState.copy(
                currencies = symbolsSuccessResponse.supported_codes,
                showError = false,
                baseSymbol = "EUR",
                targetSymbol = ""
            ),
            viewModel.uiState.value
        )
    }


    private fun getSymbolsInterceptor(response: SymbolsResponse) {
        repository.stub {
            onBlocking {
                getSymbols()
            }.doReturn(
                response
            )
        }
    }

    private val symbolsSuccessResponse = SymbolsResponse(
        result = "success", supported_codes = listOf(
            listOf("EUR", "EURO")
        )
    )

    private val symbolsErrorResponse = SymbolsResponse("error", emptyList())
}