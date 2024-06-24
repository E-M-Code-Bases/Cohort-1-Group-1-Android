package com.example.starstream.presentation.ui.fullscreenimage

import android.os.Bundle
import android.view.View
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.starstream.R
import com.example.starstream.databinding.FragmentFullscreenImageBinding
import com.example.starstream.domain.model.Image
import com.example.starstream.presentation.adapter.FullscreenImageAdapter
import com.example.starstream.presentation.ui.base.BaseFragment
import com.example.starstream.util.Constants
import kotlinx.coroutines.flow.MutableStateFlow

class FullscreenImageFragment : BaseFragment<FragmentFullscreenImageBinding>(R.layout.fragment_fullscreen_image) {

    private var _binding: FragmentFullscreenImageBinding? = null
    override val binding get() = _binding!!

    // Observable state variables
    val isFullscreen = MutableStateFlow(false)
    val imageNumber = MutableStateFlow("")

    override val defineBindingVariables: ((FragmentFullscreenImageBinding) -> Unit)?
        get() = { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
            _binding = binding
            binding.activity = this
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = arguments?.getParcelableArrayList<Image>(Constants.IMAGE_LIST) ?: emptyList()
        val position = arguments?.getInt(Constants.ITEM_POSITION, 0) ?: 0
        val totalImageCount = imageList.size

        binding.vpImages.apply {
            adapter = FullscreenImageAdapter { toggleUiVisibility() }.apply { submitList(imageList) }
            setCurrentItem(position, false)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    imageNumber.value = "${position + 1}/$totalImageCount"
                }
            })
        }
    }

    private fun toggleUiVisibility() {
        if (isFullscreen.value) showUi() else hideUi()
    }

    private fun hideUi() {
        activity?.let {
            WindowCompat.setDecorFitsSystemWindows(it.window, false)
            WindowInsetsControllerCompat(it.window, binding.frameLayout).let { controller ->
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
            isFullscreen.value = true
        }
    }

    private fun showUi() {
        activity?.let {
            WindowCompat.setDecorFitsSystemWindows(it.window, true)
            WindowInsetsControllerCompat(it.window, binding.frameLayout).show(WindowInsetsCompat.Type.systemBars())
            isFullscreen.value = false
        }
    }
    fun finish() {
        activity?.supportFragmentManager?.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
