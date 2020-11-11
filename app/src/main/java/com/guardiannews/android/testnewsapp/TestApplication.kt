
package com.guardiannews.android.testnewsapp

import android.app.Application
import com.guardiannews.android.testnewsapp.framework.network.NewsApiModule

class TestApplication : Application() {

  lateinit var appComponent: AppComponent

  private fun initAppComponent(app: TestApplication): AppComponent {
    return DaggerAppComponent.builder()
        .appModule(AppModule(app))
        .newsApiModule(NewsApiModule()).build()
  }

  companion object {
    @get:Synchronized
    lateinit var application: TestApplication
      private set
  }

  override fun onCreate() {
    super.onCreate()
    application = this
    appComponent = initAppComponent(this)
  }
}