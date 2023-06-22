package com.michaeljordanr.pizzadelivery.presentation.pizzalist

data class ResultState(
    val isLoading: Boolean = false,
    val error: String = ""
)