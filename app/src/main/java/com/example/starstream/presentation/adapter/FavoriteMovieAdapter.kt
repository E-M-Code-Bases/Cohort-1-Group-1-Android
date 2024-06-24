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

import com.example.starstream.databinding.ItemFullscreenImageBinding
import com.example.starstream.domain.model.Image
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView

class FullscreenImageAdapter(private val onClick: () -> Unit) : ListAdapter<Image, FullscreenImageAdapter.ViewHolder>(
    DIFF_CALLBACK
) {
    inner class ViewHolder(val binding: ItemFullscreenImageBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.findViewById<SubsamplingScaleImageView>(R.id.photoView).setOnClickListener { onClick() }

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

        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemFullscreenImageBinding = DataBindingUtil.inflate(inflater, R.layout.item_fullscreen_image, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.image = getItem(position)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.filePath == newItem.filePath
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {

                return oldItem == newItem
            }
        }
    }

}

}

