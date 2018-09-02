/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.msc.someweather

import android.arch.lifecycle.Observer
import android.Manifest
import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.location.Criteria
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.*
import com.afollestad.materialdialogs.MaterialDialog
import com.amap.api.location.AMapLocation
import com.google.gson.Gson
import com.msc.someweather.adapters.PlantAdapter
import com.msc.someweather.cardAndCell.cardCell.*
import com.msc.someweather.cardAndCell.viewCard.*
import com.msc.someweather.http.DataRepository
import com.msc.someweather.http.bean.*
import com.msc.someweather.utilities.HttpUtils
import com.msc.someweather.utilities.InjectorUtils
import com.msc.someweather.utilities.UnitUtils
import com.msc.someweather.viewmodels.PlantListViewModel
import com.msc.someweather.viewmodels.WeatherViewModel
import com.orhanobut.logger.Logger
import com.tbruyelle.rxpermissions2.RxPermissions
import com.tmall.wireless.tangram.TangramBuilder
import com.tmall.wireless.tangram.TangramEngine
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_weather.*
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class WeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel
    //    private lateinit var engine: TangramEngine
//    private lateinit var builder: TangramBuilder.InnerBuilder
    private var result: ArrayList<Any> = ArrayList()
    private var location: AMapLocation? = null


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        val factory = InjectorUtils.provideWeatherViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(WeatherViewModel::class.java)
        subscribeUi()
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshHeader.setColorSchemeResources(R.color.black)
//        refreshHeader.setShowBezierWave(true)

//        builder = TangramBuilder.newInnerBuilder(view.context)
//
//        //Step 3: register business cells and cards
//        builder.registerCell("location", LocationCardViewCell::class.java, LocationCardView::class.java)
//        builder.registerCell("nowweather", NowWeatherCardViewCell::class.java, NowWeatherCardView::class.java)
//        builder.registerCell("forecast", ForecastCardViewCell::class.java, ForecastCardView::class.java)
//        builder.registerCell("lifestyle", LifestyleCardViewCell::class.java, LifestyleCardView::class.java)
//        builder.registerCell("air", NowAirCardViewCell::class.java, NowAirCardView::class.java)
//
//        engine = builder.build()
//        engine.bindView(recyclerView)
//        //Step 6: enable auto load more if your page's data is lazy loaded
//        engine.enableAutoLoadMore(true)
////        engine.addSimpleClickSupport(SampleClickSupport())
//
//        //Step 9: set an offset to fix card
//        refreshLayout.setOnRefreshListener { refreshlayout ->
//            result.clear()
//            viewModel.getLocation()
//        }
//        refreshLayout.setOnLoadMoreListener { refreshlayout ->
////            viewModel.getMoreData()
//        }
    }


    private fun subscribeUi() {
        viewModel.locationLiveObservableData.observe(viewLifecycleOwner, Observer { location ->
            this.location = location
            activity!!.invalidateOptionsMenu()
        })

        viewModel.caiYunWeatherLiveObservableData.observe(viewLifecycleOwner, Observer { bean ->
            refreshLayout.finishRefresh(true)//传入false表示刷新失败
            refreshLayout.finishLoadMore()

            tv_name.text = UnitUtils.temperatureToCh(bean!!.result!!.realtime!!.skycon!!)

            tv_wind.text = UnitUtils.windToCh(bean.result!!.realtime!!.wind!!.direction)

            tv_tmp.text = "${bean.result!!.realtime!!.temperature}℃"


            val simpleDateFormat =  SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE)
            val date = Date(System.currentTimeMillis())


            Logger.e("Date: ${simpleDateFormat.format(date)}")

            val dailytmp = bean.result!!.daily!!.temperature!!.first{
                it.date == simpleDateFormat.format(date)
            }

            tv_tmp_range.text = "${dailytmp.min}℃ - ${dailytmp.max}"

            tv_aqi.text = "空气质量：${UnitUtils.aqiToCh(bean.result!!.realtime!!.aqi)}"

            tv_ultraviolet.text = "紫外线：${bean.result!!.realtime!!.ultraviolet!!.desc}"


            if(bean.result!!.forecast_keypoint!=null && bean.result!!.forecast_keypoint!!.length>12) {
                val head = bean.result!!.forecast_keypoint!!.substring(0 , bean.result!!.forecast_keypoint!!.length/2)
                val foot = bean.result!!.forecast_keypoint!!.substring(bean.result!!.forecast_keypoint!!.length/2 , bean.result!!.forecast_keypoint!!.length)
                tv_description.text = head
                tv_description_sub.text = foot
            } else {
                tv_description.text = bean.result!!.forecast_keypoint
                tv_description_sub.text = ""
            }

            val num = (Math.random() * 9).toInt()

            when(num) {
                0-> {
                    iv_bg.setImageResource(R.drawable.qiao)
                    tv_title.text = "桥"
                }
                1-> {
                    iv_bg.setImageResource(R.drawable.yu)
                    tv_title.text = "鱼"
                }
                2-> {
                    iv_bg.setImageResource(R.drawable.yan)
                    tv_title.text = "雁"

                }
                3-> {
                    iv_bg.setImageResource(R.drawable.que)
                    tv_title.text = "雀"

                }
                4-> {
                    iv_bg.setImageResource(R.drawable.nuan)
                    tv_title.text = "峦"

                }
                5-> {
                    iv_bg.setImageResource(R.drawable.mei)
                    tv_title.text = "梅"

                }
                6-> {
                    iv_bg.setImageResource(R.drawable.zhou)
                    tv_title.text = "舟"

                }
                7-> {
                    iv_bg.setImageResource(R.drawable.she)
                    tv_title.text = "舍"

                }
                8-> {
                    iv_bg.setImageResource(R.drawable.lan)
                    tv_title.text = "兰"

                }
            }

//            result.add(bean!!)
//            val data = Gson().toJson(result)
//            Logger.d(data)
//            val s = JSONArray(data)
//            engine.setData(s)

        })

    }



    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_location, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_location -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        menu!!.findItem(R.id.action_location).title =
                if (this.location == null) "定位中..."
                else "${this.location!!.district}-${this.location!!.aoiName}"
    }


}
