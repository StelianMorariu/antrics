/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.stelianmorariu.antrics.domain.model.LocalDeviceInfo
import com.stelianmorariu.antrics.domain.model.MetricsProfile
import com.stelianmorariu.antrics.domain.model.StatefulResource


class MetricsRepository constructor() {


    fun getDeviceMetricsProfile(localDeviceInfo: LocalDeviceInfo): LiveData<StatefulResource<MetricsProfile>> {

        val result = MediatorLiveData<StatefulResource<MetricsProfile>>()
        result.value = StatefulResource.loading(null)

//        val dbSource = loadFromDb()
//        result.addSource(dbSource) { data ->
//            result.removeSource(dbSource)
//            if (shouldFetch(data)) {
//                fetchFromNetwork(dbSource)
//            } else {
//                result.addSource(dbSource) { newData ->
//                    setValue(Resource.success(newData))
//                }
//            }
//        }


        val tempProfile = MetricsProfile(
            localDeviceInfo.buildCode,
            localDeviceInfo.buildCode,
            localDeviceInfo.density,
            localDeviceInfo.densityDpi.toInt(),
            getDensityBucket(localDeviceInfo.density),
            (localDeviceInfo.heightPixels / localDeviceInfo.widthPixels).toFloat(),
            "long",
            localDeviceInfo.widthPixels,
            localDeviceInfo.heightPixels,
            Math.round(localDeviceInfo.widthPixels / localDeviceInfo.density),
            Math.round(localDeviceInfo.heightPixels / localDeviceInfo.density)
        )

        result.value = StatefulResource.success(tempProfile)


        return result
    }


    private fun getDensityBucket(densityQualifier: Float): String {
        return when {
            densityQualifier <= DENSITY_LDPI_QUALIFIER -> DENSITY_LDPI_LABEL
            densityQualifier <= DENSITY_MDPI_QUALIFIER -> DENSITY_MDPI_LABEL
            densityQualifier <= DENSITY_HDPI_QUALIFIER -> DENSITY_HDPI_LABEL
            densityQualifier <= DENSITY_XHDPI_QUALIFIER -> DENSITY_XHDPI_LABEL
            densityQualifier <= DENSITY_XXHDPI_QUALIFIER -> DENSITY_XXHDPI_LABEL
            densityQualifier <= DENSITY_XXXHDPI_QUALIFIER -> DENSITY_XXXHDPI_LABEL

            else -> DENSITY_HDPI_LABEL
        }
    }


    companion object {
        private const val DENSITY_LDPI_LABEL = "ldpi"
        private const val DENSITY_MDPI_LABEL = "mdpi"
        private const val DENSITY_HDPI_LABEL = "hdpi"
        private const val DENSITY_XHDPI_LABEL = "xhdpi"
        private const val DENSITY_XXHDPI_LABEL = "xxhdpi"
        private const val DENSITY_XXXHDPI_LABEL = "xxxhdpi"

        private const val DENSITY_LDPI_QUALIFIER = 0.75F
        private const val DENSITY_MDPI_QUALIFIER = 1.0F
        private const val DENSITY_HDPI_QUALIFIER = 1.5F
        private const val DENSITY_XHDPI_QUALIFIER = 2.0F
        private const val DENSITY_XXHDPI_QUALIFIER = 3.0F
        private const val DENSITY_XXXHDPI_QUALIFIER = 3.5F
    }

}