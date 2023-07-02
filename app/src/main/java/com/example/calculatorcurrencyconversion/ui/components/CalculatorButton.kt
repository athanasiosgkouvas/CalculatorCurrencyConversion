package com.example.calculatorcurrencyconversion.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CalculatorButton(
    text: String,
    backgroundColor: Color? = null,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = backgroundColor?.let { ButtonDefaults.buttonColors(containerColor = it) }
            ?: ButtonDefaults.buttonColors(),
        shape = MaterialTheme.shapes.extraLarge,
        elevation = ButtonDefaults.buttonElevation(0.dp),
        contentPadding = PaddingValues(5.dp),
        modifier = Modifier.size(75.dp),
        content = { Text(text = text, style = MaterialTheme.typography.titleMedium) }
    )
}