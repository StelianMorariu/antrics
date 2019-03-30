/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation.metrics.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stelianmorariu.antrics.R


class MetricsProfileActivity : AppCompatActivity() {

    private lateinit var loadingImageView: ImageView

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

        recyclerView = findViewById(R.id.recyclerview)

//        loadingImageView.setOnClickListener { stopLoading = false}

    }

    override fun onStart() {
        super.onStart()
        setupRecyclerView()

        adapter.setItems(getDummyData())
    }

    fun getDisplayMetris(): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }


    private fun getDummyData(): List<MetricsProfileItem> {
        return listOf(
            MetricsProfileItem(0, "hjkm", "765"),
            MetricsProfileItem(0, "hjkm", "rtfed"),
            MetricsProfileItem(0, "hjkm", " bvfd"),
            MetricsProfileItem(0, "hjkm", "rty"),
            MetricsProfileItem(0, "hjkm", "uyt"),
            MetricsProfileItem(0, "hjkm", "nbvc"),
            MetricsProfileItem(0, "hjkm", "23456"),
            MetricsProfileItem(0, "hjkm", "o"),
            MetricsProfileItem(0, "hjkm", "rtfed")
        )
    }

    private fun setupRecyclerView() {
        adapter = MetricsItemAdapter(this)

        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MetricsProfileActivity::class.java)
        }
    }
}
