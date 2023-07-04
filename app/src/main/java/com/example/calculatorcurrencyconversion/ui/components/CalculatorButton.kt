package com.example.calculatorcurrencyconversion.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.CalculatorButton(
    text: String,
    backgroundColor: Color? = null,
    enabled: Boolean = true,
    isDoubleWidth: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = backgroundColor?.let { ButtonDefaults.buttonColors(containerColor = it) }
            ?: ButtonDefaults.buttonColors(),
        shape = MaterialTheme.shapes.extraLarge,
        elevation = ButtonDefaults.buttonElevation(0.dp),
        modifier = if(isDoubleWidth) Modifier.aspectRatio(2f).weight(2f) else Modifier.aspectRatio(1f).weight(1f),
        content = { Text(text = text, style = MaterialTheme.typography.headlineSmall) }
    )
}