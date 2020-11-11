

package com.guardiannews.android.testnewsapp.framework.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://content.guardianapis.com/"
private const val GUARDIAN_API_KEY = "00fce9b2-1201-43cc-a77e-79ef375ca6dd"

@Module
class NewsApiModule {



  @Provides
  @Singleton
  fun provideInterceptor(): Interceptor {
    return Interceptor { chain ->
      val newUrl = chain.request().url()
          .newBuilder()
          .addQueryParameter("api-key", GUARDIAN_API_KEY)
          .addQueryParameter("show-fields", "trailText,thumbnail")
          .addQueryParameter("order-by", "newest")
          .build()

      val newRequest = chain.request()
          .newBuilder()
          .url(newUrl)
          .build()

      chain.proceed(newRequest)
    }
  }

  @Provides
  @Singleton
  fun provideWeatherApiClient(authInterceptor: Interceptor): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()
  }

  @Provides
  @Singleton
  fun provideMoshi(): Moshi {
    return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(moshi: Moshi, randomUserApiClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(randomUserApiClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()
  }

  @Provides
  @Singleton
  fun providesWeatherApi(retrofit: Retrofit): NewsService {
    return retrofit.create(NewsService::class.java)
  }
}