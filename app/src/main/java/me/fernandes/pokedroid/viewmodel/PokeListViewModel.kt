package me.fernandes.pokedroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private var offset = 0
    private var localPokemonList = mutableListOf<Pokemon>()

    fun getPokemonList() {
        viewModelScope.launch {
            repository.loadPokemonFromServer(offset).collect {
                _loading.value = false
                when (it) {
                    is Response.Success -> {
                        it.data?.let { data ->
                            val results = data.results
                            if (results.isNotEmpty()) {
                                updateList(results)
                            } else {
                                _loading.value = false
                            }
                        }
                    }
                    is Response.NetworkError -> {
                        _errorText.value = "Network Error: ${it.codeError}"
                    }
                    is Response.Error -> {
                        _errorText.value = "Error: Check your internet connection"
                    }
                    is Response.Loading -> {
                        _loading.value = true
                    }
                }
            }
        }
    }

    private fun updateList(results: List<Pokemon>) {
        setSpriteUrl(results)
        localPokemonList.addAll(results)
        _pokemonList.value = localPokemonList
        offset += 90
    }

    private fun setSpriteUrl(results: List<Pokemon>) {
        results.map { pokemon ->
            val urlToRemove = "https://pokeapi.co/api/v2/pokemon/"
            val imgId = pokemon.url.replace(urlToRemove, "").removeSuffix("/")
            pokemon.spriteUrl =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${imgId}.png"
            pokemon.id = imgId
        }
    }
}