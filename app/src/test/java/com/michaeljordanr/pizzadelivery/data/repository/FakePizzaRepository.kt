package com.michaeljordanr.pizzadelivery.data.repository

import com.michaeljordanr.pizzadelivery.domain.model.Pizza

class FakePizzaRepository : PizzaRepository {
    override suspend fun getPizzas(): List<Pizza> {
        val fakePizzas = mutableListOf<Pizza>()
        ('a'..'z').forEachIndexed { index, c ->
            fakePizzas.add(
                Pizza(
                    name = c.toString(),
                    price =  5.0,
                    isSelected = false
                )
            )
        }
        return fakePizzas
    }
}