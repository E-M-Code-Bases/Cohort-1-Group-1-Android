package com.example.starstream.presentation.ui.favoritemovies

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.starstream.R
import com.example.starstream.ui.fragments.favoritemovies.FavoriteMoviesViewModel

class FavoriteMoviesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteMoviesFragment()
    }

    private val viewModel: FavoriteMoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_favorite_movies, container, false)
    }
}