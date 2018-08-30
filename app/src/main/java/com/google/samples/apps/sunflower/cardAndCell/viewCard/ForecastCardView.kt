package com.google.samples.apps.sunflower.cardAndCell.viewCard

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.google.samples.apps.sunflower.R

import com.tmall.wireless.tangram.structure.BaseCell
import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle

class ForecastCardView : FrameLayout, ITangramViewLifeCycle {

    var recyclerViewBg: RecyclerView? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.view_lifestyle_card, this)
        recyclerViewBg = findViewById(R.id.recyclerView_bg)

    }

    override fun cellInited(cell: BaseCell<*>) {
//        setOnClickListener(cell)
    }

    override fun postBindView(cell: BaseCell<*>) {
    }

    override fun postUnBindView(cell: BaseCell<*>) {
    }
}
