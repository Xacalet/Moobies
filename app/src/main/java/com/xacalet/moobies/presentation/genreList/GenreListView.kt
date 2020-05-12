package com.xacalet.moobies.presentation.genreList

import android.content.Context
import android.graphics.Rect
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
        addItemDecoration(ItemDecoration(16))
    }

    fun setGenres(genres: List<Genre>) {
        this.genres.apply {
            clear()
            addAll(genres)
        }
        adapter?.notifyDataSetChanged()
    }

    private inner class ItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
            with(outRect) {
                if (parent.getChildAdapterPosition(view) != 0) {
                    if (view.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                        right = margin
                    } else {
                        left = margin
                    }

                }
            }
        }
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
