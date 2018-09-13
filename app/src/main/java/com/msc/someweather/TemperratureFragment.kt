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

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.msc.someweather.http.bean.CaiYunWeather
import com.msc.someweather.http.bean.CaiYunWeather.ResultBean
import com.msc.someweather.utilities.InjectorUtils
import com.msc.someweather.viewmodels.TemperratureViewModel
import kotlinx.android.synthetic.main.fragment_temperrature.*

class TemperratureFragment : Fragment() {

    private lateinit var viewModel: TemperratureViewModel
    private lateinit var result: ResultBean

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val weatherString = AqiFragmentArgs.fromBundle(arguments).weather

        result = Gson().fromJson(weatherString,
                CaiYunWeather.ResultBean::class.java)

        val view = inflater.inflate(R.layout.fragment_temperrature, container, false)
        val factory = InjectorUtils.provideTemperratureViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(TemperratureViewModel::class.java)
        subscribeUi()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        tv_weather_desc.text = result.hourly!!.description

        temperratureView.result = result
        temperratureView.invalidateView()

    }


    private fun subscribeUi() {

    }


    override fun onDestroy() {
        super.onDestroy()
    }


}
