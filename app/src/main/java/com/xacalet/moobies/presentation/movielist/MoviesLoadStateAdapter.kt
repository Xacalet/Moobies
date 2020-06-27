package com.xacalet.moobies.presentation.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xacalet.moobies.R
import kotlinx.android.synthetic.main.movie_list_loading_item.view.*

class MoviesLoadStateAdapter(
        private val adapter: MovieListAdapter
) : LoadStateAdapter<MoviesLoadStateAdapter.LoadingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
            LoadingItemViewHolder(parent)

    override fun onBindViewHolder(holder: LoadingItemViewHolder, loadState: LoadState) {
        with(holder.itemView) {
            progressBar.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState is LoadState.Error
            retryButton.setOnClickListener { adapter.retry() }
            loadingMessage.text = when (loadState) {
                is LoadState.Error -> (loadState as? LoadState.Error)?.error?.message
                is LoadState.Loading -> resources.getString(R.string.loading_more_items)
                else -> ""
            }
            loadingMessage.isVisible = loadingMessage.text.isNotBlank()
        }
    }

    class LoadingItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_list_loading_item, parent, false))
}