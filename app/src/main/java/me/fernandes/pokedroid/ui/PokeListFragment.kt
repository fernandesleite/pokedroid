package me.fernandes.pokedroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.fernandes.pokedroid.databinding.PokeListFragmentBinding
import me.fernandes.pokedroid.viewmodel.PokeListViewModel

@AndroidEntryPoint
class PokeListFragment : Fragment() {

    private val viewModel by viewModels<PokeListViewModel>()

    private lateinit var binding: PokeListFragmentBinding

    private lateinit var adapter: PokeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PokeListFragmentBinding.inflate(layoutInflater)
        adapter = PokeListAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        initializeObservers()
    }

    private fun initializeView() {
        binding.apply {
            pokemonList.layoutManager = GridLayoutManager(context, 4)
            pokemonList.adapter = adapter
        }
        viewModel.getPokemonList()
    }

    private fun initializeObservers() {
        viewModel.pokemonList.observe(viewLifecycleOwner) { pokemonList ->
            adapter.submitList(pokemonList)

        }
        viewModel.errorText.observe(viewLifecycleOwner) { error ->
            handleErrorScreen(error)
        }

        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            handleLoading(loading)
        }
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun handleErrorScreen(error: String?) {
        binding.apply {
            pokemonList.visibility = View.GONE
            errorScreen.visibility = View.VISIBLE
            errorMessage.text = error
        }
    }
}