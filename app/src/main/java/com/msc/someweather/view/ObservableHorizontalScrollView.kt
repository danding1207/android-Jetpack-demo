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
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import io.reactivex.disposables.Disposables
import java.util.*

class ObservableHorizontalScrollView : HorizontalScrollView, Scrollable {

    // Fields that should be saved onSaveInstanceState
    private var mPrevScrollX: Int = 0
    private var mScrollX: Int = 0

    // Fields that don't need to be saved onSaveInstanceState
    private var mCallbacks: ObservableHorizontalScrollViewCallbacks? = null
    private var mCallbackCollection: MutableList<ObservableHorizontalScrollViewCallbacks>? = null
    private var mScrollState: ScrollState? = null
    private var mFirstScroll: Boolean = false
    private var mDragging: Boolean = false
    private var mIntercepted: Boolean = false
    private var mPrevMoveEvent: MotionEvent? = null
    private var mTouchInterceptionViewGroup: ViewGroup? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    constructor(context: Context, attrs: AttributeSet,
                defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
    }


    /**
     * Create an observable which emits the selected item in `view`.
     *
     *
     * *Warning:* The created observable keeps a strong reference to `view`. Unsubscribe
     * to free this reference.
     *
     *
     * *Note:* If an item is already selected, it will be emitted immediately on subscribe.
     */
    @CheckResult
    @NonNull
    fun onScrollChanged(): Observable<Int> {
        return ObservableHorizontalScrollViewObservable(this)
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as SavedState
        mPrevScrollX = ss.prevScrollX
        mScrollX = ss.scrollX
        super.onRestoreInstanceState(ss.superState)
    }

    public override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val ss = SavedState(superState)
        ss.prevScrollX = mPrevScrollX
        ss.scrollX = mScrollX
        return ss
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (hasNoCallbacks()) {
            return
        }
        mScrollX = l

        dispatchOnScrollChanged(l, mFirstScroll, mDragging)
        if (mFirstScroll) {
            mFirstScroll = false
        }

        if (mPrevScrollX < l) {
            mScrollState = ScrollState.UP
        } else if (l < mPrevScrollX) {
            mScrollState = ScrollState.DOWN
            //} else {
            // Keep previous state while dragging.
            // Never makes it STOP even if scrollY not changed.
            // Before Android 4.4, onTouchEvent calls onScrollChanged directly for ACTION_MOVE,
            // which makes mScrollState always STOP when onUpOrCancelMotionEvent is called.
            // STOP state is now meaningless for ScrollView.
        }
        mPrevScrollX = l
    }


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (hasNoCallbacks()) {
            return super.onInterceptTouchEvent(ev)
        }
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                // Whether or not motion events are consumed by children,
                // flag initializations which are related to ACTION_DOWN events should be executed.
                // Because if the ACTION_DOWN is consumed by children and only ACTION_MOVEs are
                // passed to parent (this view), the flags will be invalid.
                // Also, applications might implement initialization codes to onDownMotionEvent,
                // so call it here.
                mDragging = true
                mFirstScroll = mDragging
                dispatchOnDownMotionEvent()
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (hasNoCallbacks()) {
            return super.onTouchEvent(ev)
        }
        when (ev.actionMasked) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                mIntercepted = false
                mDragging = false
                dispatchOnUpOrCancelMotionEvent(mScrollState)
            }
            MotionEvent.ACTION_MOVE -> {
                if (mPrevMoveEvent == null) {
                    mPrevMoveEvent = ev
                }
                val diffX = ev.x - mPrevMoveEvent!!.x
                mPrevMoveEvent = MotionEvent.obtainNoHistory(ev)
                if (currentScrollX - diffX <= 0) {
                    // Can't scroll anymore.

                    if (mIntercepted) {
                        // Already dispatched ACTION_DOWN event to parents, so stop here.
                        return false
                    }

                    // Apps can set the interception target other than the direct parent.
                    val parent: ViewGroup = if (mTouchInterceptionViewGroup == null) {
                        parent as ViewGroup
                    } else {
                        mTouchInterceptionViewGroup!!
                    }

                    // Get offset to parents. If the parent is not the direct parent,
                    // we should aggregate offsets from all of the parents.
                    var offsetX = 0f
                    var offsetY = 0f
                    var v: View? = this
                    while (v != null && v != parent) {
                        offsetX += (v.left - v.scrollX).toFloat()
                        offsetY += (v.top - v.scrollY).toFloat()
                        v = v.parent as View
                    }
                    val event = MotionEvent.obtainNoHistory(ev)
                    event.offsetLocation(offsetX, offsetY)

                    if (parent.onInterceptTouchEvent(event)) {
                        mIntercepted = true

                        // If the parent wants to intercept ACTION_MOVE events,
                        // we pass ACTION_DOWN event to the parent
                        // as if these touch events just have began now.
                        event.action = MotionEvent.ACTION_DOWN

                        // Return this onTouchEvent() first and set ACTION_DOWN event for parent
                        // to the queue, to keep events sequence.
                        post { parent.dispatchTouchEvent(event) }
                        return false
                    }
                    // Even when this can't be scrolled anymore,
                    // simply returning false here may cause subView's click,
                    // so delegate it to super.
                    return super.onTouchEvent(ev)
                }
            }
        }
        return super.onTouchEvent(ev)
    }

    override fun setScrollViewCallbacks(listener: ObservableHorizontalScrollViewCallbacks) {
        mCallbacks = listener
    }

    override fun addScrollViewCallbacks(listener: ObservableHorizontalScrollViewCallbacks) {
        if (mCallbackCollection == null) {
            mCallbackCollection = ArrayList()
        }
        mCallbackCollection!!.add(listener)
    }

    override fun removeScrollViewCallbacks(listener: ObservableHorizontalScrollViewCallbacks) {
        if (mCallbackCollection != null) {
            mCallbackCollection!!.remove(listener)
        }
    }

    override fun clearScrollViewCallbacks() {
        if (mCallbackCollection != null) {
            mCallbackCollection!!.clear()
        }
    }

    override fun setTouchInterceptionViewGroup(viewGroup: ViewGroup) {
        mTouchInterceptionViewGroup = viewGroup
    }

    override fun scrollVerticallyTo(x: Int) {
        scrollTo(x, 0)
    }

    override fun getCurrentScrollX(): Int {
        return mScrollX
    }

    private fun dispatchOnDownMotionEvent() {
        if (mCallbacks != null) {
            mCallbacks!!.onDownMotionEvent()
        }
        if (mCallbackCollection != null) {
            for (i in mCallbackCollection!!.indices) {
                val callbacks = mCallbackCollection!![i]
                callbacks.onDownMotionEvent()
            }
        }
    }


    private fun dispatchOnScrollChanged(scrollX: Int, firstScroll: Boolean, dragging: Boolean) {
        if (mCallbacks != null) {
            mCallbacks!!.onScrollChanged(this, scrollX, firstScroll, dragging)
        }
        if (mCallbackCollection != null) {
            for (i in mCallbackCollection!!.indices) {
                val callbacks = mCallbackCollection!![i]
                callbacks.onScrollChanged(this, scrollX, firstScroll, dragging)
            }
        }
    }

    private fun dispatchOnUpOrCancelMotionEvent(scrollState: ScrollState?) {
        if (mCallbacks != null) {
            mCallbacks!!.onUpOrCancelMotionEvent(scrollState)
        }
        if (mCallbackCollection != null) {
            for (i in mCallbackCollection!!.indices) {
                val callbacks = mCallbackCollection!![i]
                callbacks.onUpOrCancelMotionEvent(scrollState)
            }
        }
    }

    private fun hasNoCallbacks(): Boolean {
        return mCallbacks == null && mCallbackCollection == null
    }

    class SavedState : View.BaseSavedState {
        internal var prevScrollX: Int = 0
        internal var scrollX: Int = 0

        /**
         * Called by onSaveInstanceState.
         */
        internal constructor(superState: Parcelable) : super(superState) {}

        /**
         * Called by CREATOR.
         */
        private constructor(`in`: Parcel) : super(`in`) {
            prevScrollX = `in`.readInt()
            scrollX = `in`.readInt()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(prevScrollX)
            out.writeInt(scrollX)
        }

        companion object {

            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    class ObservableHorizontalScrollViewObservable(private val view: ObservableHorizontalScrollView) : Observable<Int>() {

        override fun subscribeActual(observer: Observer<in Int>) {
            if (!checkMainThread(observer)) {
                return
            }
            val listener = Listener(view, observer)
            observer.onSubscribe(listener)
            view.addScrollViewCallbacks(listener)
        }

        internal class Listener(private val observableHorizontalScrollView: ObservableHorizontalScrollView, private val observer: Observer<in Int>) : MainThreadDisposable(), ObservableHorizontalScrollViewCallbacks {

            override fun onDispose() {
                observableHorizontalScrollView.removeScrollViewCallbacks(this)
            }

            override fun onScrollChanged(scrollView: ObservableHorizontalScrollView, scrollX: Int, firstScroll: Boolean, dragging: Boolean) {
                if (!isDisposed) {
                    observer.onNext(scrollX)
                }
            }

            override fun onDownMotionEvent() {

            }

            override fun onUpOrCancelMotionEvent(scrollState: ScrollState) {

            }
        }

        private fun checkMainThread(observer: Observer<*>): Boolean {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                observer.onSubscribe(Disposables.empty())
                observer.onError(IllegalStateException(
                        "Expected to be called on the main thread but was " + Thread.currentThread().name))
                return false
            }
            return true
        }
    }
}
