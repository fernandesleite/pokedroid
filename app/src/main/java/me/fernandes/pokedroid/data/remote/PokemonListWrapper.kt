package me.fernandes.pokedroid.data.remote

data class PokemonListWrapper(
    val count: String,
    val next: String,
    val previous: String,
    val results: List<Pokemon>
)
