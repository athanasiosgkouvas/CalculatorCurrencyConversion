package com.example.calculatorcurrencyconversion.ui.screens.calculator

import android.content.Context
import com.example.calculatorcurrencyconversion.data.repository.Repository
import com.example.calculatorcurrencyconversion.tools.CoroutineTestRule
import com.example.calculatorcurrencyconversion.tools.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CalculatorViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private lateinit var repository: Repository

    @Mock
    private lateinit var context: Context

    private lateinit var viewModel: CalculatorViewModel

    private val initialState = CalculatorContract.State()

    @Before
    fun setUp() {
        repository = Mockito.spy(Repository::class.java)
        MockitoAnnotations.openMocks(this)

        viewModel = CalculatorViewModel()

    }

    @Test
    fun `WHEN viewModel  ClearClicked THEN state clears`() = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.ClearClicked)

        assertEquals(
            initialState,
            viewModel.uiState.value
        )
    }

    @Test
    fun `WHEN viewModel  DeleteClicked AND states number2 is not empty THEN number2 is one character shorter`() = coroutineRule.runTest {

        viewModel.setEvent(CalculatorContract.Event.DeleteClicked)

        assertEquals(
            initialState,
            viewModel.uiState.value
        )
    }
}