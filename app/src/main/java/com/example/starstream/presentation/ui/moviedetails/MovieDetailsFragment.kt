package com.example.starstream.presentation.ui.moviedetails

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.starstream.R
import com.example.starstream.databinding.FragmentMovieDetailsBinding
import com.example.starstream.presentation.adapter.ImageAdapter
import com.example.starstream.presentation.adapter.MovieAdapter
import com.example.starstream.presentation.adapter.PersonAdapter
import com.example.starstream.presentation.adapter.VideoAdapter
import com.example.starstream.presentation.ui.base.BaseFragment
import com.example.starstream.util.playYouTubeVideo
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>(R.layout.fragment_movie_details) {

    private val viewModel: MovieDetailsViewModel by viewModel()

    override val defineBindingVariables: (FragmentMovieDetailsBinding) -> Unit
        get() = { binding ->
            binding.activity = this
            binding.lifecycleOwner = this
            binding.viewModel = viewModel
        }

     val adapterVideos = VideoAdapter { playYouTubeVideo(it) }
    private val adapterCast = PersonAdapter(isCast = true)
     val adapterImages = ImageAdapter()
    private val adapterRecommendations = MovieAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initRequests(id)
        collectFlows(listOf(::collectDetails, ::collectUiState))
    }

    fun onBackPressed () {
        fun handleOnBackPressed() {
            findNavController().navigateUp()

        }
    }

    private suspend fun collectDetails() {
        viewModel.details.collect { details ->
            adapterVideos.submitList(details.videos.filterVideos())
            adapterImages.submitList(details.images.backdrops)
            adapterRecommendations.submitList(details.recommendations.results)
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
