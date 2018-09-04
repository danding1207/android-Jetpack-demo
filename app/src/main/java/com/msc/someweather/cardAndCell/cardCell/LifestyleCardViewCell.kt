package com.msc.someweather.cardAndCell.cardCell

import android.support.v7.widget.LinearLayoutManager
import com.msc.someweather.adapters.LifestyleAdapter
import com.msc.someweather.cardAndCell.CommenDataCell
import com.msc.someweather.cardAndCell.viewCard.LifestyleCardView
import com.msc.someweather.http.bean.Lifestyle

class LifestyleCardViewCell : CommenDataCell<LifestyleCardView, Lifestyle>() {

    override fun bindView(view: LifestyleCardView) {
        super.bindView(view)

//        view.recyclerViewBg!!.layoutManager = LinearLayoutManager(view.context,
//                LinearLayoutManager.HORIZONTAL, false)
//
//        val adapter = LifestyleAdapter()
//        view.recyclerViewBg!!.adapter = adapter

//        adapter.submitList(mData!!.lifestyle)
    }

}
