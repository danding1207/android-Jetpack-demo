package com.msc.someweather.utilities

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.msc.someweather.R

object UnitUtils {


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

    fun windToLevel(wind: Float): String {
        return when {
            wind<1 -> "0级"
            5<wind && wind<1 -> "1级"
            6<wind && wind<11 -> "2级"
            12<wind && wind<19 -> "3级"
            20<wind && wind<28 -> "4级"
            29<wind && wind<38 -> "5级"
            39<wind && wind<49 -> "6级"
            50<wind && wind<61 -> "7级"
            62<wind && wind<74 -> "8级"
            75<wind && wind<88 -> "9级"
            89<wind && wind<102 -> "10级"
            103<wind && wind<116 -> "11级"
            117<wind && wind<133 -> "12级"
            134<wind && wind<149 -> "13级"
            150<wind && wind<166 -> "14级"
            167<wind && wind<183 -> "15级"
            184<wind && wind<201 -> "16级"
            202<wind && wind<220 -> "17级"
            221<wind -> "17级以上"
            else -> "0级"
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
            else -> "北风"
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
