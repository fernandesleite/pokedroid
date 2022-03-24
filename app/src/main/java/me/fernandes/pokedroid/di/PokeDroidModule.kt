package me.fernandes.pokedroid.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import me.fernandes.pokedroid.navigation.Navigation

@Module
@InstallIn(ActivityComponent::class)
class PokeDroidModule {

    @Provides
    fun provideNavigation(@ActivityContext context: Context): Navigation {
        return Navigation((context as AppCompatActivity).supportFragmentManager)
    }
}