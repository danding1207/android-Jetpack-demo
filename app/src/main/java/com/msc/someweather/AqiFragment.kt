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
import android.arch.lifecycle.ViewModelProviders
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.github.mikephil.charting.components.Legend
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.msc.someweather.adapters.LifestyleAdapter
import com.msc.someweather.http.bean.CaiYunWeather
import com.msc.someweather.http.bean.Lifestyle
import com.msc.someweather.utilities.InjectorUtils
import com.msc.someweather.utilities.UnitUtils
import com.msc.someweather.viewmodels.AqiViewModel
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_weather.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

import com.msc.someweather.http.bean.CaiYunWeather.ResultBean.HourlyBean.AqiBean
import kotlinx.android.synthetic.main.fragment_aqi.*
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.msc.someweather.http.bean.CaiYunWeather.ResultBean

class AqiFragment : Fragment() {

    private lateinit var viewModel: AqiViewModel
    private lateinit var result: ResultBean

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val weatherString = AqiFragmentArgs.fromBundle(arguments).weather

        result = Gson().fromJson(weatherString,
                CaiYunWeather.ResultBean::class.java)

        val view = inflater.inflate(R.layout.fragment_aqi, container, false)
        val factory = InjectorUtils.provideAqiViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(AqiViewModel::class.java)
        subscribeUi()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_aqi_detail.text = result.realtime!!.aqi.toString()

        tv_pm25_detail.text = result.realtime!!.pm25.toString()
        tv_pm10_detail.text = result.realtime!!.pm10.toString()
        tv_so2_detail.text = result.realtime!!.so2.toString()
        tv_no2_detail.text = result.realtime!!.no2.toString()
        tv_co_detail.text = result.realtime!!.co.toString()
        tv_o3_detail.text = result.realtime!!.o3.toString()

    }


    private fun subscribeUi() {

    }


    override fun onDestroy() {
        super.onDestroy()
    }


}
