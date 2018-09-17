package com.msc.someweather.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.msc.someweather.R
import com.msc.someweather.http.bean.CaiYunWeather
import com.msc.someweather.http.bean.CaiYunWeather.ResultBean.HourlyBean.WindBean
import com.msc.someweather.utilities.DrawableUtils
import com.msc.someweather.utilities.UnitUtils
import com.orhanobut.logger.Logger
import java.text.SimpleDateFormat
import java.util.*

class HourlyAqiView : FrameLayout {

    var llApiChart: LinearLayout? = null
    var llApiTimeLine: LinearLayout? = null
    var llApiWind: LinearLayout? = null
    var llDailyAqi: LinearLayout? = null
    var llCuttingBwTipAndChart: LinearLayout? = null

    var result: CaiYunWeather.ResultBean? = null

    var tv_y_1: TextView? = null
    var tv_y_2: TextView? = null
    var tv_y_3: TextView? = null

    var horizontalScrollViewHourlyAqi: ObservableHorizontalScrollView? = null
    var horizontalScrollViewDailyAqi: ObservableHorizontalScrollView? = null

    var apiLinearChartView: ApiLinearChartView? = null

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
        llDailyAqi = findViewById(R.id.ll_daily_aqi)
        llCuttingBwTipAndChart = findViewById(R.id.ll_cutting_bw_tip_and_chart)

        tv_y_1 = findViewById(R.id.tv_y_1)
        tv_y_2 = findViewById(R.id.tv_y_2)
        tv_y_3 = findViewById(R.id.tv_y_3)

        horizontalScrollViewHourlyAqi = findViewById(R.id.horizontalScrollView_hourly_aqi)
        horizontalScrollViewDailyAqi = findViewById(R.id.horizontalScrollView_daily_aqi)


        apiLinearChartView = findViewById(R.id.apiLinearChartView)
    }

    fun invalidateView() {

        Logger.e("HourlyAqiView：startTime->${System.currentTimeMillis()}")

        apiLinearChartView!!.setResult(result!!)

        llApiChart!!.removeAllViews()
        llApiTimeLine!!.removeAllViews()
        llApiWind!!.removeAllViews()
        llCuttingBwTipAndChart!!.removeAllViews()

        val baseHeight = resources.getDimensionPixelSize(R.dimen.hourly_aqi_view_base_height)
        val barWidth = resources.getDimensionPixelSize(R.dimen.hourly_aqi_view_bar_width)
        val barMarginWidth = resources.getDimensionPixelSize(R.dimen.hourly_aqi_view_bar_margin_width)

        val barNum = if (result!!.hourly!!.aqi == null) 0 else result!!.hourly!!.aqi!!.size
        val dailyNum = if (result!!.daily!!.aqi == null) 0 else result!!.daily!!.aqi!!.size

        val barChartLength = barWidth * barNum + barMarginWidth * 2 * (barNum + 1) - (
                UnitUtils.getAndroiodScreenProperty(context)[0].toInt() - baseHeight
                )
        val dailChartLength = (UnitUtils.getAndroiodScreenProperty(context)[0].toInt()
                / 5) * dailyNum - UnitUtils.getAndroiodScreenProperty(context)[0].toInt()

        val itemScrollBarLength = UnitUtils.getAndroiodScreenProperty(context)[0].toInt() * 3 / 5

        val timeLinearTextSize = resources.getDimensionPixelSize(R.dimen.hourly_aqi_view_base_time_linear_text_size)
        val windOrTemperratureTextSize = resources.getDimensionPixelSize(R.dimen.hourly_aqi_view_base_wind_or_temperrature_text_size)


        val nowDate1 = Calendar.getInstance().time
        Logger.e("HourlyAqiView：startTime->${System.currentTimeMillis()}")

        if (result != null &&
                result!!.hourly != null &&
                result!!.hourly!!.aqi != null &&
                result!!.hourly!!.aqi!!.isNotEmpty()) {


            val maxValue = result!!.hourly!!.aqi!!.maxBy {
                it.value
            }

            result!!.hourly!!.aqi!!.forEachIndexed { index, it ->

                /**
                 * 添加柱形图
                 */
                val step: Int = when {
                    maxValue!!.value > 50 && maxValue.value <= 100 -> 50
                    maxValue.value > 100 && maxValue.value <= 200 -> 100
                    maxValue.value > 200 && maxValue.value <= 300 -> 150
                    maxValue.value > 300 && maxValue.value <= 400 -> 200
                    else -> 0
                }
                tv_y_1!!.text = "$step"
                tv_y_2!!.text = "${step * 2}"
                tv_y_3!!.text = "${step * 3}"
                val itemBarView = LinearLayout(context)
                itemBarView.setBackgroundColor(UnitUtils.aqiToColor(context, it.value))
                val itemBarViewLayoutParams = LinearLayout.LayoutParams(
                        barWidth,
                        (it.value * baseHeight / step).toInt())
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

                        itemTimeView.textSize = timeLinearTextSize.toFloat()
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
                        itemTimeView.textSize = timeLinearTextSize.toFloat()
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
                        itemTimeView.textSize = timeLinearTextSize.toFloat()
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
                        itemTimeView.textSize = timeLinearTextSize.toFloat()
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
                        itemTimeView.textSize = timeLinearTextSize.toFloat()
                        itemTimeView.setTextColor(ContextCompat.getColor(context, R.color.gray))
                        llApiTimeLine!!.addView(itemTimeView, itemTimeViewLayoutParams)
                    }
                }

            }

        }

        val nowDate2 = Calendar.getInstance().time

        Logger.e("HourlyAqiView：柱形图时间间隔->${UnitUtils.twoDateDistance(nowDate1, nowDate2)}")

        Logger.e("HourlyAqiView：startTime->${System.currentTimeMillis()}")

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

                itemWindText.textSize = windOrTemperratureTextSize.toFloat()
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

        val nowDate3 = Calendar.getInstance().time

        Logger.e("HourlyAqiView：风速时间间隔->${UnitUtils.twoDateDistance(nowDate2, nowDate3)}")

        Logger.e("HourlyAqiView：startTime->${System.currentTimeMillis()}")

        if (result != null &&
                result!!.daily != null &&
                result!!.daily!!.aqi != null &&
                result!!.daily!!.aqi!!.isNotEmpty()) {


            result!!.daily!!.aqi!!.forEachIndexed { index, it ->

                val itemDailyTipView = ConstraintLayout(context)
                LayoutInflater.from(context).inflate(R.layout.item_daily_aqi_layout,
                        itemDailyTipView, true)

                itemDailyTipView.findViewById<TextView>(R.id.tv_week).text =
                        UnitUtils.dateToWeekCh(it.date!!)
                itemDailyTipView.findViewById<TextView>(R.id.tv_date).text = when {
                    UnitUtils.isToday(it.date!!) -> "今天"
                    UnitUtils.isYesterday(it.date!!) -> "昨天"
                    else -> UnitUtils.dateToCh(it.date!!)
                }
                itemDailyTipView.findViewById<TextView>(R.id.tv_daily_api_tip).text =
                        UnitUtils.aqiToCh(it.avg)
                itemDailyTipView.findViewById<TextView>(R.id.tv_daily_api_tip).setTextColor(
                        UnitUtils.aqiToColor(context, it.avg)
                )
                itemDailyTipView.findViewById<TextView>(R.id.tv_aqi_max).text = "${it.max}"
                itemDailyTipView.findViewById<TextView>(R.id.tv_aqi_min).text = "${it.min}"

                val itemBarViewLayoutParams = LinearLayout.LayoutParams(
                        UnitUtils.getAndroiodScreenProperty(context)[0].toInt() / 5,
                        baseHeight * 3)

                val scrollDis = ((if (index == 0) 0 else index - 1) * UnitUtils.getAndroiodScreenProperty(context)[0].toInt() / 5) / (1 + itemScrollBarLength.toFloat() / dailChartLength)

                itemDailyTipView.setOnClickListener {
                    horizontalScrollViewDailyAqi!!.smoothScrollTo(scrollDis.toInt(), 0)
                }

                llDailyAqi!!.addView(itemDailyTipView, itemBarViewLayoutParams)

            }


        }

        val itemScrollBar = ImageView(context)

        val itemScrollBarLayoutParams = LinearLayout.LayoutParams(
                UnitUtils.getAndroiodScreenProperty(context)[0].toInt() / 5,
                UnitUtils.dipTopx(context, 3f))
        itemScrollBar.setImageResource(R.color.green)
        itemScrollBarLayoutParams.leftMargin = UnitUtils.getAndroiodScreenProperty(context)[0].toInt() / 5
        itemScrollBarLayoutParams.topMargin = UnitUtils.dipTopx(context, 5f)
        itemScrollBarLayoutParams.bottomMargin = UnitUtils.dipTopx(context, 2f)
        llCuttingBwTipAndChart!!.addView(itemScrollBar, itemScrollBarLayoutParams)


//        itemScrollBarLength barChartLength
//                x l
//
//                l * itemScrollBarLength / barChartLength

        Logger.e("widthPixels--->${UnitUtils.getAndroiodScreenProperty(context)[0].toInt()}")
        Logger.e("heightPixels--->${UnitUtils.getAndroiodScreenProperty(context)[1].toInt()}")

        Logger.e("widthPixels/5--->${UnitUtils.getAndroiodScreenProperty(context)[0].toInt() / 5}")

        Logger.e("dailyNum--->$dailyNum")


        Logger.e("barChartLength--->$barChartLength")
        Logger.e("dailChartLength--->$dailChartLength")


        horizontalScrollViewHourlyAqi!!.bindView(horizontalScrollViewDailyAqi!!, llCuttingBwTipAndChart!!,
                dailChartLength.toFloat() / barChartLength, itemScrollBarLength.toFloat() / barChartLength)

        horizontalScrollViewDailyAqi!!.bindView(horizontalScrollViewHourlyAqi!!, llCuttingBwTipAndChart!!,
                barChartLength.toFloat() / dailChartLength, itemScrollBarLength.toFloat() / dailChartLength)

        val nowDate4 = Calendar.getInstance().time

        Logger.e("HourlyAqiView：日期时间间隔->${UnitUtils.twoDateDistance(nowDate3, nowDate4)}")

        Logger.e("HourlyAqiView：startTime->${System.currentTimeMillis()}")

    }

}
