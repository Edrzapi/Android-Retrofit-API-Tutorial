package uk.co.devfoundry.apitutorial.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Instance {

    object RetrofitInstance {
        private const val BASE_URL = "https://pokeapi.co/api/v2/"
        val api: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}