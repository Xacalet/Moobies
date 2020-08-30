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
import com.xacalet.domain.usecase.IsWishlistedFlowUseCase
import com.xacalet.moobies.databinding.MovieListItemBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect


class MovieListAdapter(
    private val context: Context,
    private val getImageUrlUseCase: GetImageUrlUseCase,
    private val onClick: (Long) -> Unit,
    private val onWishlistButtonClick: (Long) -> Unit,
    private val isWishlistedFlowUseCase: IsWishlistedFlowUseCase
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
        holder.bind(item!!)
    }

    override fun onViewDetachedFromWindow(holder: MovieItemViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.unbind()
    }

    inner class MovieItemViewHolder(private val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var scope: CoroutineScope

        fun bind(item: Movie) {
            scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

            with(binding) {
                itemView.setOnClickListener { onClick(item.id) }
                wishlistButton.setOnClickListener { onWishlistButtonClick(item.id) }
                movieListItemTitle.text = item.title
                movieListItemRating.text = "${item.voteAverage}"
                item.posterPath?.let { posterPath ->
                    Glide.with(context)
                        .load(getImageUrlUseCase(itemView.width, posterPath))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(movieListItemCoverImage)
                } ?: movieListItemCoverImage.setImageResource(0)
                item.id.takeIf { it != -1L }?.let { id ->
                    scope.launch {
                        isWishlistedFlowUseCase(id).collect { isWishlisted ->
                            wishlistButton.isSelected = isWishlisted
                        }
                    }
                }
            }
        }

        fun unbind() {
            scope.cancel()
        }
    }

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}
