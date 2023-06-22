package com.michaeljordanr.pizzadelivery.domain.usecase

import com.michaeljordanr.pizzadelivery.common.Resource
import com.michaeljordanr.pizzadelivery.data.repository.PizzaRepository
import com.michaeljordanr.pizzadelivery.domain.model.Pizza
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Get Pizzas Use Case
 *
 * This use case gets all the pizzas available
 *
 * @return The list of pizzas
 */
class GetPizzasUseCase @Inject constructor(
    private val repository: PizzaRepository
) {

    operator fun invoke(): Flow<Resource<List<Pizza>>> = flow {
        try {
            emit(Resource.Loading())
            val pizzas = repository.getPizzas()
            emit(Resource.Success(pizzas))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check you internet connection."))
        }
    }
}