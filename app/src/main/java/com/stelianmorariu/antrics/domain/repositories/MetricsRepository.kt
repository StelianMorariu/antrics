/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stelianmorariu.antrics.data.firebase.FirebaseDataSource
import com.stelianmorariu.antrics.domain.model.LocalDeviceInfo
import com.stelianmorariu.antrics.domain.model.MetricsProfile
import com.stelianmorariu.antrics.domain.model.StatefulResource
import com.stelianmorariu.antrics.domain.rx.SchedulersProvider
import com.stelianmorariu.antrics.domain.rx.transformers.SingleWorkerTransformer
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MetricsRepository @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource,
    private val schedulerProvider: SchedulersProvider
) {


    /**
     * Save profile here temprorarily until local persistence is implemented
     */
    private var tempMetricsProfile: MetricsProfile? = null

    /**
     * Generate a new [MetricsProfile] based on [LocalDeviceInfo] if a profile doesn't already exist.
     */
    fun generateProfileIfRequired(localDeviceInfo: LocalDeviceInfo): Single<MetricsProfile> {
        return Single.create { emitter ->
            if (tempMetricsProfile == null) {
                firebaseDataSource.getDeviceMetaData(localDeviceInfo.buildCode)
                    .compose(SingleWorkerTransformer(schedulerProvider))
                    .subscribe({ metadata ->

                        tempMetricsProfile = MetricsProfile(
                            localDeviceInfo.buildCode,
                            metadata.marketing_name,
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

                        // TODO: save to local DB

                        emitter.onSuccess(tempMetricsProfile!!)
                    },
                        { error ->
                            Timber.e(error)
                            tempMetricsProfile = MetricsProfile(
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

                            // TODO: save to local DB ?
                            emitter.onSuccess(tempMetricsProfile!!)
//                            emitter.onError(error)
//                            result.value = StatefulResource.success(tempMetricsProfile)
                        })
            }
        }
    }

    /**
     * Get the [MetricsProfile] for the given device build code.
     */
    fun getDeviceMetricsProfile(deviceBuildCode: String): LiveData<StatefulResource<MetricsProfile>> {

        val result = MutableLiveData<StatefulResource<MetricsProfile>>()
        result.value = StatefulResource.loading(null)

        // FIXME: load profile based on buildcode, from DB if it exists or from Firebase

        result.value = StatefulResource.success(tempMetricsProfile)


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