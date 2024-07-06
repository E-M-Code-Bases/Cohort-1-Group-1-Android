package com.example.starstream.presentation.ui.moviedetails

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.starstream.R
import com.example.starstream.databinding.FragmentMovieDetailsBinding
import com.example.starstream.presentation.adapter.VideoAdapter
import com.example.starstream.presentation.ui.base.BaseFragment
import com.example.starstream.util.playYouTubeVideo
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>(R.layout.fragment_movie_details) {

    private val viewModel: MovieDetailsViewModel by viewModel()

    override val defineBindingVariables: (FragmentMovieDetailsBinding) -> Unit
        get() = { binding ->
            binding.fragment = this
            binding.lifecycleOwner = this
            binding.viewModel = viewModel
        }

    val adapterVideos = VideoAdapter { requireActivity().playYouTubeVideo(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("id") ?: 0
        val backgroundColor = arguments?.getInt("backgroundColor") ?: 0

        viewModel.initRequests(id)
        collectFlows(listOf(::collectUiState))

    }

    fun onBackPressed() {
        fun handleOnBackPressed() {
            findNavController().navigateUp()
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) {
                showSnackbar(state.errorText!!, indefinite = true, actionText = getString(R.string.button_retry)) {
                    viewModel.retryConnection {
                        viewModel.initRequests(id)
                    }
                }
            }
        }
    }
}