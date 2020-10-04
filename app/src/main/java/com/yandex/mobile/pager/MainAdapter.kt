package com.yandex.mobile.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder

class MainAdapter(
    private val fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val currentList = arrayListOf<Int>()

    override fun createFragment(position: Int): Fragment {
        return MainFragment.create(currentList[position])
    }

    override fun onBindViewHolder(holder: FragmentViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) return super.onBindViewHolder(holder, position, payloads)
        val fragment = fragmentManager.findFragmentByTag("f${holder.itemId}")
        if (fragment !is MainFragment) return super.onBindViewHolder(holder, position, payloads)
        fragment.setChildCount(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun submitList(list: List<Int>) {
        val callback = MainDiffCallback(currentList, list)
        val diff = DiffUtil.calculateDiff(callback)
        currentList.clear()
        currentList.addAll(list)
        diff.dispatchUpdatesTo(this)
    }
}

private class MainDiffCallback(
    private val oldList: List<Int>,
    private val newList: List<Int>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItemPosition == newItemPosition
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return newList[newItemPosition]
    }
}
