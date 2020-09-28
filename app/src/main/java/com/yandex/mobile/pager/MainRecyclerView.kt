package com.yandex.mobile.pager

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.RecyclerView

class MainRecyclerView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    private val children = arrayListOf<View>()

    override fun onChildAttachedToWindow(child: View) {
        children.add(child)
        safelyRequestLayout()
    }

    override fun onChildDetachedFromWindow(child: View) {
        children.remove(child)
        safelyRequestLayout()
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        val children = children
        super.onMeasure(widthSpec, heightSpec)
        val maxChildHeight = children.maxOfOrNull { child ->
            val childWidthSpec = MeasureSpec.makeMeasureSpec(child.measuredWidth, MeasureSpec.EXACTLY)
            child.measure(childWidthSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
            child.measuredHeight
        } ?: 0
        super.onMeasure(widthSpec, MeasureSpec.makeMeasureSpec(maxChildHeight, MeasureSpec.EXACTLY))
    }

    private fun safelyRequestLayout() {
        doOnPreDraw { requestLayout() }
    }
}