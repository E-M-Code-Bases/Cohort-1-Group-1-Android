package com.example.starstream.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starstream.R
import com.example.starstream.databinding.ItemImageBinding
import com.example.starstream.domain.model.Image
import com.example.starstream.presentation.ui.fullscreenimage.FullscreenImageFragment
import com.example.starstream.util.Constants

class ImageAdapter(
    private val isPortrait: Boolean = false,
    private val isGrid: Boolean = false
) : ListAdapter<Image, ImageAdapter.ViewHolder>(DIFF_CALLBACK) {
    inner class ViewHolder(val view: ItemImageBinding) : RecyclerView.ViewHolder(view.root) {
        init {
            view.root.setOnClickListener {
                Intent(it.context, FullscreenImageFragment::class.java).apply {
                    putParcelableArrayListExtra(Constants.IMAGE_LIST, ArrayList(currentList))
                    putExtra(Constants.ITEM_POSITION, adapterPosition)
                    it.context.startActivity(this)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_image, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.apply {
            isGrid = this@ImageAdapter.isGrid
            isPortrait = this@ImageAdapter.isPortrait
            image = getItem(position)
        }
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