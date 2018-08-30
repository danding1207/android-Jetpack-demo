package com.google.samples.apps.sunflower.cardAndCell.cardCell

import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.View
import com.google.samples.apps.sunflower.cardAndCell.CommenDataCell
import com.google.samples.apps.sunflower.cardAndCell.viewCard.LocationCardView
import com.google.samples.apps.sunflower.cardAndCell.viewCard.NowWeatherCardView
import com.google.samples.apps.sunflower.http.bean.NowWeather
import com.google.samples.apps.sunflower.http.bean.WeatherLocation


class NowWeatherCardViewCell : CommenDataCell<NowWeatherCardView, NowWeather.HeWeather6Bean.NowBean>() {

    override fun bindView(view: NowWeatherCardView) {
        super.bindView(view)

        view.tvCondTxt!!.text = mData!!.cond_txt
        view.tvFl!!.text = "体感温度：${mData!!.fl}"
        view.tvTmp!!.text = "温度：${mData!!.tmp}"
        view.tvWindDeg!!.text = "风向角度：${mData!!.wind_deg}"
        view.tvWindDir!!.text = "风向：${mData!!.wind_dir}"
        view.tvWindSc!!.text = "风力：${mData!!.wind_sc}"
        view.tvWindSpd!!.text = "风速：${mData!!.wind_spd}"
        view.tvHum!!.text = "相对湿度：${mData!!.hum}"
        view.tvPcpn!!.text = "降水量：${mData!!.pcpn}"
        view.tvPres!!.text = "大气压强：${mData!!.pres}"
        view.tvVis!!.text = "能见度：${mData!!.vis}"
        view.tvCloud!!.text = "云量：${mData!!.cloud}"

    }

}
