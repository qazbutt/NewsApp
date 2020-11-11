

package com.guardiannews.android.testnewsapp.framework.network

import com.guardiannews.android.testnewsapp.framework.network.model.ApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

  @GET("search")
  fun fetchLatestNewsAsync(): Deferred<Response<ApiResponse>>

  @GET("search")
  fun fetchNewsByQueryAsync(@Query("q") query: String): Deferred<Response<ApiResponse>>
}