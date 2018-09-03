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
import com.google.gson.reflect.TypeToken
import com.msc.someweather.adapters.FragmentAdapter
import com.msc.someweather.http.bean.CaiYunWeather.ResultBean.AlertBean.AlertContentBean
import com.msc.someweather.utilities.InjectorUtils
import com.msc.someweather.viewmodels.AlertViewModel
import kotlinx.android.synthetic.main.fragment_alert.*
import java.util.*


class AlertFragment : Fragment() {

    private lateinit var viewModel: AlertViewModel
    private lateinit var alertList: List<AlertContentBean>
    private var mAdapter: FragmentAdapter? = null
    private val mFragments = ArrayList<Fragment>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val alertListString = AlertFragmentArgs.fromBundle(arguments).alertList

        alertList = Gson().fromJson(alertListString,
                object : TypeToken<List<AlertContentBean>>() {}.type)

        val view = inflater.inflate(R.layout.fragment_alert, container, false)
        val factory = InjectorUtils.provideAlertViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(AlertViewModel::class.java)
        subscribeUi()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertList.forEach {
            mFragments.add(AlertDetailFragment.newInstance(it))
        }

        mAdapter = FragmentAdapter(childFragmentManager, mFragments)
        container_pager.adapter = mAdapter

    }


    private fun subscribeUi() {

    }


}
