package me.fernandes.pokedroid.data

import me.fernandes.pokedroid.data.remote.Pokemon
import me.fernandes.pokedroid.data.remote.PokemonListWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexApi {
    @GET("pokemon")
    suspend fun getPokemonList(@Query("offset") offset: Int = 0): PokemonListWrapper

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): Pokemon
}