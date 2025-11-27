package com.example.testing5.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testing5.data.repository.OfflineRepository

class OfflineViewModelFactory(
    private val repository: OfflineRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OfflineViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OfflineViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}