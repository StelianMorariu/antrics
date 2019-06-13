/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.stelianmorariu.antrics.domain.model.MetricsProfile
import io.reactivex.Single
import timber.log.Timber


class FirebaseDataSource {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    fun getDeviceMetaData(deviceModel: String): Single<FirebaseDeviceMetadata> {
        return Single.create<FirebaseDeviceMetadata> { emitter ->
            val deviceMetaDataQuery = firestore
                .collection(DEVICES_COLLECTION_NAME)
                .whereEqualTo(DEVICE_MODEL_KEY, deviceModel)

            deviceMetaDataQuery.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Timber.e(firebaseFirestoreException)
                    emitter.onError(firebaseFirestoreException)
                }

                querySnapshot?.let { snapshot ->
                    if (snapshot.isEmpty) {
                        emitter.onError(RuntimeException("Metadata not found for $deviceModel"))
                    } else {
                        val metadata = snapshot.toObjects(FirebaseDeviceMetadata::class.java)
                        emitter.onSuccess(metadata.first())
                    }
                }
            }
        }

    }

    fun saveDeviceMetrics(deviceMetricsPprofile: MetricsProfile) {

    }

    companion object {
        private const val DEVICES_COLLECTION_NAME = "devices"
        private const val DEVICE_IMAGES_COLLECTION_NAME = ""

        private const val DEVICE_MODEL_KEY = "device_model"

    }
}