package com.google.samples.apps.sunflower.cardAndCell.viewCard

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.google.samples.apps.sunflower.R

import com.tmall.wireless.tangram.structure.BaseCell
import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle

class LocationCardView : FrameLayout, ITangramViewLifeCycle {

    var constraintLayoutBg: ConstraintLayout? = null
    var tvCnty: TextView? = null
    var tvLocation: TextView? = null
    var tvType: TextView? = null
    var tvLat: TextView? = null
    var tvLon: TextView? = null

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
        View.inflate(context, R.layout.view_location_card, this)
        constraintLayoutBg = findViewById(R.id.constraintLayout_bg)
        tvCnty = findViewById(R.id.tv_cnty)
        tvLocation = findViewById(R.id.tv_location)
        tvType = findViewById(R.id.tv_type)
        tvLat = findViewById(R.id.tv_lat)
        tvLon = findViewById(R.id.tv_lon)
    }

    override fun cellInited(cell: BaseCell<*>) {
        setOnClickListener(cell)
    }

    override fun postBindView(cell: BaseCell<*>) {
    }

    override fun postUnBindView(cell: BaseCell<*>) {
    }
}
