package com.msc.someweather.view

import android.content.Context
import android.os.Looper
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.CheckResult
import android.support.annotation.NonNull
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import com.msc.someweather.listener.ObservableHorizontalScrollViewCallbacks
import com.msc.someweather.listener.Scrollable
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import io.reactivex.disposables.Disposables
import java.util.*

class ObservableHorizontalScrollView : HorizontalScrollView {

    private var bindedScrollView: ObservableHorizontalScrollView? = null
    private var itemScrollBar: View? = null
    private var bindedFactor: Float = 0f
    private var scrollBarFactor: Float = 0f

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    constructor(context: Context, attrs: AttributeSet,
                defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)

        Logger.e("onScrollChanged--->$l")

        if (bindedScrollView != null) {
            bindedScrollView!!.removeBindView()
            bindedScrollView!!.scrollTo((l * bindedFactor).toInt(), t)
            bindedScrollView!!.bindView(this, itemScrollBar!!, 1 / bindedFactor, scrollBarFactor / bindedFactor)
        }

        if (itemScrollBar != null) {
            itemScrollBar!!.scrollTo(-(l * scrollBarFactor).toInt(), t)
        }

    }

    fun bindView(scrollView: ObservableHorizontalScrollView, itemScrollBar: View, factor: Float, scrollBarFactor: Float) {
        this.bindedScrollView = scrollView
        this.bindedFactor = factor
        this.itemScrollBar = itemScrollBar
        this.scrollBarFactor = scrollBarFactor
    }

    fun removeBindView() {
        this.bindedScrollView = null
    }

}
