package com.example.news.viewmodal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.data.modal.Article
import com.example.news.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    // StateFlow for UI state management
    private val _articles = MutableStateFlow<Result<List<Article>>?>(null)
    val articles: StateFlow<Result<List<Article>>?> = _articles

    // Flow for real-time updates of saved articles
    private val _savedArticles = repository.getSavedArticles()
    val savedArticles: Flow<List<Article>> = _savedArticles

    // SEARCH QUERY
    private val _query = MutableStateFlow("")
    fun onQueryChanged(text: String) {
        _query.value = text
    }

    // SEARCH RESULTS
    private val _searchedArticles =
        MutableStateFlow<Result<List<Article>>?>(null)
    val searchedArticles: StateFlow<Result<List<Article>>?> =
        _searchedArticles.asStateFlow()

    init {
        fetchNews()
        observeSearch()
    }

    fun fetchNews() {
        viewModelScope.launch {
            viewModelScope.launch {
                repository.fetchNews().collectLatest { result ->
                    _articles.value = result
                }
            }
        }
    }

    // Save article
    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.saveArticle(article)
    }

    // Delete article
    fun deleteArticle(articleUrl: String) = viewModelScope.launch {
        repository.deleteArticle(articleUrl)
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeSearch() {
        viewModelScope.launch {
            _query
                .debounce(400)
                .map { it.trim() }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    if (query.isBlank()) {
                        flowOf(Result.success(emptyList()))
                    } else {
                        repository.searchNews(query)
                    }
                }
                .collectLatest { result ->
                    _searchedArticles.value = result
                }
        }
    }
}