package com.michaeljordanr.pizzadelivery.data.remote

import com.michaeljordanr.pizzadelivery.domain.model.Pizza
import retrofit2.http.GET

interface PizzaDeliveryApi {

    @GET("/mobile/tests/pizzas.json")
    suspend fun getPizzas(): List<Pizza>

}