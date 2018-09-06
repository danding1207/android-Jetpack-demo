package com.msc.someweather.utilities

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.msc.someweather.R

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
            wind<22.5 || wind >=337.5 -> 180
            wind >=22.5 && wind<67.5 -> 225
            wind >=67.5 && wind<112.5 -> 270
            wind >=112.5 && wind<157.5 -> 315
            wind >=157.5 && wind<202.5 -> 0
            wind >=202.5 && wind<247.5 -> 45
            wind >=247.5 && wind<292.5 -> 90
            wind >=292.5 && wind<337.5 -> 135
            else -> 0
        }
    }

    fun windToLevel(wind: Double): Int {
        return when {
            wind<1 -> 0
            5<wind && wind<1 -> 1
            6<wind && wind<11 -> 2
            12<wind && wind<19 -> 3
            20<wind && wind<28 -> 4
            29<wind && wind<38 -> 5
            39<wind && wind<49 -> 6
            50<wind && wind<61 -> 7
            62<wind && wind<74 -> 8
            75<wind && wind<88 -> 9
            89<wind && wind<102 -> 10
            103<wind && wind<116 -> 11
            117<wind && wind<133 -> 12
            134<wind && wind<149 -> 13
            150<wind && wind<166 -> 14
            167<wind && wind<183 -> 15
            184<wind && wind<201 -> 16
            202<wind && wind<220 -> 17
            221<wind -> 17
            else -> 0
        }
    }

    fun aqiToCh(aqi: Int): String {
        return when (aqi / 50) {
            0 -> "优"
            1 -> "良"
            2 -> "轻度污染"
            3 -> "中度污染"
            4, 5 -> "重度污染"
            6,7,8,9,10 -> "严重污染"
            else -> "严重污染"
        }
    }

    fun aqiToColor(context: Context, aqi: Int): Int {
        return when (aqi / 50) {
            0 -> ContextCompat.getColor(context, R.color.aqi_1)
            1 -> ContextCompat.getColor(context, R.color.aqi_2)
            2 -> ContextCompat.getColor(context, R.color.aqi_3)
            3 -> ContextCompat.getColor(context, R.color.aqi_4)
            4, 5 -> ContextCompat.getColor(context, R.color.aqi_5)
            6,7,8,9,10 -> ContextCompat.getColor(context, R.color.aqi_6)
            else -> ContextCompat.getColor(context, R.color.aqi_1)
        }
    }

}
