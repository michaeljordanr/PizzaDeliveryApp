package com.michaeljordanr.pizzadelivery.domain.usecase

import com.michaeljordanr.pizzadelivery.domain.model.Pizza
import javax.inject.Inject

/**
 * Validates the maximum number of flavors selected
 *
 * This use case checks if it wasn't selected more than 2 flavors of pizza
 *
 * @return true if two or less pizza flavors were selected, otherwise returns false
 */
class ValidateMaxFlavorsSelectedUseCase @Inject constructor() {

    operator fun invoke(pizzaListItem: List<Pizza>): Boolean {
        return pizzaListItem.filter { it.isSelected }.size <= 2
    }
}