package com.xacalet.moobies.presentation.movielist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.xacalet.domain.model.Movie
import com.xacalet.domain.usecase.GetImageUrlUseCase
import com.xacalet.moobies.databinding.MovieListItemBinding


class MovieListAdapter(
    private val context: Context,
    private val getImageUrlUseCase: GetImageUrlUseCase,
    private val onClick: (Long) -> Unit
) : PagingDataAdapter<Movie, MovieListAdapter.MovieItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder =
        MovieItemViewHolder(
            MovieListItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder) {
            itemView.setOnClickListener { onClick(item?.id ?: -1L) }

            binding.movieListItemTitle.text = item?.title ?: ""
            binding.movieListItemRating.text = "${item?.voteAverage}"
            item?.posterPath?.let { posterPath ->
                Glide.with(context)
                    .load(getImageUrlUseCase(itemView.width, posterPath))
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.movieListItemCoverImage)
            } ?: binding.movieListItemCoverImage.setImageResource(0)
        }
    }

    class MovieItemViewHolder(val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}
