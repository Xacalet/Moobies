package com.xacalet.moobies.presentation.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xacalet.moobies.R
import com.xacalet.moobies.databinding.MovieListLoadingItemBinding


class MoviesLoadStateAdapter(
    private val adapter: MovieListAdapter
) : LoadStateAdapter<MoviesLoadStateAdapter.LoadingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        LoadingItemViewHolder(
            MovieListLoadingItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: LoadingItemViewHolder, loadState: LoadState) {
        with(holder) {
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.retryButton.setOnClickListener { adapter.retry() }
            binding.loadingMessage.text = when (loadState) {
                is LoadState.Error -> (loadState as? LoadState.Error)?.error?.message
                is LoadState.Loading -> itemView.resources.getString(R.string.loading_more_items)
                else -> ""
            }
            binding.loadingMessage.isVisible = binding.loadingMessage.text.isNotBlank()
        }
    }

    class LoadingItemViewHolder(val binding: MovieListLoadingItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}