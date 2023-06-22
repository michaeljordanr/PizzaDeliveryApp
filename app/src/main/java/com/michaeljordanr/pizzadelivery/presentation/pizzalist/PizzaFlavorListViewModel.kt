package com.michaeljordanr.pizzadelivery.presentation.pizzalist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaeljordanr.pizzadelivery.common.Resource
import com.michaeljordanr.pizzadelivery.domain.model.Pizza
import com.michaeljordanr.pizzadelivery.domain.usecase.GetPizzaPriceUseCase
import com.michaeljordanr.pizzadelivery.domain.usecase.GetPizzasUseCase
import com.michaeljordanr.pizzadelivery.domain.usecase.ValidateMaxFlavorsSelectedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PizzaFlavorListViewModel @Inject constructor(
    private val getPizzasUseCase: GetPizzasUseCase,
    private val validateMaxFlavorsSelectedUseCase: ValidateMaxFlavorsSelectedUseCase,
    private val getPizzaPriceUseCase: GetPizzaPriceUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(ResultState())
    val state: State<ResultState> = _state

    val pizzas = mutableStateListOf<Pizza>()

    init {
        getPizzas()
    }

    private fun getPizzas() {
        getPizzasUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ResultState(isLoading = false)
                    pizzas.addAll(result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value = ResultState(isLoading = false)
                    _state.value = ResultState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }

                is Resource.Loading -> {
                    _state.value = ResultState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun validateMaxFlavorSelected(): Boolean {
        return validateMaxFlavorsSelectedUseCase(pizzas)
    }

    fun getPizzaPrice(pizzas: List<Pizza>): Double {
        return if (pizzas.size > 1) {
            getPizzaPriceUseCase(pizza = Pair(pizzas.first(), pizzas[1]))
        } else if (pizzas.size == 1) {
            getPizzaPriceUseCase(pizza = Pair(pizzas.first(), null))
        } else 0.0
    }

    fun getSelectedItems() = pizzas.filter { it.isSelected }

    fun toggleSelection(index: Int) {

        val item = pizzas[index]
        val isSelected = item.isSelected

        if (isSelected) {
            pizzas[index] = item.copy(isSelected = false)
        } else {
            pizzas[index] = item.copy(isSelected = true)
        }
    }

    fun isTwoFlavorsSelected() : Boolean {
        return getSelectedItems().size > 1
    }

    fun getFirstFlavor() = if (getSelectedItems().any()) getSelectedItems().first() else null

    fun getSecondFlavor(): Pizza? {
        return if (getSelectedItems().size > 1) {
            getSelectedItems()[1]
        } else {
            null
        }
    }
}