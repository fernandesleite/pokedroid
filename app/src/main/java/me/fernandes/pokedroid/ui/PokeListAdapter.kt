package me.fernandes.pokedroid.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.fernandes.pokedroid.data.remote.Pokemon
import me.fernandes.pokedroid.databinding.PokemonListItemBinding

class PokeListAdapter() : ListAdapter<Pokemon, PokeListAdapter.ViewHolder>(PokemonDiff) {

    class ViewHolder(val binding: PokemonListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            binding.pokemonName.text = pokemon.name.replaceFirstChar { char -> char.uppercase() }

            Glide
                .with(binding.root.context)
                .load(pokemon.spriteUrl)
                .fitCenter()
                .into(binding.pokemonSprite);
        }

    }


    object PokemonDiff : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.name == newItem.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            PokemonListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}