/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.data.firebase

data class FirebaseDeviceMetadata(
    var device_model: String,
    var device_code: String,
    var marketing_name: String,
    var retail_branding: String
) {
    @JvmOverloads
    constructor() : this("", "", "", "")
}

data class FirebaseDeviceImage(
    var device_marketing_name: String,
    var image_url: String
) {
    @JvmOverloads
    constructor() : this("", "")
}