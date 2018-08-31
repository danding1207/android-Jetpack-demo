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
 * The ViewModel for [WeatherFragment].
 */
class WeatherViewModel(private val fragment: Fragment) : ViewModel() {

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
            getLocation()
            applyData
        } as MutableLiveData<AMapLocation>
    }

    //声明AMapLocationClient类对象
    //声明定位回调监听器
    private val mLocationListener = AMapLocationListener {

        locationLiveObservableData.value = it

        if (it != null) {
            if (it.errorCode == 0) {
                //可在其中解析amapLocation获取相应内容。

                getCaiYunWeather(it.latitude, it.longitude )

                Logger.e(
                        "定位结果来源:${it.locationType}\n"+
                                "纬度:${it.latitude}\n"+
                                "经度:${it.longitude}\n"+
                                "精度信息:${it.accuracy}\n"+
                                "地址:${it.address}\n"+
                                "国家信息:${it.country}\n"+
                                "省信息:${it.province}\n"+
                                "城市信息:${it.city}\n"+
                                "城区信息:${it.district}\n"+
                                "街道信息:${it.street}\n"+
                                "街道门牌号信息:${it.streetNum}\n"+
                                "城市编码:${it.cityCode}\n"+
                                "地区编码:${it.adCode}\n"+
                                "定位点的AOI信息:${it.aoiName}\n"+
                                "室内定位的建筑物Id:${it.buildingId}\n"+
                                "室内定位的楼层:${it.floor}\n"+
                                "GPS的当前状态:${it.gpsAccuracyStatus}"
                )

            }else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Logger.e("location Error, ErrCode:"
                        + it.errorCode + ", errInfo:"
                        + it.errorInfo)
            }
        }

    }
    //初始化定位
    private val mLocationClient = AMapLocationClient(fragment.context!!)

    fun getLocation() {
        Logger.d("start_getLocation")

        RxPermissions(fragment)
                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe { // will emit 2 Permission objects
                    permission ->
                    when {
                        permission.granted -> {
                            // `permission.name` is granted !
                            Logger.d("该权限通过.")


                            //设置定位回调监听
                            mLocationClient.setLocationListener(mLocationListener)
                            //声明AMapLocationClientOption对象,初始化AMapLocationClientOption对象
                            val option = AMapLocationClientOption()
                            /**
                             * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
                             */
                            option.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.Transport
                            mLocationClient.setLocationOption(option)
                            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
                            mLocationClient.stopLocation()
                            mLocationClient.startLocation()
                            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
                            option.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
                            //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
                            option.interval = 10*60*1000
                            //设置是否返回地址信息（默认返回地址信息）
                            option.isNeedAddress = true
                            //关闭缓存机制
                            option.isLocationCacheEnable = false
                            //给定位客户端对象设置定位参数
                            mLocationClient.setLocationOption(option)
                            //启动定位
                            mLocationClient.startLocation()


                            Logger.d("end_getLocation")
                        }
                        permission.shouldShowRequestPermissionRationale -> {
                            // Denied permission without ask never again
                            Logger.d("该权限请未被denied过.")
                            MaterialDialog(fragment.context!!)
                                    .title(text = "权限请求")
                                    .message(text = "该权限用于GPS或网络定位，为更准确的提供天气信息，请同意请求！")
                                    .show()
                        }
                        else -> {
                            Logger.d("该权限请求已经被 Denied(拒绝)过。")
                            // Denied permission with ask never again
                            MaterialDialog(fragment.context!!)
                                    .title(text = "权限请求")
                                    .message(text = "该权限用于GPS或网络定位，为更准确的提供天气信息，请在设置中同意请求！")
                                    .show()
                        }
                    }
                }
    }

    private fun getCaiYunWeather(latitude: Double, longitude: Double) {

        val url = HttpUtils.getUrl("http://api.caiyunapp.com/v2/Y2FpeXVuIGFuZHJpb2QgYXBp/$longitude,$latitude/weather",
                mapOf("lang" to "zh_CN",
                        "span" to "16",
                        "begin" to "1535558400",
                        "hourlysteps" to "384",
                        "alert" to "true",
                        "tzshift" to "28800",
                        "version" to "4.0.1",
                        "device_id" to "867601030981778") as HashMap)

        DataRepository.getCaiYunWeather(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<CaiYunWeather> {
                    override fun onSubscribe(d: Disposable) {
                    }
                    override fun onNext(value: CaiYunWeather) {

                        caiYunWeatherLiveObservableData.value = value

                    }

                    override fun onError(e: Throwable) {
                        Logger.d("=======OfficialNotificationViewModel--onError=========")
                        e.printStackTrace()
                    }
                    override fun onComplete() {
                        Logger.d("=======OfficialNotificationViewModel--onComplete=========")
                    }
                })

    }

    fun onDestroy() {
        //停止定位后，本地定位服务并不会被销毁
        mLocationClient.stopLocation()
        //销毁定位客户端，同时销毁本地定位服务。
        mLocationClient.onDestroy()
    }


}
