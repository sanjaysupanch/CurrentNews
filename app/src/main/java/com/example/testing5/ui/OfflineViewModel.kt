package com.example.testing5.ui

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testing5.data.repository.OfflineRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File

class OfflineViewModel(private val repo: OfflineRepository) : ViewModel() {

    val pages = repo.getPages().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    var url by mutableStateOf("")

    fun openPage(filePath: String, context: Context) {
        val file = File(filePath)

        val uri = FileProvider.getUriForFile(
            context,
            context.packageName + ".provider",   // same as manifest authorities
            file
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "text/html")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(intent)
    }

    fun save() = viewModelScope.launch {
        repo.saveUrl(url)
        url = ""
    }
}
