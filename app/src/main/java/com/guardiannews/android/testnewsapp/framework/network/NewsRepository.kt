
package com.guardiannews.android.testnewsapp.framework.network

import android.util.Log
import com.guardiannews.android.testnewsapp.framework.network.model.Result
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsService: NewsService) {

  suspend fun fetchLatestUkNews(): List<com.guardiannews.android.testnewsapp.framework.network.model.Result>? {
    Log.d("TAG", "fetchUkNews")
    val deferredResponse = newsService.fetchLatestNewsAsync().await()

    return if (deferredResponse.isSuccessful) {
      deferredResponse.body()?.response?.results
    } else {
      throw Exception()
    }
  }

  suspend fun fetchMovieByQuery(queryText: String): List<Result>? {
    val deferredResponse = newsService.fetchNewsByQueryAsync(queryText).await()

    return if (deferredResponse.isSuccessful) {
      deferredResponse.body()?.response?.results
    } else {
      throw Exception()
    }
  }
}