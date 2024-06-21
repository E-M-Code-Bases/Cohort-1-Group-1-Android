package com.example.starstream.presentation.ui.search

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import com.example.starstream.R
import com.example.starstream.databinding.FragmentSearchBinding
import com.example.starstream.presentation.adapter.MovieAdapter
import com.example.starstream.presentation.ui.base.BaseFragment
import com.example.starstream.util.LifecycleRecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModel()

    override val defineBindingVariables: (FragmentSearchBinding) -> Unit
        get() = { binding ->
            binding.fragment = this
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
        }

    val adapterMovies by lazy { MovieAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.addObserver(LifecycleRecyclerView(binding.rvMovies))
        setupSearchView()
        collectFlows(listOf(::collectMovieSearchResults, ::collectUiState))
    }

    fun clearSearch() {
        viewModel.clearSearchResults()
        adapterMovies.submitList(null)
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.rvMovies.scrollToPosition(0)
                if (!query.isNullOrEmpty()) viewModel.fetchInitialSearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    private suspend fun collectMovieSearchResults() {
        viewModel.movieResults.collect { movies ->
            adapterMovies.submitList(movies)
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
