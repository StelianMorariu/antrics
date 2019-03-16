/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation.metrics.profile

import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.stelianmorariu.antrics.R


class MetricsProfileActivity : AppCompatActivity() {

    private lateinit var loadingImageView: ImageView

    private var stopLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metrics_profile)

        loadingImageView = findViewById(R.id.loading_iv)

        loadingImageView.setOnClickListener { stopLoading = false }
    }

    override fun onStart() {
        super.onStart()
        startLoadingAnimation()
    }

    fun getDisplayMetris(): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }


    private fun startLoadingAnimation() {
        val avd = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_antrics_loading)
        avd?.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                loadingImageView.post {
                    if (!stopLoading) {
                        avd.start()
                    }
                }
            }

        })


        loadingImageView.setImageDrawable(avd)
        (avd as Animatable).start()
    }

//    private fun stopLoadingAnimation() {
//        val delayMillis =
//            DOWNLOAD_ANIM_DURATION - (System.currentTimeMillis() - downloadingStartTimeMillis) % DOWNLOAD_ANIM_DURATION
//
//        loadingImageView.postDelayed({
//            (loadingImageView.drawable as Animatable).stop()
//        }, delayMillis)
//    }

    companion object {
        const val DOWNLOAD_ANIM_DURATION = 500
    }
}
