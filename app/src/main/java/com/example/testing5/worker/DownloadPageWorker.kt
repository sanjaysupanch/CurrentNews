package com.example.testing5.worker

import android.content.Context
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.testing5.data.db.AppDatabase
import com.example.testing5.data.db.SavedPageEntity
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.util.Date

class DownloadPageWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        val url = inputData.getString("url") ?: return Result.failure()

        val body = OkHttpClient().newCall(
            Request.Builder().url(url).build()
        ).execute().body ?: return Result.failure()

        val file = File(applicationContext.filesDir, "page_${System.currentTimeMillis()}.html")
        file.outputStream().use { body.byteStream().copyTo(it) }

        val db = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java, "offline-db"
        ).fallbackToDestructiveMigration().build()

        val entity = SavedPageEntity(
            url = url,
            title = url,
            date = Date().toString(),
            filePath = file.absolutePath
        )

        db.savedPageDao().insert(entity)

        return Result.success()
    }
}
