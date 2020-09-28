package com.yandex.mobile.pager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainAdapter : ListAdapter<Int, MainViewHolder>(MainDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MainViewHolder(layoutInflater.inflate(R.layout.fragment_main, parent, false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val content = itemView.content
    private val context = content.context

    fun bind(item: Int) {
        val childCount = content.childCount
        when {
            childCount < item -> {
                repeat(item - childCount) { View.inflate(context, R.layout.text_main, content) }
            }
            childCount > item -> content.removeViews(item, childCount - item)
        }
    }
}

private class MainDiffItemCallback : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Int, newItem: Int) = true
}