/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.domain.model

import com.stelianmorariu.antrics.BuildConfig


/**
 * Configuration class used to read injected values from the CI.
 */
class Configuration {
    val logsEnabled = BuildConfig.LOGS_ENABLED

}