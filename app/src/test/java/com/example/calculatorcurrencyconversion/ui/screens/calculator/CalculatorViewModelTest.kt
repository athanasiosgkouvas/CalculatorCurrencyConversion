package com.example.calculatorcurrencyconversion.ui.screens.calculator

import com.example.calculatorcurrencyconversion.tools.CoroutineTestRule
import com.example.calculatorcurrencyconversion.tools.runTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class CalculatorViewModelTest {


    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private lateinit var viewModel: CalculatorViewModel

    private val initialState = CalculatorContract.State()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        viewModel = CalculatorViewModel()

    }

    @Test
    fun `GIVEN state operator is null AND event number is not dot or number1 empty and number dot WHEN viewModel NumberClicked then displayed and number1 update `() = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("5"))
        advanceUntilIdle()

        assertEquals(
            initialState.copy(
                number1 = "5",
                displayed = "5"
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun `GIVEN state operator is null AND event number is  dot and number1 not empty and number no dot WHEN viewModel NumberClicked then displayed and number1 update`() = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("5"))
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("."))
        advanceUntilIdle()

        assertEquals(
            initialState.copy(
                number1 = "5.",
                displayed = "5."
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun `GIVEN state operator is null AND event number is  dot and number1 empty and number dot WHEN viewModel NumberClicked then state do not update`() = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("."))
        advanceUntilIdle()

        assertEquals(
            initialState,
            viewModel.uiState.value
        )
    }

    @Test
    fun `GIVEN state result is empty AND number1 is not empty AND number2 is empty AND event operator not percent WHEN viewModel OperatorClicked THEN state operator and displayed updated`() = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("5"))
        viewModel.setEvent(CalculatorContract.Event.OperatorClicked(Operator.Plus))
        advanceUntilIdle()

        assertEquals(
            initialState.copy(
                number1 = "5",
                operator = Operator.Plus,
                displayed = "5 +"
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun `GIVEN state result is empty AND number1 is not empty AND number2 is empty AND event operator percent WHEN viewModel OperatorClicked THEN state number1 is cleared and result is updated`() = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("5"))
        viewModel.setEvent(CalculatorContract.Event.OperatorClicked(Operator.Percent))
        advanceUntilIdle()

        assertEquals(
            initialState.copy(
                number1 = "",
                operator = null,
                result = "0.05",
                displayed = "0.05"
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun `GIVEN state result is not empty WHEN viewModel OperatorClicked THEN state number1 equals result and result is empty and operator and displayed updated `() = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("5"))
        viewModel.setEvent(CalculatorContract.Event.OperatorClicked(Operator.Percent))
        viewModel.setEvent(CalculatorContract.Event.OperatorClicked(Operator.Plus))
        advanceUntilIdle()

        assertEquals(
            initialState.copy(
                number1 = "0.05",
                operator = Operator.Plus,
                result = "",
                displayed = "0.05 +"
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun `GIVEN state operator not null AND event number is not dot or number2 not empty and number2 has dot WHEN viewModel NumberClicked THEN displayed and number2 update`() = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("5"))
        viewModel.setEvent(CalculatorContract.Event.OperatorClicked(Operator.Plus))
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("3"))
        advanceUntilIdle()

        assertEquals(
            initialState.copy(
                number1 = "5",
                number2 = "3",
                operator = Operator.Plus,
                displayed = "5 + 3"
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun `GIVEN state number1 is empty WHEN viewModel EqualsClicked THEN state do not update`() = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.EqualsClicked)
        advanceUntilIdle()

        assertEquals(initialState, viewModel.uiState.value)
    }

    @Test
    fun `GIVEN state number1 is not empty AND number2 is empty AND operator is not percent WHEN viewModel EqualsClicked THEN state do not update`() = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("5"))
        viewModel.setEvent(CalculatorContract.Event.EqualsClicked)
        advanceUntilIdle()

        assertEquals(initialState, viewModel.uiState.value)
    }

    @Test
    fun `GIVEN state number1 is not empty AND number2 is not empty AND operator is Divide WHEN viewModel EqualsClicked THEN state result and displayed are updated and number1, number2, operator are cleared`()
    = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("5"))
        viewModel.setEvent(CalculatorContract.Event.OperatorClicked(Operator.Divide))
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("5"))
        viewModel.setEvent(CalculatorContract.Event.EqualsClicked)
        advanceUntilIdle()

        assertEquals(
            initialState.copy(
                number1 = "",
                number2 = "",
                operator = null,
                result = "1.0",
                displayed = "1.0"
            ),
            viewModel.uiState.value)
    }

    @Test
    fun `GIVEN state number1 is not empty AND number2 is not empty AND operator is Minus WHEN viewModel EqualsClicked THEN state result and displayed are updated and number1, number2, operator are cleared`()
            = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("5"))
        viewModel.setEvent(CalculatorContract.Event.OperatorClicked(Operator.Minus))
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("5"))
        viewModel.setEvent(CalculatorContract.Event.EqualsClicked)
        advanceUntilIdle()

        assertEquals(
            initialState.copy(
                number1 = "",
                number2 = "",
                operator = null,
                result = "0.0",
                displayed = "0.0"
            ),
            viewModel.uiState.value)
    }

    @Test
    fun `GIVEN state number1 is not empty AND number2 is not empty AND operator is Multiply WHEN viewModel EqualsClicked THEN state result and displayed are updated and number1, number2, operator are cleared`()
            = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("5"))
        viewModel.setEvent(CalculatorContract.Event.OperatorClicked(Operator.Multiply))
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("5"))
        viewModel.setEvent(CalculatorContract.Event.EqualsClicked)
        advanceUntilIdle()

        assertEquals(
            initialState.copy(
                number1 = "",
                number2 = "",
                operator = null,
                result = "25.0",
                displayed = "25.0"
            ),
            viewModel.uiState.value)
    }

    @Test
    fun `GIVEN state number1 is not empty AND number2 is not empty AND operator is Plus WHEN viewModel EqualsClicked THEN state result and displayed are updated and number1, number2, operator are cleared`()
            = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("5"))
        viewModel.setEvent(CalculatorContract.Event.OperatorClicked(Operator.Plus))
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("5"))
        viewModel.setEvent(CalculatorContract.Event.EqualsClicked)
        advanceUntilIdle()

        assertEquals(
            initialState.copy(
                number1 = "",
                number2 = "",
                operator = null,
                result = "10.0",
                displayed = "10.0"
            ),
            viewModel.uiState.value)
    }

    @Test
    fun `GIVEN state number2 is not empty AND number2 length equals 1 WHEN viewModel  DeleteClicked THEN number2 becomes empty`() = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("1"))
        viewModel.setEvent(CalculatorContract.Event.OperatorClicked(Operator.Plus))
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("1"))
        viewModel.setEvent(CalculatorContract.Event.DeleteClicked)
        advanceUntilIdle()

        assertEquals(
            initialState.copy(
                number1 = "1",
                number2 = "",
                operator = Operator.Plus,
                displayed = "1 +"
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun `GIVEN state number2 is not empty AND number2 length not equals 1 WHEN viewModel  DeleteClicked THEN number2 is one digit shorter`() = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("1"))
        viewModel.setEvent(CalculatorContract.Event.OperatorClicked(Operator.Plus))
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("123"))
        viewModel.setEvent(CalculatorContract.Event.DeleteClicked)
        advanceUntilIdle()

        assertEquals(
            initialState.copy(
                number1 = "1",
                number2 = "12",
                operator = Operator.Plus,
                displayed = "1 + 12"
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun `GIVEN state number1 is not empty AND number2 empty AND operator not null WHEN viewModel  DeleteClicked THEN operator becomes null`() = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("1"))
        viewModel.setEvent(CalculatorContract.Event.OperatorClicked(Operator.Plus))
        viewModel.setEvent(CalculatorContract.Event.DeleteClicked)
        advanceUntilIdle()

        assertEquals(
            initialState.copy(
                number1 = "1",
                operator = null,
                displayed = "1"
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun `GIVEN state number1 is not empty and length equals 1 AND number2 empty AND operator null WHEN viewModel DeleteClicked THEN number1 becomes empty`() = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("1"))
        viewModel.setEvent(CalculatorContract.Event.DeleteClicked)
        advanceUntilIdle()

        assertEquals(
            initialState.copy(
                number1 = "",
                displayed = ""
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun `GIVEN state number1 is not empty and length not equals 1 AND number2 empty AND operator null WHEN viewModel DeleteClicked THEN number1 becomes one digit shorter`() = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("123"))
        viewModel.setEvent(CalculatorContract.Event.DeleteClicked)
        advanceUntilIdle()

        assertEquals(
            initialState.copy(
                number1 = "12",
                displayed = "12"
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun `GIVEN state number1 is empty AND number2 empty AND operator null WHEN viewModel DeleteClicked THEN state do not update`() = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.DeleteClicked)
        advanceUntilIdle()

        assertEquals(
            initialState,
            viewModel.uiState.value
        )
    }

    @Test
    fun `WHEN viewModel ClearClicked THEN state clears and displayed is empty`() = coroutineRule.runTest {
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("2"))
        viewModel.setEvent(CalculatorContract.Event.OperatorClicked(Operator.Plus))
        viewModel.setEvent(CalculatorContract.Event.NumberClicked("2"))
        viewModel.setEvent(CalculatorContract.Event.ClearClicked)
        advanceUntilIdle()

        assertEquals(
            initialState.copy(displayed = ""),
            viewModel.uiState.value
        )
    }
}