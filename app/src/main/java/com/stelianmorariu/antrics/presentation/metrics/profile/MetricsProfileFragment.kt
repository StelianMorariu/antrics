/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation.metrics.profile

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stelianmorariu.antrics.R
import com.stelianmorariu.antrics.domain.dagger.Injectable
import com.stelianmorariu.antrics.domain.model.MetricsProfile
import com.stelianmorariu.antrics.domain.model.StatefulResource
import com.stelianmorariu.antrics.domain.model.Status
import com.stelianmorariu.antrics.presentation.commons.loadDeviceImage
import timber.log.Timber
import javax.inject.Inject

class MetricsProfileFragment : Fragment(), Injectable {

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

        metricsViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MetricsProfileViewModel::class.java)

        metricsViewModel.metricsProfile.observe(viewLifecycleOwner, Observer { statefulMetricsProfile ->
            if (statefulMetricsProfile.status == Status.LOADING) {
                // the profile should be available already so no loading state should be required
            } else if (statefulMetricsProfile.status == Status.SUCCESS) {
                loadDeviceImage(statefulMetricsProfile)
                titleTv.text = statefulMetricsProfile.data!!.deviceName
                adapter.setItems(statefulMetricsProfile.data.toProfileItemList())
            }
        })

        metricsViewModel.setDeviceBuildCode(Build.MODEL)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_metrics_profile, container, false)

        motionLayout = view.findViewById(R.id.motion_layout)
        loadingImageView = view.findViewById(R.id.device_image)
        titleTv = view.findViewById(R.id.title)
        recyclerView = view.findViewById(R.id.recyclerview)

        setupRecyclerView()
        setupMotionLayout()


        return view
    }

    private fun loadDeviceImage(statefulMetricsProfile: StatefulResource<MetricsProfile>) {
        val imageUrl = statefulMetricsProfile.data?.deviceImageUrl ?: ""
        if (imageUrl.isNullOrBlank()) {
            loadingImageView.loadDeviceImage(R.drawable.generic_device)
        } else {
            loadingImageView.loadDeviceImage(imageUrl)
        }
    }


    private fun setupRecyclerView() {
        adapter = MetricsItemAdapter(context!!)

        val layoutManager = LinearLayoutManager(context)

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

            override fun onTransitionStarted(p0: MotionLayout?, startId: Int, endId: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout?, startId: Int, endId: Int, progress: Float) {
                val computedAlpha = (progress * 255).toInt()
                Timber.d("Transition change called with progress: $progress, image view alpha: ${loadingImageView.alpha} and computed alpha: $computedAlpha")

                activity?.let {
                    val statusBarColour = ColorUtils.setAlphaComponent(
                        ContextCompat.getColor(it, R.color.white),
                        computedAlpha
                    )
                    it.window.statusBarColor = statusBarColour
                }

            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            }

        })
    }
}