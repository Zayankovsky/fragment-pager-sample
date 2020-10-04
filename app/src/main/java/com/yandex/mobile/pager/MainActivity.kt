package com.yandex.mobile.pager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.updatePaddingRelative
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        text.setOnClickListener {
            Toast.makeText(this, "Hello, World!", Toast.LENGTH_SHORT).show()
        }

        val recycler = pager.children.filterIsInstance(RecyclerView::class.java).first()
        recycler.clipToPadding = false
        val padding = (30 * resources.displayMetrics.density).toInt()
        recycler.updatePaddingRelative(start = padding, end = padding)

        val adapter = MainAdapter(limiter)
        adapter.submitList(listOf(1, 3, 5, 7, 9, 7, 5, 3, 1))
        pager.adapter = adapter
    }
}