package com.michaeljordanr.pizzadelivery.di

import com.michaeljordanr.pizzadelivery.common.Constants
import com.michaeljordanr.pizzadelivery.data.remote.PizzaDeliveryApi
import com.michaeljordanr.pizzadelivery.data.repository.PizzaRepository
import com.michaeljordanr.pizzadelivery.domain.repository.PizzaRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePizzaApi(): PizzaDeliveryApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PizzaDeliveryApi::class.java)
    }

    @Provides
    @Singleton
    fun providePizzaRepository(api: PizzaDeliveryApi): PizzaRepository {
        return PizzaRepositoryImpl(api)
    }
}