package com.example.testing5.data.repository

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.testing5.data.db.SavedPageDao
import com.example.testing5.worker.DownloadPageWorker

class OfflineRepository(
    private val dao: SavedPageDao,
    private val context: Context
) {
    fun getPages() = dao.getAll()

    fun saveUrl(url: String) {
        val request = OneTimeWorkRequestBuilder<DownloadPageWorker>()
            .setInputData(workDataOf("url" to url))
            .build()

        WorkManager.getInstance(context).enqueue(request)
    }
}