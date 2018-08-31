package com.msc.someweather.cardAndCell.viewCard

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.msc.someweather.R

import com.tmall.wireless.tangram.structure.BaseCell
import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle

class NowAirCardView : FrameLayout, ITangramViewLifeCycle {

    var constraintLayoutBg: ConstraintLayout? = null
    var tvPubTime: TextView? = null
    var tvAqi: TextView? = null
    var tvMain: TextView? = null
    var tvQlty: TextView? = null
    var tvPm10: TextView? = null
    var tvPm25: TextView? = null
    var tvNo2: TextView? = null
    var tvSo2: TextView? = null
    var tvCo: TextView? = null
    var tvO3: TextView? = null

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
        View.inflate(context, R.layout.view_nowair_card, this)
        constraintLayoutBg = findViewById(R.id.constraintLayout_bg)

        tvPubTime = findViewById(R.id.tv_pub_time)
        tvAqi = findViewById(R.id.tv_aqi)
        tvMain = findViewById(R.id.tv_main)
        tvQlty = findViewById(R.id.tv_qlty)
        tvPm10 = findViewById(R.id.tv_pm10)
        tvPm25 = findViewById(R.id.tv_pm25)
        tvNo2 = findViewById(R.id.tv_no2)
        tvSo2 = findViewById(R.id.tv_so2)
        tvCo = findViewById(R.id.tv_co)
        tvO3 = findViewById(R.id.tv_o3)
    }

    override fun cellInited(cell: BaseCell<*>) {
        setOnClickListener(cell)
    }

    override fun postBindView(cell: BaseCell<*>) {
    }

    override fun postUnBindView(cell: BaseCell<*>) {
    }
}
