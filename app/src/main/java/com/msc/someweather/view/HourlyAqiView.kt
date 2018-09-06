package com.msc.someweather.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.msc.someweather.R
import com.msc.someweather.http.bean.CaiYunWeather
import com.msc.someweather.utilities.DrawableUtils
import com.msc.someweather.utilities.UnitUtils
import kotlinx.android.synthetic.main.fragment_weather.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Collectors
import com.msc.someweather.http.bean.CaiYunWeather.ResultBean.HourlyBean.WindBean
import kotlin.collections.ArrayList


class HourlyAqiView : FrameLayout {

    var llApiChart: LinearLayout? = null
    var llApiTimeLine: LinearLayout? = null
    var llApiWind: LinearLayout? = null

    var result: CaiYunWeather.ResultBean? = null


    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        LayoutInflater.from(context).inflate(R.layout.hourly_aqi_layout, this)

        llApiChart = findViewById(R.id.ll_api_chart)
        llApiTimeLine = findViewById(R.id.ll_api_time_line)
        llApiWind = findViewById(R.id.ll_api_wind)
    }

    fun invalidateView() {

        llApiChart!!.removeAllViews()
        llApiTimeLine!!.removeAllViews()
        llApiWind!!.removeAllViews()

        val baseHeight = resources.getDimensionPixelSize(R.dimen.hourly_aqi_view_base_height)
        val barWidth = resources.getDimensionPixelSize(R.dimen.hourly_aqi_view_bar_width)
        val barMarginWidth = resources.getDimensionPixelSize(R.dimen.hourly_aqi_view_bar_margin_width)

        if (result != null &&
                result!!.hourly != null &&
                result!!.hourly!!.aqi != null &&
                result!!.hourly!!.aqi!!.isNotEmpty()) {

            result!!.hourly!!.aqi!!.forEachIndexed { index, it ->

                /**
                 * 添加柱形图
                 */
                val itemBarView = LinearLayout(context)
                itemBarView.setBackgroundColor(UnitUtils.aqiToColor(context, it.value.toInt()))
                val itemBarViewLayoutParams = LinearLayout.LayoutParams(
                        barWidth,
                        (it.value * baseHeight / 100).toInt())
                when (index) {
                    0 -> {
                        itemBarViewLayoutParams.leftMargin = barMarginWidth * 2
                        itemBarViewLayoutParams.rightMargin = barMarginWidth
                    }
                    result!!.hourly!!.aqi!!.size - 1 -> {
                        itemBarViewLayoutParams.leftMargin = barMarginWidth
                        itemBarViewLayoutParams.rightMargin = barMarginWidth * 2
                    }
                    else -> {
                        itemBarViewLayoutParams.leftMargin = barMarginWidth
                        itemBarViewLayoutParams.rightMargin = barMarginWidth
                    }
                }
                llApiChart!!.addView(itemBarView, itemBarViewLayoutParams)


                /**
                 * 添加时间轴
                 */
                when {
                    it.datetime!!.endsWith("00:00") -> {
                        val itemTimeView = TextView(context)
                        val itemTimeViewLayoutParams = LinearLayout.LayoutParams(
                                (barMarginWidth * 2 + barWidth) * 6,
                                baseHeight)
                        itemTimeView.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT

                        /**
                         * 当天日期
                         */
                        val simpleDateFormatCru = SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE)
                        val simpleDateFormatCh = SimpleDateFormat("M月d日", Locale.CHINESE)
                        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINESE)

                        val date = Date(System.currentTimeMillis())

                        if (it.datetime!!.startsWith(simpleDateFormatCru.format(date))) {
                            itemTimeView.text = "今天"
                        } else {
                            itemTimeView.text = simpleDateFormatCh.format(
                                    simpleDateFormat.parse(it.datetime))
                        }

                        itemTimeView.textSize =
                                UnitUtils.sp2px(context, 4f).toFloat()
                        itemTimeView.setTextColor(ContextCompat.getColor(context, R.color.gray))
                        llApiTimeLine!!.addView(itemTimeView, itemTimeViewLayoutParams)
                    }
                    it.datetime!!.endsWith("06:00") -> {
                        val itemTimeView = TextView(context)
                        val itemTimeViewLayoutParams = LinearLayout.LayoutParams(
                                (barMarginWidth * 2 + barWidth) * 4,
                                baseHeight)
                        itemTimeView.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
                        itemTimeView.text = "07:00"
                        itemTimeView.textSize =
                                UnitUtils.sp2px(context, 4f).toFloat()
                        itemTimeView.setTextColor(ContextCompat.getColor(context, R.color.gray))
                        llApiTimeLine!!.addView(itemTimeView, itemTimeViewLayoutParams)
                    }
                    it.datetime!!.endsWith("10:00") -> {
                        val itemTimeView = TextView(context)
                        val itemTimeViewLayoutParams = LinearLayout.LayoutParams(
                                (barMarginWidth * 2 + barWidth) * 4,
                                baseHeight)
                        itemTimeView.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
                        itemTimeView.text = "11:00"
                        itemTimeView.textSize =
                                UnitUtils.sp2px(context, 4f).toFloat()
                        itemTimeView.setTextColor(ContextCompat.getColor(context, R.color.gray))
                        llApiTimeLine!!.addView(itemTimeView, itemTimeViewLayoutParams)
                    }
                    it.datetime!!.endsWith("14:00") -> {
                        val itemTimeView = TextView(context)
                        val itemTimeViewLayoutParams = LinearLayout.LayoutParams(
                                (barMarginWidth * 2 + barWidth) * 4,
                                baseHeight)
                        itemTimeView.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
                        itemTimeView.text = "15:00"
                        itemTimeView.textSize =
                                UnitUtils.sp2px(context, 4f).toFloat()
                        itemTimeView.setTextColor(ContextCompat.getColor(context, R.color.gray))
                        llApiTimeLine!!.addView(itemTimeView, itemTimeViewLayoutParams)
                    }
                    it.datetime!!.endsWith("18:00") -> {
                        val itemTimeView = TextView(context)
                        val itemTimeViewLayoutParams = LinearLayout.LayoutParams(
                                (barMarginWidth * 2 + barWidth) * 6,
                                baseHeight)
                        itemTimeView.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
                        itemTimeView.text = "19:00"
                        itemTimeView.textSize =
                                UnitUtils.sp2px(context, 4f).toFloat()
                        itemTimeView.setTextColor(ContextCompat.getColor(context, R.color.gray))
                        llApiTimeLine!!.addView(itemTimeView, itemTimeViewLayoutParams)
                    }
                }

            }

        }


        if (result != null &&
                result!!.hourly != null &&
                result!!.hourly!!.wind != null &&
                result!!.hourly!!.wind!!.isNotEmpty()) {

            /**
             * 添加风力
             */
            var windTempList: MutableList<WindBean>? = null

            result!!.hourly!!.wind!!.forEach {

                if (windTempList == null) {
                    windTempList = mutableListOf()
                    windTempList!!.add(it)
                    windTempList!![windTempList!!.size - 1].length++
                } else {
                    val tip = UnitUtils.windToAngle(windTempList!![windTempList!!.size - 1].direction) + UnitUtils.windToLevel(windTempList!![windTempList!!.size - 1].speed)
                    val notip = UnitUtils.windToAngle(it.direction) + UnitUtils.windToLevel(it.speed)
                    if (tip == notip) {
                        windTempList!![windTempList!!.size - 1].length++
                    } else {
                        windTempList!!.add(it)
                        windTempList!![windTempList!!.size - 1].length++
                    }
                }

            }

            windTempList!!.forEachIndexed { index, it ->

                val itemWindView = LinearLayout(context)

                val itemWindText = TextView(context)

                val itemTimeViewLayoutParams = LinearLayout.LayoutParams(
                        barMarginWidth * 2 * (it.length - 1) + barWidth * it.length,
                        baseHeight - 20)
                itemWindView.gravity = Gravity.CENTER

                if (it.length > 3) {
                    itemWindText.text = "${UnitUtils.windToLevel(it.speed)}级"

                    val drawableWind = DrawableUtils.tintDrawable(
                            DrawableUtils.bitmapRotate(context.resources,
                                    ContextCompat.getDrawable(context, R.drawable.wind)!!,
                                    UnitUtils.windToAngle(it.direction)),
                            ColorStateList.valueOf(Color.BLACK))

                    drawableWind.setBounds(0, 0, 30, 30)
                    itemWindText.setCompoundDrawables(drawableWind,
                            null, null, null)
                    itemWindText.compoundDrawablePadding = 4
                }

                itemWindText.textSize =
                        UnitUtils.sp2px(context, 4f).toFloat()
                itemWindText.setTextColor(ContextCompat.getColor(context, R.color.black))
//                itemWindView.setBackgroundColor(ContextCompat.getColor(context, R.color.gray_cuttingline))
                itemWindView.setBackgroundResource(R.drawable.wind_bar_bg)

                when (index) {
                    0 -> {
                        itemTimeViewLayoutParams.leftMargin = barMarginWidth * 2
                        itemTimeViewLayoutParams.rightMargin = barMarginWidth
                    }
                    result!!.hourly!!.aqi!!.size - 1 -> {
                        itemTimeViewLayoutParams.leftMargin = barMarginWidth
                        itemTimeViewLayoutParams.rightMargin = barMarginWidth * 2
                    }
                    else -> {
                        itemTimeViewLayoutParams.leftMargin = barMarginWidth
                        itemTimeViewLayoutParams.rightMargin = barMarginWidth
                    }
                }

                itemWindView.addView(itemWindText)
                llApiWind!!.addView(itemWindView, itemTimeViewLayoutParams)

            }


        }

    }

}
