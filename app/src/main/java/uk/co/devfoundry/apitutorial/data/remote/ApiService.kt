package uk.co.devfoundry.apitutorial.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import uk.co.devfoundry.apitutorial.data.model.PokemonResponse

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 5, // Number of Pokémon per page
        @Query("offset") offset: Int = 0 // Pagination offset
    ): PokemonResponse
}