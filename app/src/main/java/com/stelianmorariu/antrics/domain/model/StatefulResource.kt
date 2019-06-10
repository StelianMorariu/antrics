/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.domain.model


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class StatefulResource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): StatefulResource<T> {
            return StatefulResource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): StatefulResource<T> {
            return StatefulResource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): StatefulResource<T> {
            return StatefulResource(Status.LOADING, data, null)
        }
    }
}
