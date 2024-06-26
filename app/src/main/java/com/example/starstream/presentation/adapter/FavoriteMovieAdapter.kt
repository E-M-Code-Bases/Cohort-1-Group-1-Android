package com.example.starstream.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starstream.R
import com.example.starstream.databinding.ItemFavoriteMovieBinding
import com.example.starstream.domain.model.FavoriteMovie

class FavoriteMovieAdapter(private val onItemClicked: (item: FavoriteMovie) -> Unit) : ListAdapter<FavoriteMovie, FavoriteMovieAdapter.ViewHolder>(
    DIFF_CALLBACK
) {
    inner class ViewHolder(val view: ItemFavoriteMovieBinding) : RecyclerView.ViewHolder(view.root) {
        init {
            view.ivRemove.setOnClickListener { onItemClicked(getItem(adapterPosition)) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_favorite_movie, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.movie = getItem(position)

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteMovie>() {
            override fun areItemsTheSame(oldItem: FavoriteMovie, newItem: FavoriteMovie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavoriteMovie, newItem: FavoriteMovie): Boolean {
                return oldItem == newItem
            }
        }
    }
}