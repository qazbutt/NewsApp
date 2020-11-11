
package com.guardiannews.android.testnewsapp.ui.newsdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.guardiannews.android.testnewsapp.R
import kotlinx.android.synthetic.main.fragment_news_details.*

class NewsDetailFragment : Fragment(R.layout.fragment_news_details) {

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    initialiseUIElements()
  }
                                                                                                                                      
  private fun initialiseUIElements() {
    arguments?.let {
      newsTitleTextView.text = NewsDetailFragmentArgs.fromBundle(it).movieName
    }
  }
}