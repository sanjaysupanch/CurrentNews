package com.example.news.data.worker

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.news.data.repository.NewsRepository
import com.example.news.utils.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NewsSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repository: NewsRepository
) : CoroutineWorker(context, params) {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {
        return try {
            val latest = repository.fetchLatestNewsOnce().getOrNull().orEmpty()
            val oldUrls = repository.getSavedArticleUrls()

            val newOnes = latest.filter { it.url !in oldUrls }

            if (newOnes.isNotEmpty()) {
                repository.saveArticles(newOnes)

                NotificationHelper.showNotification(
                    context = applicationContext,
                    title = "News Update",
                    message = "${newOnes.size} new articles available!"
                )
            }

            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }
}
