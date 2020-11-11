
package com.guardiannews.android.testnewsapp

import com.guardiannews.android.testnewsapp.ui.MainActivity
import com.guardiannews.android.testnewsapp.ui.ViewModelModule
import com.guardiannews.android.testnewsapp.ui.news.NewsResultsFragment
import com.guardiannews.android.testnewsapp.framework.network.NewsApiModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NewsApiModule::class, ViewModelModule::class])
interface AppComponent {
  fun inject(mainActivity: MainActivity)
  fun inject(newsResultsFragment: NewsResultsFragment)
}