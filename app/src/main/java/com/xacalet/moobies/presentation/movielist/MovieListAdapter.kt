package com.xacalet.moobies.presentation.movielist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xacalet.domain.model.Movie
import com.xacalet.domain.usecase.GetImageUrlUseCase
import com.xacalet.moobies.R
import kotlinx.android.synthetic.main.movie_list_item.view.*


class MovieListAdapter(
    private val context: Context,
    private val getImageUrlUseCase: GetImageUrlUseCase
) :
    RecyclerView.Adapter<MovieListAdapter.ItemViewHolder>() {

    private var movies: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.movie_list_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with(movies[position]) {
            holder.itemView.movieListItemTitle.text = title
            holder.itemView.movieListItemRating.text = "$voteAverage"
            Glide.with(context)
                .load(getImageUrlUseCase.invoke(holder.itemView.width, posterPath!!))
                .centerCrop()
                .into(holder.itemView.movieListItemCoverImage)
        }
    }

    fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
