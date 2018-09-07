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
import android.widget.LinearLayout
import android.widget.TextView
import com.msc.someweather.R
import com.msc.someweather.http.bean.CaiYunWeather
import com.msc.someweather.http.bean.CaiYunWeather.ResultBean.HourlyBean.WindBean
import com.msc.someweather.listener.ObservableHorizontalScrollViewCallbacks
import com.msc.someweather.utilities.DrawableUtils
import com.msc.someweather.utilities.UnitUtils
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date


class HourlyAqiView : FrameLayout {

    var llApiChart: LinearLayout? = null
    var llApiTimeLine: LinearLayout? = null
    var llApiWind: LinearLayout? = null
    var llDailyAqi: LinearLayout? = null

    var result: CaiYunWeather.ResultBean? = null

    var tv_y_1: TextView? = null
    var tv_y_2: TextView? = null
    var tv_y_3: TextView? = null

    var horizontalScrollViewHourlyAqi: ObservableHorizontalScrollView? = null
    var horizontalScrollViewDailyAqi: ObservableHorizontalScrollView? = null

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

        tv_y_1 = findViewById(R.id.tv_y_1)
        tv_y_2 = findViewById(R.id.tv_y_2)
        tv_y_3 = findViewById(R.id.tv_y_3)


        horizontalScrollViewHourlyAqi = findViewById(R.id.horizontalScrollView_hourly_aqi)
        horizontalScrollViewDailyAqi = findViewById(R.id.horizontalScrollView_daily_aqi)

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

                llDailyAqi!!.addView(itemDailyTipView, itemBarViewLayoutParams)

            }

        }

        val barNum = if (result!!.hourly!!.aqi == null) 0 else result!!.hourly!!.aqi!!.size
        val dailyNum = if (result!!.daily!!.aqi == null) 0 else result!!.daily!!.aqi!!.size

        val barChartLength = barWidth * barNum + barMarginWidth * 2 * (barNum + 1) - (
                UnitUtils.getAndroiodScreenProperty(context)[0].toInt() - baseHeight
                )
        val dailChartLength = (UnitUtils.getAndroiodScreenProperty(context)[0].toInt()
                / 5) * dailyNum - UnitUtils.getAndroiodScreenProperty(context)[0].toInt()

        Logger.e("widthPixels--->${UnitUtils.getAndroiodScreenProperty(context)[0].toInt()}")
        Logger.e("heightPixels--->${UnitUtils.getAndroiodScreenProperty(context)[1].toInt()}")

        Logger.e("widthPixels/5--->${UnitUtils.getAndroiodScreenProperty(context)[0].toInt() / 5}")

        Logger.e("dailyNum--->$dailyNum")


        Logger.e("barChartLength--->$barChartLength")
        Logger.e("dailChartLength--->$dailChartLength")

//        val scrollViewCallbacks = object : ObservableHorizontalScrollViewCallbacks {
//            override fun onScrollChanged(scrollView: ObservableHorizontalScrollView,
//                                         scrollX: Int, firstScroll: Boolean,
//                                         dragging: Boolean) {
//
//
//                Logger.e("HourlyscrollX--->$scrollX")
//
//
//                when (scrollView) {
//                    horizontalScrollViewHourlyAqi -> {
//                        horizontalScrollViewDailyAqi!!.scrollTo(scrollX * dailChartLength / barChartLength, 0)
//
//                    }
//                    horizontalScrollViewDailyAqi -> {
//                        horizontalScrollViewHourlyAqi!!.scrollTo(scrollX * barChartLength / dailChartLength, 0)
//
//                    }
//                }
//            }
//
//            override fun onDownMotionEvent() {
//
//            }
//
//            override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {
//            }
//
//        }
//
//        horizontalScrollViewHourlyAqi!!.addScrollViewCallbacks(scrollViewCallbacks)
//        horizontalScrollViewDailyAqi!!.addScrollViewCallbacks(object : ObservableHorizontalScrollViewCallbacks {
//            override fun onScrollChanged(scrollView: ObservableHorizontalScrollView,
//                                         scrollX: Int, firstScroll: Boolean,
//                                         dragging: Boolean) {
//                Logger.e("DailyscrollX--->$scrollX")
//            }
//
//            override fun onDownMotionEvent() {
//            }
//
//            override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {
//            }
//        })

        val hourlyObservable = horizontalScrollViewHourlyAqi!!.onScrollChanged()
                .doOnNext {

                }
                .subscribe {
                    horizontalScrollViewDailyAqi!!.scrollTo(it * dailChartLength / barChartLength, 0)
                }


//        val dailyObservable =  horizontalScrollViewDailyAqi!!.onScrollChanged()
//                .doOnNext {
//
//                }
//                .subscribe {
//                    horizontalScrollViewHourlyAqi!!.scrollTo(it * barChartLength / dailChartLength, 0)
//                }


//        Observable.combineLatest(hourlyObservable, dailyObservable,
//                BiFunction<Int, Int, Int> { t1, t2 ->
//
//
//                    return
//
//                })

    }

}
