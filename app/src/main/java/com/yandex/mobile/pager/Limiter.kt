package com.yandex.mobile.pager

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.doOnPreDraw

class Limiter(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private val contentViews = arrayListOf<View>()

    fun addContent(view: View) {
        contentViews.add(view)
        safelyRequestLayout()
    }

    fun removeContent(view: View) {
        contentViews.remove(view)
        safelyRequestLayout()
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        val attachedContentViews = attachedContentViews.toList()
        super.onMeasure(widthSpec, heightSpec)
        val maxContentHeight = contentViews.maxOfOrNull { child ->
            val childWidthSpec = MeasureSpec.makeMeasureSpec(child.measuredWidth, MeasureSpec.EXACTLY)
            child.measure(childWidthSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
            child.measuredHeight
        } ?: 0
        super.onMeasure(widthSpec, MeasureSpec.makeMeasureSpec(maxContentHeight, MeasureSpec.EXACTLY))
    }

    private fun safelyRequestLayout() {
        doOnPreDraw { requestLayout() }
    }
}