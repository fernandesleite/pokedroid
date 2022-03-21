package me.fernandes.pokedroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.fernandes.pokedroid.data.PokedexRepository
import me.fernandes.pokedroid.data.remote.Pokemon
import me.fernandes.pokedroid.utils.Response
import javax.inject.Inject

@HiltViewModel
class PokeListViewModel @Inject constructor(
    private val repository: PokedexRepository
) : ViewModel() {
    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>>
        get() = _pokemonList

    private val _errorText = MutableLiveData<String>()
    val errorText: LiveData<String>
        get() = _errorText

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun getPokemonList() {
        viewModelScope.launch {
            repository.loadPokemonFromServer.collect {
                _loading.value = false
                when (it) {
                    is Response.Success -> {
                        it.data?.results?.map { pokemon ->
                            setSpriteUrl(pokemon)
                        }
                        _pokemonList.value = it.data?.results ?: emptyList()
                    }
                    is Response.NetworkError -> {
                        _errorText.value = "Network Error: ${it.codeError}"
                    }
                    is Response.Error -> {
                        _errorText.value = "Error: Check your internet connection"
                    }
                    is Response.Loading -> {
                        _loading.value = true
                        delay(1000L)
                    }
                }
            }
        }
    }

    private fun setSpriteUrl(pokemon: Pokemon) {
        val imageUrl =
            pokemon.url.replace("https://pokeapi.co/api/v2/pokemon/", "")
                .removeSuffix("/")
        pokemon.spriteUrl =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${imageUrl}.png"
    }
}