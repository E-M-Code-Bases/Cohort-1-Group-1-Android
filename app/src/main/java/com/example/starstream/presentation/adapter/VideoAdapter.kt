package com.example.starstream.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starstream.R
import com.example.starstream.domain.model.Video
import com.example.starstream.databinding.ItemVideoBinding

class VideoAdapter(
    private val isGrid: Boolean = false,
    private val onItemClick: (String) -> Unit
) : ListAdapter<Video, VideoAdapter.ViewHolder>(DIFF_CALLBACK) {
    inner class ViewHolder(val view: ItemVideoBinding) : RecyclerView.ViewHolder(view.root) {
        init {
            view.linearLayout.setOnClickListener {
                onItemClick.invoke(getItem(adapterPosition).key)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_video, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.apply {
            video = getItem(position)
            isGrid = this@VideoAdapter.isGrid
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Video>() {
            override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem == newItem
            }
        }
    }
}