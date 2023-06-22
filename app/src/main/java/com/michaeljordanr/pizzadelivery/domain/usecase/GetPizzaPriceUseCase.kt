package com.michaeljordanr.pizzadelivery.domain.usecase

import com.michaeljordanr.pizzadelivery.domain.model.Pizza
import javax.inject.Inject

/**
 * Get Pizza Price Use Case
 *
 * This use case gets the correct pizza price accordingly with the number and kind of pizza flavors
 *
 * @property pizza a pair of pizzas, the second item is nullable since the second flavor is optional
 * @return The correct pizza price
 */
class GetPizzaPriceUseCase @Inject constructor() {

    operator fun invoke(pizza: Pair<Pizza, Pizza?>): Double {
        return if (pizza.second == null) {
            pizza.first.price
        } else {
            val firstHalf = pizza.first.price / 2

            val price = pizza.second?.price ?: 0.0
            val secondHalf = if (price > 0) {
                (price / 2)
            } else {
                0.0
            }
            firstHalf + secondHalf
        }
    }
}