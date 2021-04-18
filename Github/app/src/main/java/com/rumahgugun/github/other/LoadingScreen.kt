package com.rumahgugun.github.other

import android.animation.ObjectAnimator
import android.view.View
import android.widget.ProgressBar

class LoadingScreen {
    private fun progressBar(progressBar: ProgressBar) {
        progressBar.progress = 0
        progressBar.visibility = View.VISIBLE
        progressBar.max = 255
        val currentProgress = 255
        ObjectAnimator.ofInt(progressBar, "progress", currentProgress)
            .setDuration(250)
            .start()

    }

    internal fun loadingScreen(state: Boolean, progressBar: ProgressBar) {
        if (state) {
            progressBar(progressBar)
        } else {
            progressBar.visibility = View.GONE
        }
    }
}