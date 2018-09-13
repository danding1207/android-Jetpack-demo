package com.msc.someweather.view

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.msc.someweather.R
import com.msc.someweather.http.bean.CaiYunWeather
import com.msc.someweather.http.bean.CaiYunWeather.ResultBean.DailyBean.TemperatureBeanX
import com.msc.someweather.utilities.UnitUtils

class TemperratureLinearGraphView : View {

    private lateinit var mFillPaint: Paint
    private lateinit var mTextPaint: Paint

    private var result: CaiYunWeather.ResultBean? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {

        mFillPaint = Paint()
        mFillPaint.style = Paint.Style.STROKE
        mFillPaint.strokeCap = Paint.Cap.ROUND
        mFillPaint.isAntiAlias = true
        mFillPaint.strokeWidth = 3f
        mFillPaint.color = ContextCompat.getColor(context, R.color.green)
        mFillPaint.strokeJoin = Paint.Join.ROUND
        mFillPaint.pathEffect = CornerPathEffect(200f)
        setLayerType(View.LAYER_TYPE_SOFTWARE, mFillPaint)

        mTextPaint = Paint()
        mTextPaint.style = Paint.Style.FILL
        mTextPaint.strokeCap = Paint.Cap.ROUND
        mTextPaint.isAntiAlias = true
        mTextPaint.strokeWidth = 2f
        mTextPaint.color = ContextCompat.getColor(context, R.color.black)
        mTextPaint.strokeJoin = Paint.Join.MITER
        mTextPaint.textSize = 28f
//        setLayerType(View.LAYER_TYPE_SOFTWARE, mFillPaint)

    }

    fun setResult(result: CaiYunWeather.ResultBean) {
        this.result = result

        if (result.daily != null &&
                result.daily!!.temperature != null &&
                result.daily!!.temperature!!.isNotEmpty()) {

            maxValue = result.daily!!.temperature!!.maxBy {
                it.max
            }
            minValue = result.daily!!.temperature!!.minBy {
                it.min
            }
            linearBarHeight = resources.getDimensionPixelSize(R.dimen.hourly_temperrature_view_linear_graph_center_height)

            textHeight = (resources.getDimensionPixelSize(R.dimen.hourly_temperrature_view_linear_graph_height)
                    - resources.getDimensionPixelSize(R.dimen.hourly_temperrature_view_linear_graph_center_height)) / 2

            step = (maxValue!!.max - minValue!!.min) / linearBarHeight

            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val hourlyNum = if (result!!.hourly!!.temperature == null) 0 else result!!.hourly!!.temperature!!.size
        val barMarginWidth = resources.getDimensionPixelSize(R.dimen.hourly_temperrature_view_linear_graph_margin_width)
        val width = (UnitUtils.getAndroiodScreenProperty(context)[0].toInt() / 23) * hourlyNum + barMarginWidth * 2
        val height = measureDimension(heightMeasureSpec)
        //将计算的宽和高设置进去，保存，最后一步一定要有
        if (width != 0 && height != 0)
            setMeasuredDimension(width, height)
    }

    /**
     * @param measureSpec   父控件传来的widthMeasureSpec，heightMeasureSpec
     * @return  结果
     */
    private fun measureDimension(measureSpec: Int): Int {
        var result: Int
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec)

        //1,layout中自定义组件给出来确定的值，比如100dp
        //2,layout中自定义组件使用的是match_parent，但父控件的size已经可以确定了，比如设置的具体的值或者match_parent
        result = when (specMode) {
            View.MeasureSpec.EXACTLY -> specSize
            View.MeasureSpec.AT_MOST -> specSize
            View.MeasureSpec.UNSPECIFIED -> specSize
            else -> specSize
        }//UNSPECIFIED,没有任何限制，所以可以设置任何大小
        //layout中自定义组件使用的wrap_content
        return result
    }

    var maxValue: TemperatureBeanX? = null
    var minValue: TemperatureBeanX? = null
    var step: Double = 0.0
    var linearBarHeight = 0
    var textHeight = 0

    override fun onDraw(canvas: Canvas) {

        val temperaturePath = Path()

        if (result != null &&
                result!!.hourly != null &&
                result!!.hourly!!.temperature != null &&
                result!!.hourly!!.temperature!!.isNotEmpty()) {

            result!!.hourly!!.temperature!!.forEachIndexed { index, temperatureBean ->

                //                val maxText = "${temperatureBean.max}°"
//                val minText = "${temperatureBean.min}°"
//                val maxRect = Rect()
//                val minRect = Rect()
//                mTextPaint.getTextBounds(maxText, 0, maxText.length, maxRect)
//                //文本的宽度
//                val maxWidth = maxRect.width()//文本的宽度
//                //文本的高度
//                val maxHeight = maxRect.height()//文本的高度
//
//                mTextPaint.getTextBounds(minText, 0, minText.length, minRect)
//
//                //文本的宽度
//                val minWidth = minRect.width()
//                //文本的高度
//                val minHeight = minRect.height()
//
//                canvas.drawText(maxText, index * UnitUtils.getAndroiodScreenProperty(context)[0].toInt() / 5f
//                        + UnitUtils.getAndroiodScreenProperty(context)[0].toInt() / 10f
//                        - maxWidth/2,
//                        linearBarHeight - ((result!!.daily!!.temperature!![index].max - minValue!!.min) / step).toFloat()
//                                +textHeight
//                        -maxHeight,
//                        mTextPaint)
//
//                canvas.drawText(minText, index * UnitUtils.getAndroiodScreenProperty(context)[0].toInt() / 5f
//                        + UnitUtils.getAndroiodScreenProperty(context)[0].toInt() / 10f
//                        - maxWidth/2,
//                        linearBarHeight - ((result!!.daily!!.temperature!![index].min - minValue!!.min) / step).toFloat()
//                                +textHeight
//                                +2*maxHeight,
//                        mTextPaint)

                if (index != result!!.daily!!.temperature!!.size - 1) {


                    if (index == 0) {

                        temperaturePath.moveTo(
                                index * UnitUtils.getAndroiodScreenProperty(context)[0].toInt() / 23f,
                                linearBarHeight - ((result!!.hourly!!.temperature!![index].value - minValue!!.min) / step).toFloat()
                                        + textHeight)

                    }

                    temperaturePath.lineTo((index + 1) * UnitUtils.getAndroiodScreenProperty(context)[0].toInt() / 23f,
                            linearBarHeight - ((result!!.hourly!!.temperature!![index + 1].value - minValue!!.min) / step).toFloat()
                                    + textHeight)

                }

            }

            canvas.drawPath(temperaturePath, mFillPaint)

        }

    }

}
