package com.msc.someweather.cardAndCell.cardCell

import com.msc.someweather.cardAndCell.CommenDataCell
import com.msc.someweather.cardAndCell.viewCard.NowAirCardView
import com.msc.someweather.http.bean.AirNow

class NowAirCardViewCell : CommenDataCell<NowAirCardView, AirNow.HeWeather6Bean>() {

    override fun bindView(view: NowAirCardView) {
        super.bindView(view)

        view.tvPubTime!!.text = "数据发布时间：${mData!!.air_now_city!!.pub_time}"
        view.tvAqi!!.text = "空气质量指数：${mData!!.air_now_city!!.aqi}"
        view.tvMain!!.text = "主要污染物：${mData!!.air_now_city!!.main}"
        view.tvQlty!!.text = "空气质量：${mData!!.air_now_city!!.qlty}"
        view.tvPm10!!.text = "pm10：${mData!!.air_now_city!!.pm10}"
        view.tvPm25!!.text = "pm25：${mData!!.air_now_city!!.pm25}"
        view.tvNo2!!.text = "二氧化氮：${mData!!.air_now_city!!.no2}"
        view.tvSo2!!.text = "二氧化硫：${mData!!.air_now_city!!.so2}"
        view.tvCo!!.text = "一氧化碳：${mData!!.air_now_city!!.co}"
        view.tvO3!!.text = "臭氧：${mData!!.air_now_city!!.o3}"

    }

}
