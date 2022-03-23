package me.fernandes.pokedroid.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import me.fernandes.pokedroid.R
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
                .placeholder(R.drawable.missing_no_error_icon)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.pokemonSprite)
        }

    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    object PokemonDiff : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            PokemonListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

        if (position == (itemCount - 45)) {
            onLoadMoreTriggered?.invoke()
        }
    }

    var onLoadMoreTriggered: (() -> Unit)? = null
}