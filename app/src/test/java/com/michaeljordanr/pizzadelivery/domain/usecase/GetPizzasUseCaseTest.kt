package com.michaeljordanr.pizzadelivery.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.michaeljordanr.pizzadelivery.data.repository.FakePizzaRepository
import com.michaeljordanr.pizzadelivery.domain.model.Pizza
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetPizzasUseCaseTest {

    private lateinit var getPizzas: GetPizzasUseCase
    private lateinit var validateMaxFlavorsSelected: ValidateMaxFlavorsSelectedUseCase
    private lateinit var getPizzaPrice: GetPizzaPriceUseCase
    private lateinit var fakeRepository: FakePizzaRepository

    @Before
    fun setUp() {
        fakeRepository = FakePizzaRepository()
        getPizzas = GetPizzasUseCase(fakeRepository)
    }

    @Test
    fun `Check if the pizza result are not null`(): Unit = runBlocking {
        getPizzas().onEach { result ->
            val pizzas = result.data
            assertThat(pizzas).isNotNull()
        }
    }

    @Test
    fun `Validate if max flavors were correctly selected`(): Unit = runBlocking {
        getPizzas().onEach { result ->
            val pizzas = result.data ?: emptyList()
            pizzas[0].isSelected = true
            pizzas[1].isSelected = true

            assertThat(validateMaxFlavorsSelected(pizzas)).isTrue()
        }
    }

    @Test
    fun `Validate if the total price when selecting only one pizza flavor is correct`(): Unit = runBlocking {
        getPizzas().onEach { result ->
            val pizzas = result.data ?: emptyList()
            val totalPrice = getPizzaPrice(Pair(pizzas.first(), null))

            assertThat(pizzas.first().price).isEqualTo(totalPrice)
        }
    }

    @Test
    fun `Validate if the total price when selecting two pizza flavors is correct`(): Unit = runBlocking {
        getPizzas().onEach { result ->
            val pizzas = result.data ?: emptyList()
            val firstFlavor = Pizza("a", 10.0, true)
            val secondFlavor = Pizza("b", 15.0, true)

            val correctTotalPrice = (firstFlavor.price / 2) + (secondFlavor.price / 2)

            val totalPrice = getPizzaPrice(Pair(firstFlavor, secondFlavor))

            assertThat(correctTotalPrice).isEqualTo(totalPrice)
        }
    }
}