package com.google.samples.apps.sunflower.cardAndCell.cardCell

import android.support.v7.widget.LinearLayoutManager
import com.google.samples.apps.sunflower.adapters.LifestyleAdapter
import com.google.samples.apps.sunflower.cardAndCell.CommenDataCell
import com.google.samples.apps.sunflower.cardAndCell.viewCard.LifestyleCardView
import com.google.samples.apps.sunflower.http.bean.Lifestyle

class LifestyleCardViewCell : CommenDataCell<LifestyleCardView, Lifestyle.HeWeather6Bean>() {

    override fun bindView(view: LifestyleCardView) {
        super.bindView(view)

        view.recyclerViewBg!!.layoutManager = LinearLayoutManager(view.context,
                LinearLayoutManager.HORIZONTAL, false)

        val adapter = LifestyleAdapter()
        view.recyclerViewBg!!.adapter = adapter

        adapter.submitList(mData!!.lifestyle)
    }

}
