package com.michaeljordanr.pizzadelivery.presentation.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalDim = compositionLocalOf { Dimensions() }

data class Dimensions(
    val logoWidth: Dp = 100.dp,
    val logoHeight: Dp = 50.dp
)
