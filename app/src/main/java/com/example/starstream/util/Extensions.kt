package com.example.starstream.util

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.example.starstream.BuildConfig
import com.example.starstream.R
import com.google.android.youtube.player.YouTubeStandalonePlayer
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

internal fun Activity.playYouTubeVideo(videoKey: String) {
    startActivity(YouTubeStandalonePlayer.createVideoIntent(this, BuildConfig.YOUTUBE_API_KEY, videoKey, 0, true, false))
}

internal fun RecyclerView.interceptTouch() {
    addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            return if (canScrollHorizontally(RecyclerView.FOCUS_FORWARD)) {
                when (e.action) {
                    MotionEvent.ACTION_MOVE -> rv.parent.requestDisallowInterceptTouchEvent(true)
                }
                false
            } else {
                when (e.action) {
                    MotionEvent.ACTION_MOVE -> rv.parent.requestDisallowInterceptTouchEvent(false)
                }
                removeOnItemTouchListener(this)
                true
            }
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
    })
}

internal fun Int.isDarkColor(): Boolean {
    val darkness = 1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
    return darkness >= 0.5
}

internal fun Int.setTintColor(reverse: Boolean = false): Int {
    return if (reverse) {
        if (this.isDarkColor()) Color.BLACK else Color.WHITE
    } else {
        if (this.isDarkColor()) Color.WHITE else Color.BLACK
    }
}

internal fun Int?.formatTime(context: Context): String? = this?.let {
    when {
        it == 0 -> return null
        it >= 60 -> {
            val hours = it / 60
            val minutes = it % 60
            "${hours}${context.getString(R.string.hour_short)} ${if (minutes == 0) "" else "$minutes${context.getString(R.string.minute_short)}"}"
        }
        else -> "${it}${context.getString(R.string.minute_short)}"
    }
}

internal fun Long.thousandsSeparator(context: Context): String {
    val symbols = DecimalFormatSymbols()
    symbols.groupingSeparator = context.getString(R.string.thousand_separator).toCharArray()[0]
    return DecimalFormat("#,###", symbols).format(this)
}

internal fun Double.round(): Double = (this * 10.0).roundToInt() / 10.0

internal fun Double.asPercent(): String = "%${(this * 10).toInt()}"

internal fun String?.formatDate(): String {
    val outputFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.US)
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return if (!this.isNullOrEmpty()) outputFormat.format(inputFormat.parse(this)) else ""
}

