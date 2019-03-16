/*
 * Copyright ©  2019 Stelian Morariu. All rights reserved.
 */

package com.stelianmorariu.antrics.presentation

import android.util.Log
import timber.log.Timber

class ReleaseLogTree : Timber.Tree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean = priority == Log.ERROR


    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (isLoggable(tag, priority)) {
            if (message.length < MAX_LOG_LENGTH) {
                when (priority) {
                    Log.ASSERT -> Log.wtf(tag, message)
                    else -> Log.println(priority, tag, message)
                }

                return
            }

            val length = message.length
            var i = 0
            while (i < length) {
                var newLine = message.indexOf('\n', i)
                newLine = if (newLine != -1) newLine else length
                do {
                    val end = Math.min(newLine, i + MAX_LOG_LENGTH)
                    val part = message.substring(i, end)
                    if (priority == Log.ASSERT) {
                        Log.wtf(tag, part)
                    } else {
                        Log.println(priority, tag, part)
                    }
                    i = end
                } while (i < newLine)
                i++
            }
        }
    }

    companion object {

        private const val MAX_LOG_LENGTH = 4000
    }
}