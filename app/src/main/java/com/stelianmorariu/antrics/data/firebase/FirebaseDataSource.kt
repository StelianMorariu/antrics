/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.data.firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.stelianmorariu.antrics.domain.model.MetricsProfile
import com.stelianmorariu.antrics.domain.model.StatefulResource
import timber.log.Timber


class FirebaseDataSource {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    fun getDeviceMetaData(deviceModel: String): LiveData<StatefulResource<FirebaseDeviceMetadata>> {
        val result: MutableLiveData<StatefulResource<FirebaseDeviceMetadata>> = MutableLiveData()

        result.postValue(StatefulResource.loading(null))

        val deviceMetaDataQuery = firestore
            .collection(DEVICES_COLLECTION_NAME)
            .whereEqualTo(DEVICE_MODEL_KEY, deviceModel)

        deviceMetaDataQuery.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Timber.e(firebaseFirestoreException)
                result.postValue(StatefulResource.error(firebaseFirestoreException.toString(), null))
            }

            querySnapshot?.let { snapshot ->
                if (snapshot.isEmpty) {
                    result.postValue(StatefulResource.error("No result found for $deviceModel", null))
                } else {
                    val metadata = snapshot.toObjects(FirebaseDeviceMetadata::class.java)
                    result.postValue(StatefulResource.success(metadata.first()))
                }
            }
        }

        return result
    }

    fun saveDeviceMetrics(deviceMetricsPprofile: MetricsProfile) {

    }

    companion object {
        private const val DEVICES_COLLECTION_NAME = "devices"
        private const val DEVICE_IMAGES_COLLECTION_NAME = ""

        private const val DEVICE_MODEL_KEY = "device_model"

    }
}