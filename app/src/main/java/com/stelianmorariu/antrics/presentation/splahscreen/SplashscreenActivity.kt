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
import com.stelianmorariu.antrics.presentation.metrics.profile.MetricsProfileActivity

class SplashscreenActivity : AppCompatActivity() {

    private lateinit var loadingImageView: ImageView

    private lateinit var motionLayout: MotionLayout

    private var stopLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        motionLayout = findViewById(R.id.motion_layout)
        motionLayout.setState(R.id.cs_loading, 0, 0)
        motionLayout.setTransitionListener(createTransitionListener())

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

    private fun createTransitionListener(): MotionLayout.TransitionListener {
        return object : MotionLayout.TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                this@SplashscreenActivity.startActivity(
                    MetricsProfileActivity.newIntent(this@SplashscreenActivity)
                )
            }

        }

    }
}
