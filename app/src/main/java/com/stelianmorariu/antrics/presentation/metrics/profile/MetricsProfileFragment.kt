/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation.metrics.profile

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stelianmorariu.antrics.R
import com.stelianmorariu.antrics.domain.dagger.Injectable
import com.stelianmorariu.antrics.domain.model.MetricsProfile
import com.stelianmorariu.antrics.domain.model.StatefulResource
import com.stelianmorariu.antrics.domain.model.Status
import com.stelianmorariu.antrics.presentation.commons.doOnApplyWindowInsets
import com.stelianmorariu.antrics.presentation.commons.loadDeviceImage
import javax.inject.Inject

class MetricsProfileFragment : Fragment(), Injectable, PopupMenu.OnMenuItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var metricsViewModel: MetricsProfileViewModel

    private lateinit var menuBtn: ImageView

    private lateinit var loadingImageView: ImageView

    private lateinit var titleTv: TextView

    private lateinit var motionLayout: MotionLayout

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: MetricsItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_metrics_profile, container, false)

        motionLayout = view.findViewById(R.id.motion_layout)

        view.doOnApplyWindowInsets { v, windowInsets, initialPadding ->
            v.updatePadding(
                top = initialPadding.top + windowInsets.systemWindowInsetTop
            )
        }

        menuBtn = view.findViewById(R.id.btn_menu)
        loadingImageView = view.findViewById(R.id.device_image)
        titleTv = view.findViewById(R.id.title)
        recyclerView = view.findViewById(R.id.recyclerview)

        setupMenu()
        setupRecyclerView()

        metricsViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MetricsProfileViewModel::class.java)

        metricsViewModel.metricsProfile.observe(
            viewLifecycleOwner,
            Observer { statefulMetricsProfile ->
                if (statefulMetricsProfile.status == Status.LOADING) {
                    // the profile should be available already so no loading state should be required
                } else if (statefulMetricsProfile.status == Status.SUCCESS) {
                    loadDeviceImage(statefulMetricsProfile)
                    titleTv.text = statefulMetricsProfile.data!!.deviceName
                    adapter.setItems(statefulMetricsProfile.data.toProfileItemList())
                }
            })

        metricsViewModel.setDeviceBuildCode(Build.MODEL)


        return view
    }


    /**
     * This method will be invoked when a menu item is clicked if the item
     * itself did not already handle the event.
     *
     * @param item the menu item that was clicked
     * @return `true` if the event was handled, `false`
     * otherwise
     */
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_settings -> {
                findNavController().navigate(R.id.action_metricsProfileFragment_to_settingsActivity)
                true
            }
            else -> false
        }
    }

    private fun loadDeviceImage(statefulMetricsProfile: StatefulResource<MetricsProfile>) {
        val imageUrl = statefulMetricsProfile.data?.deviceImageUrl ?: ""
        if (imageUrl.isNullOrBlank()) {
            loadingImageView.loadDeviceImage(R.drawable.generic_device)
        } else {
            loadingImageView.loadDeviceImage(imageUrl)
        }
    }


    private fun setupMenu() {
        menuBtn.setOnClickListener { v ->
            v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
            showPopMenu(v)
        }
    }

    private fun showPopMenu(v: View) {
        PopupMenu(context!!, v).apply {
            // MainActivity implements OnMenuItemClickListener
            setOnMenuItemClickListener(this@MetricsProfileFragment)
            inflate(R.menu.main_menu)
            show()
        }
    }

    private fun setupRecyclerView() {
        adapter = MetricsItemAdapter(context!!)

        val layoutManager = LinearLayoutManager(context)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

}