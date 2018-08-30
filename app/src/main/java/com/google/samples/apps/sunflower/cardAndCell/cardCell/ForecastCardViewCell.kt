package com.google.samples.apps.sunflower.cardAndCell.cardCell

import android.support.v7.widget.LinearLayoutManager
import com.google.samples.apps.sunflower.adapters.ForecastAdapter
import com.google.samples.apps.sunflower.adapters.LifestyleAdapter
import com.google.samples.apps.sunflower.cardAndCell.CommenDataCell
import com.google.samples.apps.sunflower.cardAndCell.viewCard.ForecastCardView
import com.google.samples.apps.sunflower.cardAndCell.viewCard.LifestyleCardView
import com.google.samples.apps.sunflower.http.bean.Forecast
import com.google.samples.apps.sunflower.http.bean.Lifestyle

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
