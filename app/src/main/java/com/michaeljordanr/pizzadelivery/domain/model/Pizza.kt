package com.michaeljordanr.pizzadelivery.domain.model


data class Pizza(
    val name: String,
    val price: Double,
    var isSelected: Boolean = false
)