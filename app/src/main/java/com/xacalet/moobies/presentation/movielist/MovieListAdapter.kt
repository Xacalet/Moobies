package com.xacalet.moobies.presentation.movielist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.xacalet.domain.model.Movie
import com.xacalet.domain.usecase.GetImageUrlUseCase
import com.xacalet.moobies.R
import kotlinx.android.synthetic.main.movie_list_item.view.*


class MovieListAdapter(
        private val context: Context,
        private val getImageUrlUseCase: GetImageUrlUseCase,
        private val onClick: (Long) -> Unit
) : PagingDataAdapter<Movie, MovieListAdapter.MovieItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder =
            MovieItemViewHolder(
                    LayoutInflater.from(context).inflate(
                            R.layout.movie_list_item,
                            parent,
                            false
                    )
            )

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.itemView) {
            setOnClickListener { onClick(item?.id ?: -1L) }
            movieListItemTitle.text = item?.title ?: ""
            movieListItemRating.text = "${item?.voteAverage}"
            item?.posterPath?.let { posterPath ->
                Glide.with(context)
                        .load(getImageUrlUseCase(width, posterPath))
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(movieListItemCoverImage)
            } ?: movieListItemCoverImage.setImageResource(0)
        }
    }

    class MovieItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                    oldItem == newItem
        }
    }
}
