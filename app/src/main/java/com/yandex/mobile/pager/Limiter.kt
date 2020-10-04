package com.yandex.mobile.pager

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.doOnPreDraw

class Limiter(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private val attachedContentViews = arrayListOf<View>()

    private val attachStateChangeListener = object : OnAttachStateChangeListener {

        override fun onViewAttachedToWindow(v: View) {
            addAttachedContent(v)
        }

        override fun onViewDetachedFromWindow(v: View) {
            removeAttachedContent(v)
        }
    }

    fun addContent(view: View) {
        view.addOnAttachStateChangeListener(attachStateChangeListener)
        if (view.isAttachedToWindow) addAttachedContent(view)
    }

    fun removeContent(view: View) {
        view.removeOnAttachStateChangeListener(attachStateChangeListener)
        if (view.isAttachedToWindow) removeAttachedContent(view)
    }

    private fun addAttachedContent(view: View) {
        attachedContentViews.add(view)
        safelyRequestLayout()
    }

    private fun removeAttachedContent(view: View) {
        attachedContentViews.remove(view)
        safelyRequestLayout()
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        val attachedContentViews = attachedContentViews.toList()
        super.onMeasure(widthSpec, heightSpec)
        val maxContentHeight = attachedContentViews.maxOfOrNull { child ->
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