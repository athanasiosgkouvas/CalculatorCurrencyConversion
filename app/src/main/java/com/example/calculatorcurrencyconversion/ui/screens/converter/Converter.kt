package com.example.calculatorcurrencyconversion.ui.screens.converter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculatorcurrencyconversion.R
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Converter(
    padding: PaddingValues,
    viewModel: ConverterViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val baseBottomSheetState = rememberModalBottomSheetState()
    val targetBottomSheetState = rememberModalBottomSheetState()

    var openBaseBottomSheet by rememberSaveable { mutableStateOf(false) }
    var openTargetBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest {
            when (it) {
                ConverterContract.Effect.ShowBaseBottomSheet -> {
                    openBaseBottomSheet = true
                }

                ConverterContract.Effect.HideBaseBottomSheet -> {
                    openBaseBottomSheet = false
                }

                ConverterContract.Effect.ShowTargetBottomSheet -> {
                    openTargetBottomSheet = true
                }

                ConverterContract.Effect.HideTargetBottomSheet -> {
                    openTargetBottomSheet = false
                }
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(15.dp)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }else{
            if (state.showError.not()) {
                Column(Modifier.fillMaxSize()) {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = { viewModel.setEvent(ConverterContract.Event.OnBaseSymbolClicked) }) {
                            Text(text = "Change")
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = state.baseSymbol)
                        Spacer(modifier = Modifier.weight(1f))
                        OutlinedTextField(
                            modifier = Modifier.width(150.dp),
                            value = state.baseAmount,
                            onValueChange = {
                                viewModel.setEvent(
                                    ConverterContract.Event.OnBaseAmountChanged(
                                        it
                                    )
                                )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Decimal,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onDone = {
                                viewModel.setEvent(
                                    ConverterContract.Event.CalculateTargetAmount
                                )
                            }),
                            singleLine = true,
                        )
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = { viewModel.setEvent(ConverterContract.Event.OnBaseSymbolClicked) }) {
                            Text(text = "Change")
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = state.targetSymbol)
                        Spacer(modifier = Modifier.weight(1f))
                        OutlinedTextField(
                            modifier = Modifier.width(150.dp),
                            value = state.targetAmount,
                            onValueChange = {
                                viewModel.setEvent(
                                    ConverterContract.Event.OnTargetAmountChanged(
                                        it
                                    )
                                )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Decimal,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onDone = {
                                viewModel.setEvent(
                                    ConverterContract.Event.CalculateBaseAmount
                                )
                            }),
                            singleLine = true,
                        )
                    }
                }
            } else {
                Text(
                    text = stringResource(id = R.string.genericError), modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
        }
    }

    if (openBaseBottomSheet) {
        SelectCurrencyBottomSheet(
            onDismissRequest = { openBaseBottomSheet = false },
            sheetState = baseBottomSheetState,
            symbols = state.currencies,
            onClick = { viewModel.setEvent(ConverterContract.Event.OnBaseCurrencySelected(it)) }
        )
    }

    if (openTargetBottomSheet) {
        SelectCurrencyBottomSheet(
            onDismissRequest = { openTargetBottomSheet = false },
            sheetState = targetBottomSheetState,
            symbols = state.currencies,
            onClick = { viewModel.setEvent(ConverterContract.Event.OnTargetCurrencySelected(it)) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCurrencyBottomSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    symbols: List<List<String>>,
    onClick: (String) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(
                count = symbols.size,
                key = { index -> symbols[index][0] }
            ) { index ->
                ListItem(
                    modifier = Modifier.fillMaxWidth(),
                    headlineContent = {
                        Text(
                            text = "${symbols[index][0]} : ${symbols[index][1]}",
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth().clickable { onClick(symbols[index][0]) },
                            maxLines = 1,
                            softWrap = true
                        )
                    }
                )
            }
        }
    }
}