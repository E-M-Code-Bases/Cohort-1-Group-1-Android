package com.example.starstream.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.starstream.presentation.ui.favoritemovies.FavoriteMoviesFragment
import com.example.starstream.presentation.ui.favorites.FavoritesFragment
import com.example.starstream.presentation.ui.fullscreenimage.FullscreenImageFragment
import com.example.starstream.presentation.ui.home.HomeFragment
import com.example.starstream.presentation.ui.moviedetails.MovieDetailsFragment
import com.example.starstream.presentation.ui.movielists.MovieListsFragment
import com.example.starstream.presentation.ui.seeall.SeeAllFragment
import com.example.starstream.util.Constants

class FragmentAdapter(private val fragment: Fragment) : FragmentStateAdapter(fragment.childFragmentManager, fragment.viewLifecycleOwner.lifecycle) {
    private val homeFragments = listOf(MovieListsFragment())
    private val favoritesFragments = listOf(FavoriteMoviesFragment())

    private val movieListsFragment = listOf(MovieListsFragment())
    private val movieDetailsFragment = listOf(MovieDetailsFragment())
    private val seeAllFragment = listOf(SeeAllFragment())
    private val fullscreenImageFragment = listOf(FullscreenImageFragment())

    override fun getItemCount(): Int = 1

    override fun createFragment(position: Int): Fragment {
        return when (fragment) {
            is HomeFragment -> homeFragments[position]
            is FavoritesFragment -> favoritesFragments[position]

            is MovieListsFragment -> movieListsFragment[position]
            is MovieDetailsFragment -> movieDetailsFragment [position]
            is SeeAllFragment -> seeAllFragment[position]
            is FullscreenImageFragment -> fullscreenImageFragment[position]

            else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_FRAGMENT_TYPE)
        }
    }
}
