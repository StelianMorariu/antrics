/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation.metrics.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stelianmorariu.antrics.R
import kotlinx.android.synthetic.main.list_item_profile_metric_data.view.*
import java.util.*

class MetricsItemAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater: LayoutInflater =
        this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val items = ArrayList<MetricsProfileItem>()


    fun setItems(items: List<MetricsProfileItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = inflater.inflate(R.layout.list_item_profile_metric_data, parent, false)
        return MetricItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        bindMetricDataItem(holder as MetricItemViewHolder, item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun bindMetricDataItem(holder: MetricItemViewHolder, item: MetricsProfileItem) {
        holder.itemValue?.text = item.itemValue

    }


    internal class MetricItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iconImageView: ImageView? = itemView.icon_iv
        var label: TextView? = itemView.label_tv
        var itemValue: TextView? = itemView.value_tv

    }

}

