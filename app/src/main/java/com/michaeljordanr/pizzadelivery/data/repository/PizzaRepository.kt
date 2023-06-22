package com.michaeljordanr.pizzadelivery.data.repository

import com.michaeljordanr.pizzadelivery.domain.model.Pizza

interface PizzaRepository {

    suspend fun getPizzas(): List<Pizza>
}