/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation.splahscreen

import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.stelianmorariu.antrics.R

class SplashscreenActivity : AppCompatActivity() {

    private lateinit var loadingImageView: ImageView

    private lateinit var motionLayout: MotionLayout

    private var stopLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        motionLayout = findViewById(R.id.motion_layout)
        motionLayout.setState(R.id.cs_loading, 0, 0)

        loadingImageView = findViewById(R.id.device_image)
    }

    override fun onStart() {
        super.onStart()
        startLoadingAnimation()

        Handler().postDelayed({ stopLoading = true }, 1500L)
    }

    private fun startLoadingAnimation() {
        val avd = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_antrics_loading)
        avd?.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                loadingImageView.post {
                    if (stopLoading) {
                        loadingImageView.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@SplashscreenActivity,
                                R.drawable.ic_device
                            )
                        )
                        motionLayout.transitionToEnd()
                    } else {
                        avd.start()
                    }
                }
            }

        })


        loadingImageView.setImageDrawable(avd)
        (avd as Animatable).start()
    }
}
