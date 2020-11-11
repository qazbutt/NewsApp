


package com.guardiannews.android.testnewsapp.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.guardiannews.android.testnewsapp.R
import com.guardiannews.android.testnewsapp.framework.network.model.Result
import kotlinx.android.synthetic.main.line_item_news.view.*

class NewsAdapter(
    private val newsClickListener: NewsClickListener) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

  private var data = mutableListOf<Result?>()

  interface NewsClickListener {
    fun onMovieClicked(movie: Result)
  }

  val requestOptions: RequestOptions by lazy {
    RequestOptions()
        .error(R.drawable.no_internet)
        .placeholder(R.drawable.ic_launcher_foreground)
  }

  fun updateData(newData: List<Result?>) {
    data.clear()
    data.addAll(newData)
    data.sortedWith(compareBy<Result?> { it?.webPublicationDate }.thenBy { it?.webTitle })
    notifyDataSetChanged()
  }

  inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(result: Result?) {
      itemView.titleTextView.text = result?.webTitle
      itemView.overviewTextView.text = result?.fields?.trailText

      Glide.with(itemView.context)
          .applyDefaultRequestOptions(requestOptions)
          .load("${result?.fields?.thumbnail}")
          .into(itemView.moviePosterImageView)

      itemView.setOnClickListener {
        result?.let {
          newsClickListener.onMovieClicked(it)
        }
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(
        R.layout.line_item_news,
        parent,
        false
    )

    return NewsViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
    holder.bind(data[position])
  }

  override fun getItemCount() = data.size


}