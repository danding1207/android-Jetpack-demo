package com.msc.someweather.utilities

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


}
