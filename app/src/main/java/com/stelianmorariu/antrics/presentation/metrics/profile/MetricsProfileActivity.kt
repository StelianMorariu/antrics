/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation.metrics.profile

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stelianmorariu.antrics.R
import com.stelianmorariu.antrics.domain.model.Status
import com.stelianmorariu.antrics.presentation.commons.loadDeviceImage
import javax.inject.Inject


class MetricsProfileActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var metricsViewModel: MetricsProfileViewModel

    private lateinit var loadingImageView: ImageView

    private lateinit var titleTv: TextView

    private lateinit var motionLayout: MotionLayout

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: MetricsItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // override the default transitions because we create the circular reveal manually
        overridePendingTransition(R.anim.anim_no_translate, R.anim.anim_no_translate)

        setContentView(R.layout.activity_metrics_profile)

        motionLayout = findViewById(R.id.motion_layout)
        loadingImageView = findViewById(R.id.device_image)
        titleTv = findViewById(R.id.title)
        recyclerView = findViewById(R.id.recyclerview)

        setupRecyclerView()
        setupMotionLayout()

        metricsViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MetricsProfileViewModel::class.java)

        metricsViewModel.metricsProfile.observe(this, Observer { statefulMetricsProfile ->
            if (statefulMetricsProfile.status == Status.LOADING) {
                // the profile should be available already so no loading state should be required
            } else if (statefulMetricsProfile.status == Status.SUCCESS) {
                // TODO: show device image
                loadingImageView.loadDeviceImage(R.drawable.pixel_xl_2_thumb)
                titleTv.text = statefulMetricsProfile.data!!.deviceName
                adapter.setItems(statefulMetricsProfile.data.toProfileItemList())
            }
        })

        metricsViewModel.setDeviceBuildCode(Build.MODEL)
    }


    private fun setupRecyclerView() {
        adapter = MetricsItemAdapter(this)

        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun setupMotionLayout() {
        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun allowsTransition(p0: MotionScene.Transition?): Boolean {
                return true
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout?, startId: Int, endId: Int, progress: Float) {
                var statusBarColour = 0
                if (startId == R.id.cs_collapsed) {
                    statusBarColour = ColorUtils.setAlphaComponent(
                        ContextCompat.getColor(this@MetricsProfileActivity, R.color.lightGray),
                        (progress * 255).toInt()
                    )
                } else if (startId == R.id.cs_expanded) {
                    statusBarColour = ColorUtils.setAlphaComponent(
                        ContextCompat.getColor(this@MetricsProfileActivity, R.color.neutralWhite),
                        (progress * 255).toInt()
                    )
                }

                window.statusBarColor = statusBarColour
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            }

        })
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MetricsProfileActivity::class.java)
        }
    }
}
