package com.mad.app

import android.app.Application
import timber.log.Timber

class MADApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Timber for structured logging
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
                return String.format(
                    "MAD:%s:%s",
                    element.fileName,
                    element.lineNumber
                )
            }
        })

        Timber.d("MADApplication initialized successfully")
    }
}
