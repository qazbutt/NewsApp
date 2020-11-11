

package com.guardiannews.android.testnewsapp.ui

import androidx.lifecycle.*
import com.guardiannews.android.testnewsapp.Event
import com.guardiannews.android.testnewsapp.framework.network.NewsRepository
import com.guardiannews.android.testnewsapp.framework.network.model.Result
import com.guardiannews.android.testnewsapp.ui.news.NewsLoadingState
import kotlinx.coroutines.*
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: NewsRepository) :
    ViewModel() {

  private val _popularMoviesLiveData = MutableLiveData<List<com.guardiannews.android.testnewsapp.framework.network.model.Result>>()
  val moviesMediatorData = MediatorLiveData<List<com.guardiannews.android.testnewsapp.framework.network.model.Result>>()
  private var debouncePeriod: Long = 500
  private var searchJob: Job? = null
  private var _searchMoviesLiveData: LiveData<List<com.guardiannews.android.testnewsapp.framework.network.model.Result>>

  val newsLoadingStateLiveData = MutableLiveData<NewsLoadingState>()
  private val _searchFieldTextLiveData = MutableLiveData<String>()

  private val _navigateToDetails = MutableLiveData<Event<String>>()
  val navigateToDetails: LiveData<Event<String>>
    get() = _navigateToDetails

  init {
    _searchMoviesLiveData = Transformations.switchMap(_searchFieldTextLiveData) {
      fetchMovieByQuery(it)
    }

    moviesMediatorData.addSource(_popularMoviesLiveData) {
      moviesMediatorData.value = it
    }

    moviesMediatorData.addSource(_searchMoviesLiveData) {
      moviesMediatorData.value = it
    }
  }

  fun onFragmentReady() {
    if (_popularMoviesLiveData.value.isNullOrEmpty()) {
      fetchPopularMovies()
    }
  }

  fun onSearchQuery(query: String) {
    searchJob?.cancel()
    searchJob = viewModelScope.launch {
      delay(debouncePeriod)
      if (query.length > 2) {
        _searchFieldTextLiveData.value = query
      }
    }
  }

  private fun fetchPopularMovies() {
    newsLoadingStateLiveData.value = NewsLoadingState.LOADING
    viewModelScope.launch(Dispatchers.IO) {
      try {
        val movies = repository.fetchLatestUkNews()
        _popularMoviesLiveData.postValue(movies)
        newsLoadingStateLiveData.postValue(NewsLoadingState.LOADED)
      } catch (e: Exception) {
        newsLoadingStateLiveData.postValue(NewsLoadingState.INVALID_API_KEY)
      }
    }
  }

  private fun fetchMovieByQuery(query: String): LiveData<List<Result>> {
    val liveData = MutableLiveData<List<com.guardiannews.android.testnewsapp.framework.network.model.Result>>()
    viewModelScope.launch(Dispatchers.IO) {
      try {
        //1
        withContext(Dispatchers.Main) {
          newsLoadingStateLiveData.value = NewsLoadingState.LOADING
        }

        val movies = repository.fetchMovieByQuery(query)
        liveData.postValue(movies)

        //2
        newsLoadingStateLiveData.postValue(NewsLoadingState.LOADED)
      } catch (e: Exception) {
        //3
        newsLoadingStateLiveData.postValue(NewsLoadingState.INVALID_API_KEY)
      }
    }
    return liveData
  }

  fun onMovieClicked(movie: com.guardiannews.android.testnewsapp.framework.network.model.Result) {
    movie.webTitle?.let {
      _navigateToDetails.value = Event(it)
    }
  }

  override fun onCleared() {
    super.onCleared()
    searchJob?.cancel()
  }
}