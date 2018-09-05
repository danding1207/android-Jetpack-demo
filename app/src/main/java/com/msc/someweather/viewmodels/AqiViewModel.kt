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

package com.msc.someweather.viewmodels

import android.Manifest
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import android.location.Location
import android.support.v4.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.msc.someweather.http.DataRepository
import com.msc.someweather.http.bean.*
import com.msc.someweather.utilities.HttpUtils
import com.msc.someweather.utilities.PermissionsUtils
import com.orhanobut.logger.Logger
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * The ViewModel for [AlertFragment].
 */
class AqiViewModel(private val fragment: Fragment) : ViewModel() {

    var locationLiveObservableData = MutableLiveData<AMapLocation>()

    var caiYunWeatherLiveObservableData = MutableLiveData<CaiYunWeather>()

    init {
        //这里的trigger为网络检测，也可以换成缓存数据是否存在检测
        locationLiveObservableData = switchMap<Boolean, AMapLocation>(
                PermissionsUtils.isGranted(fragment.context!!, Manifest.permission.ACCESS_FINE_LOCATION)
        ) { hasPermissions ->
            if (!hasPermissions) {

            }
            val applyData = MutableLiveData<AMapLocation>()
            applyData
        } as MutableLiveData<AMapLocation>
    }




}
