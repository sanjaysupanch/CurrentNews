package com.example.news

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class NewsApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Volatile
    private var _workManagerConfiguration: Configuration? = null

    override fun onCreate() {
        super.onCreate()
        // Hilt injection happens during super.onCreate()
        // Now we can safely create the configuration
        _workManagerConfiguration = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    override val workManagerConfiguration: Configuration
        get() {
            return _workManagerConfiguration ?: throw IllegalStateException(
                "WorkManagerConfiguration accessed before Application.onCreate() completed. " +
                        "This should not happen as Application.onCreate() completes before Activity.onCreate()"
            )
        }
}
