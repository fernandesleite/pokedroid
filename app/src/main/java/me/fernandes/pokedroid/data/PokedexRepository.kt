package me.fernandes.pokedroid.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.fernandes.pokedroid.data.remote.PokemonListWrapper
import me.fernandes.pokedroid.utils.Response
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PokedexRepository @Inject constructor(
    private val api: PokedexApi
) {

    fun loadPokemonFromServer(offset: Int): Flow<Response<PokemonListWrapper>> = flow {
        emit(Response.Loading())
        try {
            emit(Response.Success(data = api.getPokemonList(offset)))
        } catch (error: HttpException) {
            emit(Response.NetworkError(error.code(), error.message()))
        } catch (error: IOException) {
            emit(Response.Error(error.message ?: "Generic Error"))
        }

    }
}