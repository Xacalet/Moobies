package com.xacalet.moobies.presentation.movielist

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.xacalet.moobies.R

class MovieListItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.apply {
            val position = parent.getChildAdapterPosition(view)
            if (position == 0) {
                left = view.resources.getDimensionPixelSize(R.dimen.left_margin_first_element_list)
            }
            right = view.resources.getDimensionPixelSize(R.dimen.left_margin_first_element_list)
        }
    }
}