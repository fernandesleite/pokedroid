package me.fernandes.pokedroid.navigation

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import me.fernandes.pokedroid.R
import me.fernandes.pokedroid.ui.ErrorFragment
import me.fernandes.pokedroid.ui.PokeListFragment

class Navigation(private val supportFragmentManager: FragmentManager) {
    fun navigateToPokeList() {
        supportFragmentManager.commit {
            replace(
                R.id.container,
                PokeListFragment.newInstance(),
                PokeListFragment::class.simpleName
            )
            addToBackStack(PokeListFragment::class.simpleName)
        }
    }

    fun navigateToErrorScreen(error: String) {
        supportFragmentManager.commit {
            replace(
                R.id.container,
                ErrorFragment.newInstance(error),
                ErrorFragment::class.simpleName
            )
            addToBackStack(ErrorFragment::class.simpleName)
        }
    }
}