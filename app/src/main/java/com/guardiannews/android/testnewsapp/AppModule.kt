
package com.guardiannews.android.testnewsapp

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val newsApplication: Application) {

  @Provides
  @Singleton
  fun provideContext(): Application = newsApplication
}