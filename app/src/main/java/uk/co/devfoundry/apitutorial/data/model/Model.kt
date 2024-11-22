package uk.co.devfoundry.apitutorial.data.model

data class PokemonResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Pokemon>
)


data class Pokemon(
    val name: String
)