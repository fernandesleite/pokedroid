package me.fernandes.pokedroid.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import me.fernandes.pokedroid.data.PokedexApi
import me.fernandes.pokedroid.data.PokedexRepository

@Module
@InstallIn(ViewModelComponent::class)
class PokeListModule {
    @Provides
    @ViewModelScoped
    fun getRepo(api: PokedexApi): PokedexRepository = PokedexRepository(api)
}