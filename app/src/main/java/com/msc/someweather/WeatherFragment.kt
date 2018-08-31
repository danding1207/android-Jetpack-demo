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
import com.msc.someweather.http.bean.Forecast
import com.msc.someweather.http.bean.Lifestyle
import com.msc.someweather.http.bean.NowWeather
import com.msc.someweather.http.bean.WeatherLocation
import com.msc.someweather.utilities.HttpUtils
import com.msc.someweather.utilities.InjectorUtils
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
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class WeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var engine: TangramEngine
    private lateinit var builder: TangramBuilder.InnerBuilder
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

        refreshHeader.setColorSchemeResources(R.color.colorPrimaryDark)
        refreshHeader.setShowBezierWave(true)

        builder = TangramBuilder.newInnerBuilder(view.context)

        //Step 3: register business cells and cards
        builder.registerCell("location", LocationCardViewCell::class.java, LocationCardView::class.java)
        builder.registerCell("nowweather", NowWeatherCardViewCell::class.java, NowWeatherCardView::class.java)
        builder.registerCell("forecast", ForecastCardViewCell::class.java, ForecastCardView::class.java)
        builder.registerCell("lifestyle", LifestyleCardViewCell::class.java, LifestyleCardView::class.java)
        builder.registerCell("air", NowAirCardViewCell::class.java, NowAirCardView::class.java)

        engine = builder.build()
        engine.bindView(recyclerView)
        //Step 6: enable auto load more if your page's data is lazy loaded
        engine.enableAutoLoadMore(true)
//        engine.addSimpleClickSupport(SampleClickSupport())

        //Step 9: set an offset to fix card
        refreshLayout.setOnRefreshListener { refreshlayout ->
            result.clear()
            viewModel.getLocation()
        }
        refreshLayout.setOnLoadMoreListener { refreshlayout ->
//            viewModel.getMoreData()
        }
    }


    private fun subscribeUi() {
        viewModel.locationLiveObservableData.observe(viewLifecycleOwner, Observer { location ->
            this.location = location
            activity!!.invalidateOptionsMenu()
        })

        viewModel.caiYunWeatherLiveObservableData.observe(viewLifecycleOwner, Observer { bean ->
            refreshLayout.finishRefresh(true)//传入false表示刷新失败
            refreshLayout.finishLoadMore()

            result.add(bean!!)
            val data = Gson().toJson(result)
            Logger.d(data)
            val s = JSONArray(data)
            engine.setData(s)

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
                else "${this.location!!.district} ${this.location!!.aoiName}"
    }


}
