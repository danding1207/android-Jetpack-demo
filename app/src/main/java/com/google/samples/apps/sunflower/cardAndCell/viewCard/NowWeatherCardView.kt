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

class NowWeatherCardView : FrameLayout, ITangramViewLifeCycle {

    var constraintLayoutBg: ConstraintLayout? = null
    var tvCondTxt: TextView? = null
    var tvFl: TextView? = null
    var tvTmp: TextView? = null
    var tvWindDeg: TextView? = null
    var tvWindDir: TextView? = null
    var tvWindSc: TextView? = null
    var tvWindSpd: TextView? = null
    var tvHum: TextView? = null
    var tvPcpn: TextView? = null
    var tvPres: TextView? = null
    var tvVis: TextView? = null
    var tvCloud: TextView? = null

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
        View.inflate(context, R.layout.view_nowweather_card, this)
        constraintLayoutBg = findViewById(R.id.constraintLayout_bg)

        tvCondTxt = findViewById(R.id.tv_cond_txt)
        tvFl = findViewById(R.id.tv_fl)
        tvTmp = findViewById(R.id.tv_tmp)
        tvWindDeg = findViewById(R.id.tv_wind_deg)
        tvWindDir = findViewById(R.id.tv_wind_dir)
        tvWindSc = findViewById(R.id.tv_wind_sc)
        tvWindSpd = findViewById(R.id.tv_wind_spd)
        tvHum = findViewById(R.id.tv_hum)
        tvPcpn = findViewById(R.id.tv_pcpn)
        tvPres = findViewById(R.id.tv_pres)
        tvVis = findViewById(R.id.tv_vis)
        tvCloud = findViewById(R.id.tv_cloud)
    }

    override fun cellInited(cell: BaseCell<*>) {
        setOnClickListener(cell)
    }

    override fun postBindView(cell: BaseCell<*>) {
    }

    override fun postUnBindView(cell: BaseCell<*>) {
    }
}
