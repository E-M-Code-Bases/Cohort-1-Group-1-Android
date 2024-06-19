package com.example.starstream.presentation.ui.fullscreenimage

import android.os.Bundle
import android.os.Parcelable
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.starstream.R
import kotlinx.coroutines.flow.MutableStateFlow

class FullscreenImageActivity : BaseActivity<ActivityFullscreenImageBinding>(R.layout.activity_fullscreen_image) {

    override val defineBindingVariables: (ActivityFullscreenImageBinding) -> Unit
        get() = { binding ->
            binding.activity = this
            binding.lifecycleOwner = this
        }

    val isFullscreen = MutableStateFlow(false)
    val imageNumber = MutableStateFlow("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        val imageList = intent.getParcelableArrayListExtra<Parcelable>(Constants.IMAGE_LIST) as List<Image>
        val position = intent.getIntExtra(Constants.ITEM_POSITION, 0)
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
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.frameLayout).let {
            it.hide(WindowInsetsCompat.Type.systemBars())
            it.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        isFullscreen.value = true
    }

    private fun showUi() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, binding.frameLayout).show(WindowInsetsCompat.Type.systemBars())

        isFullscreen.value = false
    }
}