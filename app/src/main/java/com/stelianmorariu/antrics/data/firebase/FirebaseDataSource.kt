/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.stelianmorariu.antrics.domain.model.MetricsProfile
import io.reactivex.Single
import timber.log.Timber


class FirebaseDataSource {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


    /**
     * Get device metadata that includes marketing name.
     */
    fun getDeviceMetaData(deviceModel: String): Single<FirebaseDeviceMetadata> {
        return firebaseAnonymousAuth()
            .flatMap {
                Single.create<FirebaseDeviceMetadata> { emitter ->
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
    }

    fun getDeviceImage(deviceMarketingName: String): Single<String> {
        return firebaseAnonymousAuth()
            .flatMap {
                Single.create<String> { emitter ->
                    val deviceMetaDataQuery = firestore
                        .collection(DEVICE_IMAGES_COLLECTION_NAME)
                        .whereEqualTo(deviceMarketingName, deviceMarketingName)

                    deviceMetaDataQuery.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                        if (firebaseFirestoreException != null) {
                            Timber.e(firebaseFirestoreException)
                            emitter.onError(firebaseFirestoreException)
                        }

                        querySnapshot?.let { snapshot ->
                            if (snapshot.isEmpty) {
                                emitter.onError(RuntimeException("Metadata not found for $deviceMarketingName"))
                            } else {
                                val metadata = snapshot.toObjects(FirebaseDeviceImage::class.java)
                                emitter.onSuccess(metadata.first().image_url)
                            }
                        }
                    }
                }
            }
    }


    fun saveDeviceMetrics(deviceMetricsPprofile: MetricsProfile) {

    }


    /**
     * Perform anonymous authentication to allow access to the Firebase data base.
     */
    private fun firebaseAnonymousAuth(): Single<FirebaseUser> {
        return Single.create<FirebaseUser> { emitter ->
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                emitter.onSuccess(currentUser)
            } else {
                firebaseAuth.signInAnonymously()
                    .addOnCompleteListener { authTask ->
                        if (authTask.isSuccessful) {
                            emitter.onSuccess(firebaseAuth.currentUser!!)
                        } else {
                            emitter.onError(RuntimeException("Can't perform anonymous sign in: ${authTask.exception?.localizedMessage}"))
                        }
                    }
            }
        }
    }


    companion object {
        private const val DEVICES_COLLECTION_NAME = "devices"
        private const val DEVICE_IMAGES_COLLECTION_NAME = "device_images"

        private const val DEVICE_MODEL_KEY = "device_model"
        private const val DEVICE_MARKETING_NAME_KEY = "device_marketing_name"

    }
}