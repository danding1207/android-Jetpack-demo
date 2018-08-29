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
package com.google.samples.apps.sunflower

import android.Manifest
import android.content.Context
import android.location.Criteria
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.samples.apps.sunflower.http.DataRepository
import com.google.samples.apps.sunflower.http.bean.Forecast
import com.google.samples.apps.sunflower.http.bean.Lifestyle
import com.google.samples.apps.sunflower.http.bean.NowWeather
import com.google.samples.apps.sunflower.utilities.HttpUtils
import com.orhanobut.logger.Logger
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_weather.*
import java.util.*
import kotlin.collections.HashMap

class WeatherFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getLocation()

    }

    fun getLocation() {

        Logger.d("start_getLocation")

        val criteria = Criteria()
        criteria.powerRequirement = Criteria.POWER_MEDIUM//设置耗电量为低耗电
        criteria.bearingAccuracy = Criteria.ACCURACY_HIGH//设置精度标准为粗糙
        criteria.isAltitudeRequired = false//设置海拔不需要   ACCURACY_COARSE
        criteria.isBearingRequired = false//设置导向不需要
        criteria.accuracy = Criteria.ACCURACY_FINE//设置精度为低  ACCURACY_HIGH
        criteria.isCostAllowed = false//设置成本为不需要
        //... Criteria 还有其他属性

        Logger.d("Criteria")

        val manager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val bestProvider = manager.getBestProvider(criteria, true)
        //得到定位信息

        RxPermissions(this)
                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe({ // will emit 2 Permission objects
                    permission ->
                    when {
                        permission.granted -> {
                            // `permission.name` is granted !
                            Logger.d("该权限通过.")


                            var location: Location? = null
                            if (!TextUtils.isEmpty(bestProvider)) {
                                Logger.d("bestProvider")

                                location = manager.getLastKnownLocation(bestProvider)
                            }
                            if (null == location) {
                                Logger.d("no bestProvider")

                                //如果没有最好的定位方案则手动配置
                                when {
                                    manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                                    manager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                                    manager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER) -> location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                                }
                            }
                            if (null == location) {
                                Logger.d("获取定位失败")
                                return@subscribe
                            }
                            //通过地理编码的到具体位置信息
                            val geocoder = Geocoder(activity!!, Locale.CHINESE)
                            val addresses = geocoder.getFromLocation(location.latitude,
                                    location.longitude, 1)
                            if (addresses.size <= 0) {
                                Logger.d("获取地址失败")
                            }
                            val address = addresses[0]
                            val country = address.countryName//得到国家
                            val locality = address.locality//得到城市
                            //要获得哪些信息自己看咯

                            Logger.d("end_getLocation")

                            tv_weather.text = "经度：${location.latitude}；\n纬度：${location.longitude}；\n国家：$country；\n城市：$locality"

                            initCity("${location.longitude},${location.latitude}")

                        }
                        permission.shouldShowRequestPermissionRationale -> {
                            // Denied permission without ask never again
                            Logger.d("该权限请未被denied过.")
                        }
                        else -> {
                            Logger.d("该权限请求已经被 Denied(拒绝)过。")
                            // Denied permission with ask never again
                            // Need to go to the settings
                        }
                    }
                })



    }

    private fun initCity(value:String) {

       val url = HttpUtils.getUrl("https://search.heweather.com/find",
                mapOf("key" to "532df559756b42cd938942d3cea012d3",
                "location" to value) as HashMap)

        DataRepository.searchLocation(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<com.google.samples.apps.sunflower.http.bean.Location> {
                    override fun onSubscribe(d: Disposable) {

                    }
                    override fun onNext(value: com.google.samples.apps.sunflower.http.bean.Location) {

//                        tv_weather.append("\n$value")

                        initWeather(value.heWeather6!![0].basic[0].cid)
                        initForecastWeather(value.heWeather6!![0].basic[0].cid)
                        initLifestyle(value.heWeather6!![0].basic[0].cid)
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

    private fun initWeather(cityID:String) {

        val url = HttpUtils.getUrl("https://free-api.heweather.com/s6/weather/now",
                mapOf("key" to "532df559756b42cd938942d3cea012d3",
                        "location" to cityID) as HashMap)

        DataRepository.getNowWeather(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<NowWeather> {
                    override fun onSubscribe(d: Disposable) {

                    }
                    override fun onNext(value: NowWeather) {

                        Logger.d(value.toString())

                        tv_weather.append("\n${value.heWeather6[0].now}")

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

    private fun initForecastWeather(cityID:String) {

        val url = HttpUtils.getUrl("https://free-api.heweather.com/s6/weather/forecast",
                mapOf("key" to "532df559756b42cd938942d3cea012d3",
                        "location" to cityID) as HashMap)

        DataRepository.getForecastWeather(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Forecast> {
                    override fun onSubscribe(d: Disposable) {

                    }
                    override fun onNext(value: Forecast) {

                        Logger.d(value.toString())

//                        tv_weather.append("\n${value.heWeather6!![0].daily_forecast!![0]}")

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

    private fun initLifestyle(cityID:String) {

        val url = HttpUtils.getUrl("https://free-api.heweather.com/s6/weather/lifestyle",
                mapOf("key" to "532df559756b42cd938942d3cea012d3",
                        "location" to cityID) as HashMap)

        DataRepository.getLifestyle(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Lifestyle> {
                    override fun onSubscribe(d: Disposable) {

                    }
                    override fun onNext(value: Lifestyle) {

                        Logger.d(value.toString())

//                        tv_weather.append("\n${value.heWeather6!![0].lifestyle!![0]}")

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


}
