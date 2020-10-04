package com.yandex.mobile.pager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        text.setOnClickListener {
            Toast.makeText(this, "Hello, World!", Toast.LENGTH_SHORT).show()
        }

        val adapter = MainAdapter(limiter)
        adapter.submitList(listOf(1, 3, 5, 7, 9, 7, 5, 3, 1))
        pager.adapter = adapter
    }
}