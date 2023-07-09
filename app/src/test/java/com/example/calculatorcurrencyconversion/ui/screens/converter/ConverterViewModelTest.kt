package com.example.calculatorcurrencyconversion.ui.screens.converter

import com.example.calculatorcurrencyconversion.data.models.ConvertResponse
import com.example.calculatorcurrencyconversion.data.models.SymbolsResponse
import com.example.calculatorcurrencyconversion.data.repository.Repository
import com.example.calculatorcurrencyconversion.tools.CoroutineTestRule
import com.example.calculatorcurrencyconversion.tools.runFlowTest
import com.example.calculatorcurrencyconversion.tools.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.kotlin.whenever
@OptIn(ExperimentalCoroutinesApi::class)
class ConverterViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @Spy
    private lateinit var repository: Repository

    private lateinit var viewModel: ConverterViewModel

    private val initialState = ConverterContract.State()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        viewModel = ConverterViewModel(repository)

    }

    @Test
    fun `GIVEN getSymbols success WHEN viewModel init THEN state is updated`() =
        coroutineRule.runTest {
            whenever(repository.getSymbols()).thenReturn(symbolsSuccessResponse)
            advanceUntilIdle()

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

    @Test
    fun `GIVEN getSymbols error WHEN viewModel init THEN state showError is true`() =
        coroutineRule.runTest {
            whenever(repository.getSymbols()).thenReturn(symbolsErrorResponse)
            advanceUntilIdle()

            assertEquals(
                initialState.copy(
                    showError = true
                ),
                viewModel.uiState.value
            )
        }

    @Test
    fun `GIVEN getSymbols exception WHEN viewModel THEN then state showError is true`() =
        coroutineRule.runTest {
            whenever(repository.getSymbols()).thenThrow(RuntimeException())
            advanceUntilIdle()

            assertEquals(
                initialState.copy(
                    showError = true
                ),
                viewModel.uiState.value
            )
        }

    @Test
    fun `WHEN viewModel OnBaseSymbolClicked THEN ShowBaseBottomSheet effect is emitted`() =
        coroutineRule.runTest {
            viewModel.setEvent(ConverterContract.Event.OnBaseSymbolClicked)
            advanceUntilIdle()

            viewModel.effect.runFlowTest {
                assertEquals(
                    ConverterContract.Effect.ShowBaseBottomSheet,
                    awaitItem()
                )
            }
        }

    @Test
    fun `WHEN viewModel OnTargetSymbolClicked THEN ShowTargetBottomSheet effect is emitted`() =
        coroutineRule.runTest {
            viewModel.setEvent(ConverterContract.Event.OnTargetSymbolClicked)
            advanceUntilIdle()

            viewModel.effect.runFlowTest {
                assertEquals(
                    ConverterContract.Effect.ShowTargetBottomSheet,
                    awaitItem()
                )
            }
        }

    @Test
    fun `WHEN viewModel OnBaseCurrencySelected THEN HideBaseBottomSheet effect is emitted and baseSymbol is updated`() =
        coroutineRule.runTest {
            whenever(repository.getSymbols()).thenReturn(symbolsEmpty)
            viewModel.setEvent(ConverterContract.Event.OnBaseCurrencySelected("EUR"))

            advanceUntilIdle()

            viewModel.effect.runFlowTest {
                assertEquals(
                    ConverterContract.Effect.HideBaseBottomSheet,
                    awaitItem()
                )
                assertEquals(
                    initialState.copy(baseSymbol = "EUR"),
                    viewModel.uiState.value
                )
            }
        }

    @Test
    fun `WHEN viewModel OnTargetCurrencySelected THEN HideTargetBottomSheet effect is emitted and targetSymbol is updated`() =
        coroutineRule.runTest {
            whenever(repository.getSymbols()).thenReturn(symbolsEmpty)
            viewModel.setEvent(ConverterContract.Event.OnTargetCurrencySelected("EUR"))

            advanceUntilIdle()
            viewModel.effect.runFlowTest {
                assertEquals(
                    ConverterContract.Effect.HideTargetBottomSheet,
                    awaitItem()
                )
                assertEquals(
                    initialState.copy(targetSymbol = "EUR"),
                    viewModel.uiState.value
                )
            }
        }

    @Test
    fun `WHEN viewModel OnBaseAmountChanged THEN baseAmount is updated`() = coroutineRule.runTest {
        whenever(repository.getSymbols()).thenReturn(symbolsEmpty)
        viewModel.setEvent(ConverterContract.Event.OnBaseAmountChanged("1"))

        advanceUntilIdle()

        assertEquals(
            initialState.copy(baseAmount = "1"),
            viewModel.uiState.value
        )
    }

    @Test
    fun `WHEN viewModel OnTargetAmountChanged THEN targetAmount is updated`() =
        coroutineRule.runTest {
            whenever(repository.getSymbols()).thenReturn(symbolsEmpty)
            viewModel.setEvent(ConverterContract.Event.OnTargetAmountChanged("1"))
            advanceUntilIdle()

            assertEquals(
                initialState.copy(targetAmount = "1"),
                viewModel.uiState.value
            )
        }


    @Test
    fun `GIVEN baseAmount is not empty AND repository convert success WHEN viewModel CalculateTargetAmount THEN baseAmount and targetAmount is updated`() =
        coroutineRule.runTest {
            whenever(repository.getSymbols()).thenReturn(symbolsEmpty)
            whenever(repository.convert("EUR", "USD", 5.0)).thenReturn(convertSuccess)
            viewModel.setEvent(ConverterContract.Event.OnBaseCurrencySelected("EUR"))
            viewModel.setEvent(ConverterContract.Event.OnTargetCurrencySelected("USD"))
            viewModel.setEvent(ConverterContract.Event.OnBaseAmountChanged("5.0"))
            viewModel.setEvent(ConverterContract.Event.CalculateTargetAmount)
            advanceUntilIdle()

            assertEquals(
                initialState.copy(
                    baseSymbol = "EUR",
                    targetSymbol = "USD",
                    baseAmount = "5.0",
                    targetAmount = "10.0",
                ),
                viewModel.uiState.value
            )
        }

    @Test
    fun `GIVEN baseAmount is not empty AND repository convert error WHEN viewModel CalculateTargetAmount THEN showError is true`() =
        coroutineRule.runTest {
            whenever(repository.getSymbols()).thenReturn(symbolsEmpty)
            whenever(repository.convert("EUR", "USD", 5.0)).thenReturn(convertError)
            viewModel.setEvent(ConverterContract.Event.OnBaseCurrencySelected("EUR"))
            viewModel.setEvent(ConverterContract.Event.OnTargetCurrencySelected("USD"))
            viewModel.setEvent(ConverterContract.Event.OnBaseAmountChanged("5.0"))
            viewModel.setEvent(ConverterContract.Event.CalculateTargetAmount)
            advanceUntilIdle()

            assertEquals(
                initialState.copy(
                    baseSymbol = "EUR",
                    targetSymbol = "USD",
                    baseAmount = "5.0",
                    showError = true
                ),
                viewModel.uiState.value
            )
        }

    @Test
    fun `GIVEN baseAmount is not empty AND repository convert exception WHEN viewModel CalculateTargetAmount THEN showError is true`() =
        coroutineRule.runTest {
            whenever(repository.getSymbols()).thenReturn(symbolsEmpty)
            whenever(repository.convert("EUR", "USD", 5.0)).thenThrow(RuntimeException())
            viewModel.setEvent(ConverterContract.Event.OnBaseCurrencySelected("EUR"))
            viewModel.setEvent(ConverterContract.Event.OnTargetCurrencySelected("USD"))
            viewModel.setEvent(ConverterContract.Event.OnBaseAmountChanged("5.0"))
            viewModel.setEvent(ConverterContract.Event.CalculateTargetAmount)
            advanceUntilIdle()

            assertEquals(
                initialState.copy(
                    baseSymbol = "EUR",
                    targetSymbol = "USD",
                    baseAmount = "5.0",
                    showError = true
                ),
                viewModel.uiState.value
            )
        }

    @Test
    fun `GIVEN baseAmount is empty WHEN viewModel CalculateTargetAmount THEN state is not updated`() =
        coroutineRule.runTest {
            whenever(repository.getSymbols()).thenReturn(symbolsEmpty)
            viewModel.setEvent(ConverterContract.Event.OnBaseCurrencySelected("EUR"))
            viewModel.setEvent(ConverterContract.Event.OnTargetCurrencySelected("USD"))
            viewModel.setEvent(ConverterContract.Event.OnBaseAmountChanged(""))
            viewModel.setEvent(ConverterContract.Event.CalculateTargetAmount)
            advanceUntilIdle()

            assertEquals(
                initialState.copy(
                    baseSymbol = "EUR",
                    targetSymbol = "USD"
                ),
                viewModel.uiState.value
            )
        }

    @Test
    fun `GIVEN targetAmount is not empty AND repository convert success WHEN viewModel CalculateBaseAmount THEN state is updated`() =
        coroutineRule.runTest {
            whenever(repository.getSymbols()).thenReturn(symbolsEmpty)
            whenever(repository.convert("EUR", "USD", 5.0)).thenReturn(convertSuccess)
            viewModel.setEvent(ConverterContract.Event.OnBaseCurrencySelected("USD"))
            viewModel.setEvent(ConverterContract.Event.OnTargetCurrencySelected("EUR"))
            viewModel.setEvent(ConverterContract.Event.OnTargetAmountChanged("5.0"))
            viewModel.setEvent(ConverterContract.Event.CalculateBaseAmount)
            advanceUntilIdle()

            assertEquals(
                initialState.copy(
                    baseSymbol = "USD",
                    targetSymbol = "EUR",
                    baseAmount = "10.0",
                    targetAmount = "5.0",
                ),
                viewModel.uiState.value
            )
        }

    @Test
    fun `GIVEN targetAmount is not empty AND repository convert error WHEN viewModel CalculateBaseAmount THEN showError is true`() =
        coroutineRule.runTest {
            whenever(repository.getSymbols()).thenReturn(symbolsEmpty)
            whenever(repository.convert("EUR", "USD", 5.0)).thenReturn(convertError)
            viewModel.setEvent(ConverterContract.Event.OnBaseCurrencySelected("USD"))
            viewModel.setEvent(ConverterContract.Event.OnTargetCurrencySelected("EUR"))
            viewModel.setEvent(ConverterContract.Event.OnTargetAmountChanged("5.0"))
            viewModel.setEvent(ConverterContract.Event.CalculateBaseAmount)
            advanceUntilIdle()

            assertEquals(
                initialState.copy(
                    baseSymbol = "USD",
                    targetSymbol = "EUR",
                    targetAmount = "5.0",
                    showError = true
                ),
                viewModel.uiState.value
            )
        }

    @Test
    fun `GIVEN targetAmount is not empty AND repository convert exception WHEN viewModel CalculateBaseAmount THEN showError is true`() =
        coroutineRule.runTest {
            whenever(repository.getSymbols()).thenReturn(symbolsEmpty)
            whenever(repository.convert("EUR", "USD", 5.0)).thenThrow(RuntimeException())
            viewModel.setEvent(ConverterContract.Event.OnBaseCurrencySelected("USD"))
            viewModel.setEvent(ConverterContract.Event.OnTargetCurrencySelected("EUR"))
            viewModel.setEvent(ConverterContract.Event.OnTargetAmountChanged("5.0"))
            viewModel.setEvent(ConverterContract.Event.CalculateBaseAmount)
            advanceUntilIdle()

            assertEquals(
                initialState.copy(
                    baseSymbol = "USD",
                    targetSymbol = "EUR",
                    targetAmount = "5.0",
                    showError = true
                ),
                viewModel.uiState.value
            )
        }

    @Test
    fun `GIVEN targetAmount is empty WHEN viewModel CalculateBaseAmount THEN state is not updated`() =
        coroutineRule.runTest {
            whenever(repository.getSymbols()).thenReturn(symbolsEmpty)
            viewModel.setEvent(ConverterContract.Event.OnBaseCurrencySelected("USD"))
            viewModel.setEvent(ConverterContract.Event.OnTargetCurrencySelected("EUR"))
            viewModel.setEvent(ConverterContract.Event.CalculateBaseAmount)
            advanceUntilIdle()

            assertEquals(
                initialState.copy(
                    baseSymbol = "USD",
                    targetSymbol = "EUR",
                ),
                viewModel.uiState.value
            )
        }

    private val symbolsSuccessResponse = SymbolsResponse(result = "success", supported_codes = listOf(listOf("EUR", "EURO")))
    private val symbolsErrorResponse = SymbolsResponse("error", emptyList())
    private val symbolsEmpty = SymbolsResponse("success", emptyList())
    private val convertSuccess = ConvertResponse("success", "EUR", "USD", 10.0)
    private val convertError = ConvertResponse("error", "", "", 0.0)
}