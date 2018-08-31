package com.msc.someweather.cardAndCell.cardCell

import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.View
import com.msc.someweather.cardAndCell.CommenDataCell
import com.msc.someweather.cardAndCell.viewCard.LocationCardView
import com.msc.someweather.http.bean.WeatherLocation


class LocationCardViewCell : CommenDataCell<LocationCardView, WeatherLocation.HeWeather6Bean.BasicBean>() {

    override fun bindView(view: LocationCardView) {
        super.bindView(view)

        view.tvCnty!!.text = mData!!.cnty
        view.tvLocation!!.text = mData!!.location
        view.tvLat!!.text = "经度：${mData!!.lat}"
        view.tvLon!!.text = "纬度：${mData!!.lon}"

        when (mData!!.cityType) {
            "city" -> view.tvType!!.text = "城市"
            "scenic" -> view.tvType!!.text = "景点"
        }

    }

}
