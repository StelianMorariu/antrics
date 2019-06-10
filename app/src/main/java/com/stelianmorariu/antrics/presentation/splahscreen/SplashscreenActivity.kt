/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation.splahscreen

import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.stelianmorariu.antrics.R
import com.stelianmorariu.antrics.domain.model.MetricsProfile
import com.stelianmorariu.antrics.domain.model.emptyMetricsProfile
import com.stelianmorariu.antrics.presentation.metrics.profile.MetricsProfileActivity
import com.stelianmorariu.antrics.presentation.metrics.profile.MetricsProfileViewModel
import javax.inject.Inject

class SplashscreenActivity : AppCompatActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var metricsViewModel: MetricsProfileViewModel

    private lateinit var loadingImageView: ImageView

    private lateinit var motionLayout: MotionLayout

    private var stopLoading = false

    private var metricsProfile = emptyMetricsProfile()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        motionLayout = findViewById(R.id.motion_layout)
        motionLayout.setState(R.id.cs_loading, 0, 0)
        motionLayout.setTransitionListener(createTransitionListener())

        loadingImageView = findViewById(R.id.device_image)

        metricsViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MetricsProfileViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
//        val displayMetrics = DisplayMetrics()
//        windowManager.defaultDisplay.getMetrics(displayMetrics)
//        presenter.getMetricsProfile(Build.DEVICE, displayMetrics)
    }

    fun showLoading() {
        startLoadingAnimation()
    }

    fun showMetricsProfile(profile: MetricsProfile) {
        metricsProfile = profile
        stopLoading = true
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
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                this@SplashscreenActivity.startActivity(
                    MetricsProfileActivity.newIntent(this@SplashscreenActivity, metricsProfile)
                )
            }

        }

    }
}
