package com.xacalet.moobies.presentation.genreList

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xacalet.domain.model.Genre
import com.xacalet.moobies.R
import kotlinx.android.synthetic.main.genre_list_item.view.*

class GenreListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val genres: MutableList<Genre> = mutableListOf()

    var onItemClickListener: OnItemClickListener? = null

    init {
        adapter = Adapter()
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun setGenres(genres: List<Genre>) {
        this.genres.apply {
            clear()
            addAll(genres)
        }
        adapter?.notifyDataSetChanged()
    }

    inner class Adapter : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.genre_list_item, parent, false)
        )

        override fun getItemCount(): Int = genres.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.item?.apply {
                text = genres.getOrNull(position)?.name ?: ""
                this.setOnClickListener {
                    onItemClickListener?.onItemClick(genres.getOrNull(position))
                }
            }
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnItemClickListener {
        fun onItemClick(genre: Genre?)
    }
}
