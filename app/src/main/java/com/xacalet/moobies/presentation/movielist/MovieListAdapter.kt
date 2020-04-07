package com.xacalet.moobies.presentation.movielist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xacalet.domain.model.Movie
import com.xacalet.moobies.R
import kotlinx.android.synthetic.main.movie_list_item.view.*


class MovieListAdapter(private val context: Context) : RecyclerView.Adapter<MovieListViewHolder>() {

    private var movies: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder =
        MovieListViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.movie_list_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        with(movies[position]) {
            holder.itemView.movieListItemTitle.text = title
            holder.itemView.movieListItemRating.text = "$voteAverage"
        }
    }

    fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }
}
