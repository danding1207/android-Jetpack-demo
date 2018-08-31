package com.msc.someweather.cardAndCell.cardCell

import android.support.v7.widget.LinearLayoutManager
import com.msc.someweather.adapters.ForecastAdapter
import com.msc.someweather.adapters.LifestyleAdapter
import com.msc.someweather.cardAndCell.CommenDataCell
import com.msc.someweather.cardAndCell.viewCard.ForecastCardView
import com.msc.someweather.cardAndCell.viewCard.LifestyleCardView
import com.msc.someweather.http.bean.Forecast
import com.msc.someweather.http.bean.Lifestyle

class ForecastCardViewCell : CommenDataCell<ForecastCardView, Forecast.HeWeather6Bean>() {

    override fun bindView(view: ForecastCardView) {
        super.bindView(view)

        view.recyclerViewBg!!.layoutManager = LinearLayoutManager(view.context,
                LinearLayoutManager.HORIZONTAL, false)

        val adapter = ForecastAdapter()
        view.recyclerViewBg!!.adapter = adapter

        adapter.submitList(mData!!.daily_forecast)
    }

}
