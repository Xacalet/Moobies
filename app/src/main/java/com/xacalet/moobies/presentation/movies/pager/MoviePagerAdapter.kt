package com.xacalet.moobies.presentation.movies.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.xacalet.domain.model.Movie
import java.lang.ref.WeakReference

class MoviePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragmentManager = fragment.childFragmentManager

    private val fragments: MutableMap<Int, WeakReference<MoviePagerItemFragment>> =
        mutableMapOf()

    private val items: MutableList<Movie> = mutableListOf()

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment =
        MoviePagerItemFragment.newInstance(items[position]).apply {
            fragments[position] = WeakReference(this)
        }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        if (!fragments.containsKey(position)) {
            (fragmentManager.findFragmentByTag("f" + items[position].id) as? MoviePagerItemFragment)?.apply {
                fragments[position] = WeakReference(this)
            }
        }
    }

    fun getFragmentAt(position: Int): MoviePagerItemFragment? = fragments[position]?.get()

    fun setItems(items: List<Movie>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}
