package com.example.starstream.presentation.ui.moviedetails

import android.os.Bundle
import com.example.starstream.R
import com.example.starstream.databinding.ActivityMovieDetailsBinding
import com.example.starstream.presentation.adapter.ImageAdapter
import com.example.starstream.presentation.adapter.MovieAdapter
import com.example.starstream.presentation.adapter.PersonAdapter
import com.example.starstream.presentation.adapter.VideoAdapter
import com.example.starstream.presentation.ui.base.BaseActivity
import com.example.starstream.util.playYouTubeVideo
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsActivity : BaseActivity<ActivityMovieDetailsBinding>(R.layout.activity_movie_details) {

    private val viewModel: MovieDetailsViewModel by viewModel()

    override val defineBindingVariables: (ActivityMovieDetailsBinding) -> Unit
        get() = { binding ->
            binding.activity = this
            binding.lifecycleOwner = this
            binding.viewModel = viewModel
        }

    val adapterVideos = VideoAdapter { playYouTubeVideo(it) }
    val adapterCast = PersonAdapter(isCast = true)
    val adapterImages = ImageAdapter()
    val adapterRecommendations = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        viewModel.initRequests(id)
        collectFlows(listOf(::collectDetails, ::collectUiState))
    }

    private suspend fun collectDetails() {
        viewModel.details.collect { details ->
//            adapterCast.submitList(details.credits.cast)
            adapterVideos.submitList(details.videos.filterVideos())
            adapterImages.submitList(details.images.backdrops)
            adapterRecommendations.submitList(details.recommendations.results)
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) showSnackbar(state.errorText!!, getString(R.string.button_retry)) {
                viewModel.retryConnection {
                    viewModel.initRequests(id)
                }
            }
        }
    }
}
