package com.yandex.mobile.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {

    private val childCount by lazy { requireArguments().getInt(keyChildCount) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val content = view.content
        repeat(childCount) { inflater.inflate(R.layout.text_main, content) }
        requireActivity().limiter.addContent(content)
        return view
    }

    override fun onDestroyView() {
        requireActivity().limiter.removeContent(content)
        super.onDestroyView()
    }

    fun setChildCount(childCount: Int) {
        val diff = childCount - content.childCount
        when {
            diff > 0 -> repeat(diff) { View.inflate(context, R.layout.text_main, content) }
            diff < 0 -> content.removeViews(childCount, -diff)
        }
    }

    companion object {

        private const val keyChildCount = "childCount"

        fun create(childCount: Int): MainFragment {
            return MainFragment().apply {
                arguments = Bundle().apply { putInt(keyChildCount, childCount) }
            }
        }
    }
}