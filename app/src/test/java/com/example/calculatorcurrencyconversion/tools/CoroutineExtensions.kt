package com.example.calculatorcurrencyconversion.tools

import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.test
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest

fun CoroutineTestRule.runTest(block: suspend TestScope.() -> Unit): Unit =
    testScope.runTest {
        block()
    }

suspend fun <T> Flow<T>.runFlowTest(block: suspend ReceiveTurbine<T>.() -> Unit) {
    test {
        block()
        cancelAndConsumeRemainingEvents()
    }
}