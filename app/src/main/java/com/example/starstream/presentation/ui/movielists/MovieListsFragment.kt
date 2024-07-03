package com.example.starstream.presentation.ui.movielists

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.starstream.R
import com.example.starstream.databinding.FragmentMovieListsBinding
import com.example.starstream.domain.model.Movie
import com.example.starstream.presentation.adapter.MovieAdapter
import com.example.starstream.presentation.ui.base.BaseFragment
import com.example.starstream.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListsFragment : BaseFragment<FragmentMovieListsBinding>(R.layout.fragment_movie_lists) {

    private val viewModel: MovieListsViewModel by viewModel()

    override val defineBindingVariables: (FragmentMovieListsBinding) -> Unit
        get() = { binding ->
            binding.fragment = this
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
        }

    val adapterTrending = MovieAdapter(isTrending = true, onTrendingFabClick = { playTrailer(it) })
    val adapterPopular = MovieAdapter(onLoadMore = { viewModel.onLoadMore(Constants.LIST_ID_POPULAR) })
    val adapterTopRated = MovieAdapter(onLoadMore = { viewModel.onLoadMore(Constants.LIST_ID_TOP_RATED) })
    val adapterNowPlaying = MovieAdapter(onLoadMore = { viewModel.onLoadMore(Constants.LIST_ID_NOW_PLAYING) })
    val adapterUpcoming = MovieAdapter(onLoadMore = { viewModel.onLoadMore(Constants.LIST_ID_UPCOMING) })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycle.apply {
            addObserver(LifecycleViewPager(binding.vpTrendings, true))
            addObserver(LifecycleRecyclerView(binding.rvPopular))
            addObserver(LifecycleRecyclerView(binding.rvTopRated))
            addObserver(LifecycleRecyclerView(binding.rvNowPlaying))
            addObserver(LifecycleRecyclerView(binding.rvUpcoming))
        }

//        binding.vpTrendings.setOnClickListener {
//            navigateToSeeAll(IntentType.LIST, MediaType.MOVIE, Constants.LIST_ID_TRENDING, getString(R.string.title_trending))
//        }
//        binding.rvPopular.setOnClickListener {
//            navigateToSeeAll(IntentType.LIST, MediaType.MOVIE, Constants.LIST_ID_POPULAR, getString(R.string.title_popular_movies))
//        }
//        binding.rvTopRated.setOnClickListener {
//            navigateToSeeAll(IntentType.LIST, MediaType.MOVIE, Constants.LIST_ID_TOP_RATED, getString(R.string.title_top_rated_movies))
//        }
//        binding.rvNowPlaying.setOnClickListener {
//            navigateToSeeAll(IntentType.LIST, MediaType.MOVIE, Constants.LIST_ID_NOW_PLAYING, getString(R.string.title_in_theatres))
//        }
//        binding.rvUpcoming.setOnClickListener {
//            navigateToSeeAll(IntentType.LIST, MediaType.MOVIE, Constants.LIST_ID_UPCOMING, getString(R.string.title_upcoming_movies))
//        }

        // Set click listeners for "See All" TextViews
        binding.seeAllPopular.setOnClickListener {
            navigateToSeeAll(IntentType.LIST, MediaType.MOVIE, Constants.LIST_ID_POPULAR, getString(R.string.title_popular_movies))
        }
        binding.seeAllTopRated.setOnClickListener {
            navigateToSeeAll(IntentType.LIST, MediaType.MOVIE, Constants.LIST_ID_TOP_RATED, getString(R.string.title_top_rated_movies))
        }
        binding.seeAllNowPlaying.setOnClickListener {
            navigateToSeeAll(IntentType.LIST, MediaType.MOVIE, Constants.LIST_ID_NOW_PLAYING, getString(R.string.title_in_theatres))
        }
        binding.seeAllUpcoming.setOnClickListener {
            navigateToSeeAll(IntentType.LIST, MediaType.MOVIE, Constants.LIST_ID_UPCOMING, getString(R.string.title_upcoming_movies))
        }

        collectFlows(listOf(::collectTrendingMovies, ::collectPopularMovies, ::collectTopRatedMovies, ::collectNowPlayingMovies, ::collectUpcomingMovies, ::collectUiState))

    }

    private fun playTrailer(movieId: Int) {
        val videoKey = viewModel.getTrendingMovieTrailer(movieId)
        if (videoKey.isEmpty()) showSnackbar(
            message = getString(R.string.trending_trailer_error),
            indefinite = false,
            anchor = true
        ) else activity?.playYouTubeVideo(videoKey)
    }

    private fun navigateToMovieDetails(movieId: Int) {
        val action = MovieListsFragmentDirections.actionMovieListsFragmentToMovieDetailsFragment(movieId)
        findNavController().navigate(action)
    }

    private fun navigateToSeeAll(intentType: IntentType, mediaType: MediaType, listId: String, title: String, region: String? = null) {
        val action = MovieListsFragmentDirections.actionMovieListsFragmentToSeeAllFragment(
            INTENTTYPE = intentType.ordinal,
            MEDIATYPE = mediaType.ordinal,
            LISTID = listId,
            REGION = region ?: "",
            LIST = emptyArray<Movie>(), // Parcelable array
            ISLANDSCAPE = false,
            TITLE = title
        )
        findNavController().navigate(action)
    }

    private suspend fun collectTrendingMovies() {
        viewModel.trendingMovies.collect { trendingMovies ->
            adapterTrending.submitList(trendingMovies.take(10))
        }
    }

    private suspend fun collectPopularMovies() {
        viewModel.popularMovies.collect { popularMovies ->
            adapterPopular.submitList(popularMovies)
        }
    }

    private suspend fun collectTopRatedMovies() {
        viewModel.topRatedMovies.collect { topRatedMovies ->
            adapterTopRated.submitList(topRatedMovies)
        }
    }

    private suspend fun collectNowPlayingMovies() {
        viewModel.nowPlayingMovies.collect { nowPlayingMovies ->
            adapterNowPlaying.submitList(nowPlayingMovies)
        }
    }

    private suspend fun collectUpcomingMovies() {
        viewModel.upcomingMovies.collect { upcomingMovies ->
            adapterUpcoming.submitList(upcomingMovies)
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) showSnackbar(
                message = state.errorText!!,
                actionText = getString(R.string.button_retry),
                anchor = true
            ) {
                viewModel.retryConnection {
                    viewModel.initRequests()
                }
            }
        }
    }
}
