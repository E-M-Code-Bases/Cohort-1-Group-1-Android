package com.example.starstream.presentation.ui.favoritetvs

import android.os.Bundle
import android.view.View
import com.example.starstream.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTvsFragment : BaseFragment<FragmentFavoriteTvsBinding>(R.layout.fragment_favorite_tvs) {

    private val viewModel: FavoriteTvsViewModel by viewModel()

    override val defineBindingVariables: (FragmentFavoriteTvsBinding) -> Unit
        get() = { binding ->
            binding.fragment = this
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
        }

    val adapterFavorites = FavoriteTvAdapter { removeTv(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycle.addObserver(LifecycleRecyclerView(binding.recyclerView))
        collectFlows(listOf(::collectFavoriteTvs))
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFavoriteTvs()
    }

    private fun removeTv(tv: FavoriteTv) {
        viewModel.removeTvFromFavorites(tv)

        showSnackbar(
            message = getString(R.string.snackbar_removed_item),
            actionText = getString(R.string.snackbar_action_undo),
            anchor = true
        ) {
            viewModel.addTvToFavorites(tv)
        }
    }

    private suspend fun collectFavoriteTvs() {
        viewModel.favoriteTvs.collect { favoriteTvs ->
            adapterFavorites.submitList(favoriteTvs)
        }
    }
}