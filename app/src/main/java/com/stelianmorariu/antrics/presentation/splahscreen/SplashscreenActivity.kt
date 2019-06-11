/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation.splahscreen

import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.stelianmorariu.antrics.R
import com.stelianmorariu.antrics.domain.model.LocalDeviceInfo
import com.stelianmorariu.antrics.domain.model.Status
import com.stelianmorariu.antrics.presentation.metrics.profile.MetricsProfileActivity
import javax.inject.Inject

class SplashscreenActivity : AppCompatActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var splashViewModel: SplashViewModel

    private lateinit var loadingImageView: ImageView

    private lateinit var motionLayout: MotionLayout

    private var stopLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        motionLayout = findViewById(R.id.motion_layout)
        motionLayout.setTransitionListener(createTransitionListener())

        loadingImageView = findViewById(R.id.device_image)

        splashViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SplashViewModel::class.java)

        splashViewModel.metricsProfile.observe(this, Observer { statefulMetricsProfile ->
            if (statefulMetricsProfile.status == Status.LOADING) {
                startLoadingAnimation()
            } else if (statefulMetricsProfile.status == Status.SUCCESS) {
                stopLoading = true
            }
        })

        setLocalDeviceInfo()
    }

    private fun setLocalDeviceInfo() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        splashViewModel.setLocalDeviceInfo(
            LocalDeviceInfo(
                Build.DEVICE,
                displayMetrics.density,
                displayMetrics.densityDpi.toFloat(),
                displayMetrics.heightPixels,
                displayMetrics.widthPixels
            )
        )
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
            override fun allowsTransition(p0: MotionScene.Transition?): Boolean {
                return true
            }

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
