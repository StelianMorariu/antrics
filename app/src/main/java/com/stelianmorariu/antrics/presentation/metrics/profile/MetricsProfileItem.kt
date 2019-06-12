/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation.metrics.profile

import com.stelianmorariu.antrics.R
import com.stelianmorariu.antrics.domain.model.MetricsProfile


enum class ProfileItemType(val iconResId: Int, val displayName: String) {
    SCREEN_SIZE_PX(R.drawable.ic_screen_size, "Screen size(px)"),
    SCREEN_SIZE_DP(R.drawable.ic_screen_size, "Screen size(dp)"),
    DENSITY(R.drawable.ic_idensity, "Density"),
    ASPECT_RATIO(R.drawable.ic_aspect_ratio, "Aspect ratio"),
    DENSITY_BUCKET(R.drawable.ic_idensity, "Density qualifier")

}

data class MetricsProfileItem(
    val itemType: ProfileItemType,
    val itemValue: String
)

inline fun MetricsProfile.toProfileItemList(): List<MetricsProfileItem> {
    return listOf(
        MetricsProfileItem(ProfileItemType.SCREEN_SIZE_PX, "${this.widthPx}x${this.heightPx}"),
        MetricsProfileItem(ProfileItemType.SCREEN_SIZE_DP, "${this.widthDp}x${this.heightDp}"),
        MetricsProfileItem(ProfileItemType.DENSITY, "${this.densityDpi}"),
        MetricsProfileItem(ProfileItemType.DENSITY_BUCKET, "${this.densityBucket}")
    )
}