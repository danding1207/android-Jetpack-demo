package com.msc.someweather.utilities

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.msc.someweather.R
import android.util.DisplayMetrics
import android.support.v4.content.ContextCompat.getSystemService
import android.view.WindowManager
import com.orhanobut.logger.Logger
import android.text.format.DateFormat.getDateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object UnitUtils {

    /**
     * dp转换为px
     *
     * @param context
     * @param dpValue
     * dp值
     * @return 转换后px值
     */
    fun dipTopx(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * px转换为dp
     *
     * @param context
     * @param pxValue
     * px值
     * @return 转换后dp值
     */
    fun pxTodip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 将px值转换为sp值，保证文字大小不变  
     *
     * @param pxValue
     * @param fontScale
     * （DisplayMetrics类中属性scaledDensity）
     * @return          
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值，保证文字大小不变         
     *
     * @param spValue
     *          
     * @param fontScale
     * （DisplayMetrics类中属性scaledDensity）
     * @return          
     */
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    fun temperatureToCh(temperature: String): String {
        return when (temperature) {
            "HAZE" -> "霾"
            "RAIN" -> "雨"
            "CLEAR_NIGHT" -> "晴夜"
            "PARTLY_CLOUDY_NIGHT" -> "阴夜"
            "PARTLY_CLOUDY_DAY" -> "阴天"
            "CLOUDY" -> "多云"
            "CLEAR_DAY" -> "晴天"
            else -> "未知"
        }
    }

    fun windToCh(wind: Double): String {
        return when (Math.round(wind / 22.5).toInt()) {
            0 -> "北风"
            1 -> "北东北风"
            2 -> "东北风"
            3 -> "东东北风"
            4 -> "东风"
            5 -> "东东南风"
            6 -> "东南风"
            7 -> "南东南风"
            8 -> "南风"
            9 -> "南西南风"
            10 -> "西南风"
            11 -> "西西南风"
            12 -> "西风"
            13 -> "西西北风"
            14 -> "西北风"
            15 -> "北西北风"
            else -> "北风"
        }
    }

    fun windToAngle(wind: Double): Int {
        return when {
            wind < 22.5 || wind >= 337.5 -> 180
            wind >= 22.5 && wind < 67.5 -> 225
            wind >= 67.5 && wind < 112.5 -> 270
            wind >= 112.5 && wind < 157.5 -> 315
            wind >= 157.5 && wind < 202.5 -> 0
            wind >= 202.5 && wind < 247.5 -> 45
            wind >= 247.5 && wind < 292.5 -> 90
            wind >= 292.5 && wind < 337.5 -> 135
            else -> 0
        }
    }

    fun windToLevel(wind: Double): Int {
        return when {
            wind < 1 -> 0
            5 < wind && wind < 1 -> 1
            6 < wind && wind < 11 -> 2
            12 < wind && wind < 19 -> 3
            20 < wind && wind < 28 -> 4
            29 < wind && wind < 38 -> 5
            39 < wind && wind < 49 -> 6
            50 < wind && wind < 61 -> 7
            62 < wind && wind < 74 -> 8
            75 < wind && wind < 88 -> 9
            89 < wind && wind < 102 -> 10
            103 < wind && wind < 116 -> 11
            117 < wind && wind < 133 -> 12
            134 < wind && wind < 149 -> 13
            150 < wind && wind < 166 -> 14
            167 < wind && wind < 183 -> 15
            184 < wind && wind < 201 -> 16
            202 < wind && wind < 220 -> 17
            221 < wind -> 17
            else -> 0
        }
    }

    fun aqiToCh(aqi: Double): String {
        return when {
            aqi >= 0 && aqi < 50 -> "优"
            aqi >= 50 && aqi < 100 -> "良"
            aqi >= 100 && aqi < 150 -> "轻度"
            aqi >= 150 && aqi < 200 -> "中度"
            aqi >= 200 && aqi < 300 -> "重度"
            aqi >= 300 -> "严重"
            else -> "优"
        }
    }

    fun aqiToColor(context: Context, aqi: Double): Int {
        return when {
            aqi >= 0 && aqi < 50 -> ContextCompat.getColor(context, R.color.aqi_1)
            aqi >= 50 && aqi < 100 -> ContextCompat.getColor(context, R.color.aqi_2)
            aqi >= 100 && aqi < 150 -> ContextCompat.getColor(context, R.color.aqi_3)
            aqi >= 150 && aqi < 200 -> ContextCompat.getColor(context, R.color.aqi_4)
            aqi >= 200 && aqi < 300 -> ContextCompat.getColor(context, R.color.aqi_5)
            aqi >= 300 -> ContextCompat.getColor(context, R.color.aqi_6)
            else -> ContextCompat.getColor(context, R.color.aqi_1)
        }
    }

    fun getAndroiodScreenProperty(context: Context): List<Float> {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        val width: Int = dm.widthPixels         // 屏幕宽度（像素）
        val height: Int = dm.heightPixels       // 屏幕高度（像素）
        val density: Float = dm.density         // 屏幕密度（0.75 / 1.0 / 1.5）
        val densityDpi = dm.densityDpi     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        val screenWidth = (width / density).toInt()  // 屏幕宽度(dp)
        val screenHeight = (height / density).toInt()// 屏幕高度(dp)

        Logger.d("h_bl", "屏幕宽度（像素）：$width")
        Logger.d("h_bl", "屏幕高度（像素）：$height")
        Logger.d("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：$density")
        Logger.d("h_bl", "屏幕密度dpi（120 / 160 / 240）：$densityDpi")
        Logger.d("h_bl", "屏幕宽度（dp）：$screenWidth")
        Logger.d("h_bl", "屏幕高度（dp）：$screenHeight")

        return listOf(width.toFloat(), height.toFloat(), density)
    }

    /**
     * 判断是否为今天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    @Throws(ParseException::class)
    fun isToday(day: String): Boolean {
        val pre = Calendar.getInstance()
        val predate = Date(System.currentTimeMillis())
        pre.time = predate
        val cal = Calendar.getInstance()
        val date = getDateFormat().parse(day)
        cal.time = date
        if (cal.get(Calendar.YEAR) == pre.get(Calendar.YEAR)) {
            val diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR)
            if (diffDay == 0) {
                return true
            }
        }
        return false
    }

    /**
     * 判断是否为昨天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    @Throws(ParseException::class)
    fun isYesterday(day: String): Boolean {

        val pre = Calendar.getInstance()
        val predate = Date(System.currentTimeMillis())
        pre.time = predate

        val cal = Calendar.getInstance()
        val date = getDateFormat().parse(day)
        cal.time = date

        if (cal.get(Calendar.YEAR) == pre.get(Calendar.YEAR)) {
            val diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR)

            if (diffDay == -1) {
                return true
            }
        }
        return false
    }

    /**
     * Date转换为中文
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return 中文：9月8日
     * @throws ParseException
     */
    fun dateToCh(day: String): String {
        val cal = Calendar.getInstance()
        val date = getDateFormat().parse(day)
        cal.time = date
        return "${cal.get(Calendar.MONTH)+1}月${cal.get(Calendar.DAY_OF_MONTH)}日"
    }

    /**
     * Date转换为中文星期
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return 中文星期：周一
     * @throws ParseException
     */
    fun dateToWeekCh(day: String): String {
        val cal = Calendar.getInstance()
        val date = getDateFormat().parse(day)
        cal.time = date
        return when(cal.get(Calendar.DAY_OF_WEEK)) {
            1-> "周日"
            2-> "周一"
            3-> "周二"
            4-> "周三"
            5-> "周四"
            6-> "周五"
            7-> "周六"
            else -> "错误"
        }
    }

    private fun getDateFormat(): SimpleDateFormat {
        if (null == DateLocal.get()) {
            DateLocal.set(SimpleDateFormat("yyyy-MM-dd", Locale.CHINA))
        }
        return DateLocal.get()
    }

    private val DateLocal = ThreadLocal<SimpleDateFormat>()

}
