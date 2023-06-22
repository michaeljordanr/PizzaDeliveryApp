package com.michaeljordanr.pizzadelivery.domain.repository

import com.michaeljordanr.pizzadelivery.data.remote.PizzaDeliveryApi
import com.michaeljordanr.pizzadelivery.data.repository.PizzaRepository
import com.michaeljordanr.pizzadelivery.domain.model.Pizza
import javax.inject.Inject

class PizzaRepositoryImpl @Inject constructor(
    private val api: PizzaDeliveryApi
) : PizzaRepository {

    override suspend fun getPizzas(): List<Pizza> {
        return api.getPizzas()
    }
}