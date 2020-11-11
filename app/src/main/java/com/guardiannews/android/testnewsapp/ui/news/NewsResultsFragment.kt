

package com.guardiannews.android.testnewsapp.ui.news

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.guardiannews.android.testnewsapp.R
import com.guardiannews.android.testnewsapp.TestApplication.Companion.application
import com.guardiannews.android.testnewsapp.connectivity.ConnectivityLiveData
import com.guardiannews.android.testnewsapp.framework.network.model.Result
import com.guardiannews.android.testnewsapp.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_news_list.*
import javax.inject.Inject

class NewsResultsFragment : Fragment(R.layout.fragment_news_list),
    NewsAdapter.NewsClickListener {

  private lateinit var mainViewModel: MainViewModel
  private lateinit var newsAdapter: NewsAdapter
  private lateinit var connectivityLiveData: ConnectivityLiveData

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val searchTextWatcher = object : TextWatcher {
    override fun afterTextChanged(editable: Editable?) {
      mainViewModel.onSearchQuery(editable.toString())
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    connectivityLiveData = ConnectivityLiveData(application)

    application.appComponent.inject(this)
    mainViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(
        MainViewModel::class.java)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    initialiseObservers()
    initialiseUIElements()
  }

  private fun initialiseObservers() {
    mainViewModel.moviesMediatorData.observe(viewLifecycleOwner, Observer {
      newsAdapter.updateData(it)
    })

    mainViewModel.newsLoadingStateLiveData.observe(viewLifecycleOwner, Observer {
      onMovieLoadingStateChanged(it)
    })

    connectivityLiveData.observe(viewLifecycleOwner, Observer { isAvailable ->
      when (isAvailable) {
        true -> {
          mainViewModel.onFragmentReady()
          statusButton.visibility = View.GONE
          newsRecyclerView.visibility = View.VISIBLE
          searchEditText.visibility = View.VISIBLE
        }
        false -> {
          statusButton.visibility = View.VISIBLE
          newsRecyclerView.visibility = View.GONE
          searchEditText.visibility = View.GONE
        }
      }
    })

    mainViewModel.navigateToDetails.observe(viewLifecycleOwner, Observer {
      it?.getContentIfNotHandled()?.let { movieTitle ->
        findNavController().navigate(
            NewsResultsFragmentDirections.actionMovieClicked(movieTitle)
        )
      }
    })
  }

  private fun initialiseUIElements() {
    searchEditText.addTextChangedListener(searchTextWatcher)
    newsAdapter = NewsAdapter(this)
    newsRecyclerView.apply {
      adapter = newsAdapter
      hasFixedSize()
    }
  }

  override fun onMovieClicked(movie: Result) {
    mainViewModel.onMovieClicked(movie)
  }

  private fun onMovieLoadingStateChanged(state: NewsLoadingState) {
    when (state) {
      NewsLoadingState.LOADING -> {
        statusButton.visibility = View.GONE
        newsRecyclerView.visibility = View.GONE
        loadingProgressBar.visibility = View.VISIBLE
      }
      NewsLoadingState.LOADED -> {
        connectivityLiveData.value?.let {
          if (it) {
            statusButton.visibility = View.GONE
            newsRecyclerView.visibility = View.VISIBLE
          } else {
            statusButton.visibility = View.VISIBLE
            newsRecyclerView.visibility = View.GONE
          }
        }
        loadingProgressBar.visibility = View.GONE
      }
      NewsLoadingState.ERROR -> {
        statusButton.visibility = View.VISIBLE
        context?.let {
          statusButton.setCompoundDrawables(
              null, ContextCompat.getDrawable(it, R.drawable.no_internet), null,
              null)
        }
        newsRecyclerView.visibility = View.GONE
        loadingProgressBar.visibility = View.GONE
      }
      NewsLoadingState.INVALID_API_KEY -> {
        statusButton.visibility = View.VISIBLE
        statusButton.text = getString(R.string.error_string)
        statusButton.setCompoundDrawables(null, null, null, null)
        newsRecyclerView.visibility = View.GONE
        loadingProgressBar.visibility = View.GONE
      }
    }
  }
}