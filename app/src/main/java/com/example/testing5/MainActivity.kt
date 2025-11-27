package com.example.testing5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.testing5.data.db.AppDatabase
import com.example.testing5.data.repository.OfflineRepository
import com.example.testing5.ui.OfflineScreen
import com.example.testing5.ui.OfflineViewModel
import com.example.testing5.ui.OfflineViewModelFactory

//https://drive.google.com/file/d/1vOSUmh3LpEwPQJGvatRpzz4w01vb718Y/view
class MainActivity : ComponentActivity() {

    private lateinit var viewModel: OfflineViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "offline-db"
        ).fallbackToDestructiveMigration().build()

        val repository = OfflineRepository(db.savedPageDao(), applicationContext)

        val factory = OfflineViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[OfflineViewModel::class.java]

        setContent {
            OfflineScreen(viewModel)
        }
    }
}