
package com.guardiannews.android.testnewsapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.guardiannews.android.testnewsapp.TestApplication
import com.guardiannews.android.testnewsapp.R
import com.guardiannews.android.testnewsapp.ui.about.AboutDialogActivity
import java.util.*
import javax.inject.Inject

/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {

  private lateinit var mainViewModel: MainViewModel

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  override fun onCreate(savedInstanceState: Bundle?) {
    // Switch to AppTheme for displaying the activity
    setTheme(R.style.AppTheme)


    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    TestApplication.application.appComponent.inject(this)
    mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    val inflater: MenuInflater = menuInflater
    inflater.inflate(R.menu.main_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.aboutMenuItem -> {
        startActivity(Intent(this, AboutDialogActivity::class.java))
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}