package com.msc.someweather.view

import android.content.Context
import android.content.res.Configuration
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Paint.Cap
import android.graphics.Paint.Style
import android.graphics.Path
import android.graphics.Path.Direction
import android.graphics.RectF
import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout

import com.msc.someweather.R

class BubbleRelativeLayout(context: Context, attrs: AttributeSet?, defStyle: Int) : RelativeLayout(context, attrs, defStyle) {

    private var mFillPaint: Paint? = null
    private var PrimaryColor: Int = 0
    private val mPath = Path()
    private val mBubbleLegPrototype = Path()

    constructor(context: Context) : this(context, null) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        init(context, attrs)
    }

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layoutParams = params
        PrimaryColor = ContextCompat.getColor(context, R.color.aqi_1)
        mFillPaint = Paint()
        mFillPaint!!.style = Style.FILL
        mFillPaint!!.strokeCap = Cap.BUTT
        mFillPaint!!.isAntiAlias = true
        mFillPaint!!.strokeWidth = STROKE_WIDTH.toFloat()
        mFillPaint!!.strokeJoin = Paint.Join.MITER
        mFillPaint!!.pathEffect = CornerPathEffect(CORNER_RADIUS)
        mFillPaint!!.color = PrimaryColor

        mFillPaint!!.setShadowLayer(8f, 4f, 6f, SHADOW_COLOR)
        setLayerType(View.LAYER_TYPE_SOFTWARE, mFillPaint)

        renderBubbleLegPrototype()
        setPadding(PADDING, PADDING, PADDING, PADDING)
        setBackgroundColor(Color.TRANSPARENT)
    }

    fun setPrimaryColor(color: Int) {
        PrimaryColor = color
        mFillPaint!!.color = PrimaryColor
        invalidate()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    /**
     * 尖角path
     */
    private fun renderBubbleLegPrototype() {
        mBubbleLegPrototype.moveTo(0f, 0f)
        mBubbleLegPrototype.lineTo(PADDING * 1.0f, -PADDING * 1.0f)
        mBubbleLegPrototype.lineTo(PADDING * 1.0f, PADDING * 1.0f)
        mBubbleLegPrototype.close()
    }

    /**
     * 根据显示方向，获取尖角位置矩阵
     *
     * @param width
     * @param height
     * @return
     */
    private fun renderBubbleLegMatrix(width: Float, height: Float): Matrix {
        val matrix = Matrix()
        matrix.postRotate(45f)
        matrix.postTranslate(PADDING.toFloat(), height - PADDING * 1.5f)
        return matrix
    }

    override fun onDraw(canvas: Canvas) {
        val width = canvas.width.toFloat()
        val height = canvas.height.toFloat()
        mPath.rewind()
        mPath.addRoundRect(RectF(PADDING.toFloat(), PADDING.toFloat(), width - PADDING, height - PADDING),
                CORNER_RADIUS, CORNER_RADIUS, Direction.CW)
        mPath.addPath(mBubbleLegPrototype, renderBubbleLegMatrix(width, height))
        canvas.drawPath(mPath, mFillPaint!!)
    }

    companion object {
        var PADDING = 14
        var STROKE_WIDTH = 0
        var CORNER_RADIUS = 8.0f
        var SHADOW_COLOR = Color.argb(20, 0, 0, 0)
    }
}
