package com.msc.someweather.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.msc.someweather.R
import com.msc.someweather.http.bean.CaiYunWeather
import com.msc.someweather.utilities.UnitUtils
import com.orhanobut.logger.Logger
import java.lang.Exception
import java.util.*

class ApiLinearChartView : View {

    //    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var mFillPaint: Paint
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
//        surfaceHolder = holder
//        surfaceHolder.addCallback(this)

        mFillPaint = Paint()
        mFillPaint.style = Paint.Style.FILL
        mFillPaint.strokeCap = Paint.Cap.ROUND
        mFillPaint.isAntiAlias = true
        mFillPaint.strokeWidth = 4f
        mFillPaint.strokeJoin = Paint.Join.MITER
        setLayerType(View.LAYER_TYPE_SOFTWARE, mFillPaint)

    }

    fun setResult(result: CaiYunWeather.ResultBean) {
        this.result = result

        if (result.hourly != null &&
                result.hourly!!.aqi != null &&
                result.hourly!!.aqi!!.isNotEmpty()) {

            index = 0
            maxValue = result.hourly!!.aqi!!.maxBy {
                it.value
            }
            step = when {
                maxValue!!.value > 50 && maxValue!!.value <= 100 -> 50
                maxValue!!.value > 100 && maxValue!!.value <= 200 -> 100
                maxValue!!.value > 200 && maxValue!!.value <= 300 -> 150
                maxValue!!.value > 300 && maxValue!!.value <= 400 -> 200
                else -> 0
            }
            linearBarHeight = resources.getDimensionPixelSize(R.dimen.hourly_aqi_view_linear_bar_height)

            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val dailyNum = if (result!!.daily!!.aqi == null) 0 else result!!.daily!!.aqi!!.size
        val width = (UnitUtils.getAndroiodScreenProperty(context)[0].toInt() / 5) * dailyNum
        val height = measureDimension(heightMeasureSpec)
        //将计算的宽和高设置进去，保存，最后一步一定要有
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

    var index = 0
    var maxValue: CaiYunWeather.ResultBean.HourlyBean.AqiBean? = null
    var step = 0
    var linearBarHeight = 0

    override fun onDraw(canvas: Canvas) {

        if (result != null &&
                result!!.hourly != null &&
                result!!.hourly!!.aqi != null &&
                result!!.hourly!!.aqi!!.isNotEmpty()) {

            result!!.hourly!!.aqi!!.forEachIndexed { index, aqiBean ->


                if (index != result!!.hourly!!.aqi!!.size - 1) {

                    mFillPaint.color = UnitUtils.aqiToColor(context,
                            (result!!.hourly!!.aqi!![index].value + result!!.hourly!!.aqi!![index + 1].value) / 2)



                    canvas.drawLine((index * UnitUtils.getAndroiodScreenProperty(context)[0].toInt() / 5) / 24f,
                            linearBarHeight - result!!.hourly!!.aqi!![index].value.toFloat() * linearBarHeight / (step * 2f),

                            ((index + 1) * UnitUtils.getAndroiodScreenProperty(context)[0].toInt() / 5) / 24f,
                            linearBarHeight - result!!.hourly!!.aqi!![index + 1].value.toFloat() * linearBarHeight / (step * 2f),

                            mFillPaint)

//                    path.lineTo(((index + 1) * UnitUtils.getAndroiodScreenProperty(context)[0].toInt() / 5) / 24f,
//                            linearBarHeight - result!!.hourly!!.aqi!![index + 1].value.toFloat() * linearBarHeight / (step * 2f))

//                } else if (index==0) {
//                    path.moveTo((index * UnitUtils.getAndroiodScreenProperty(context)[0].toInt() / 5) / 24f,
//                            linearBarHeight - result!!.hourly!!.aqi!![index].value.toFloat() * linearBarHeight / (step * 2f))


                }

            }

//            canvas.drawPath(path, mFillPaint)


        }

    }


    private val moveThread = Runnable {

        try {
            Thread.sleep(30)
        } catch (e: InterruptedException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }


        postInvalidate()

    }




}
