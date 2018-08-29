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
import android.app.Dialog
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
import com.afollestad.materialdialogs.MaterialDialog
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
        /**
         * 定位
         */
        getLocation()
    }

    private fun getLocation() {
        Logger.d("start_getLocation")
        val criteria = Criteria()
        criteria.powerRequirement = Criteria.POWER_MEDIUM//设置耗电量为低耗电
        criteria.bearingAccuracy = Criteria.ACCURACY_HIGH//设置精度标准为粗糙
        criteria.isAltitudeRequired = false//设置海拔不需要   ACCURACY_COARSE
        criteria.isBearingRequired = false//设置导向不需要
        criteria.accuracy = Criteria.ACCURACY_FINE//设置精度为低  ACCURACY_HIGH
        criteria.isCostAllowed = false//设置成本为不需要
        //... Criteria 还有其他属性

        val manager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val bestProvider = manager.getBestProvider(criteria, true)
        //得到定位信息

        RxPermissions(this)
                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe { // will emit 2 Permission objects
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
//                            val geocoder = Geocoder(activity!!, Locale.CHINESE)
//                            val addresses = geocoder.getFromLocation(location.latitude,
//                                    location.longitude, 1)
//                            if (addresses.size <= 0) {
//                                Logger.d("获取地址失败")
//                            }
//                            val address = addresses[0]
//                            val country = address.countryName//得到国家
//                            val locality = address.locality//得到城市
                            //要获得哪些信息自己看咯

                            Logger.d("end_getLocation")
                            tv_weather.text = "经度：${location.latitude}；\n纬度：${location.longitude}"
                            initCity("${location.longitude},${location.latitude}")
                        }
                        permission.shouldShowRequestPermissionRationale -> {
                            // Denied permission without ask never again
                            Logger.d("该权限请未被denied过.")
                            MaterialDialog(activity!!)
                                    .title(text = "权限请求")
                                    .message(text = "该权限用于GPS或网络定位，为更准确的提供天气信息，请同意请求！")
                                    .show()
                        }
                        else -> {
                            Logger.d("该权限请求已经被 Denied(拒绝)过。")
                            // Denied permission with ask never again
                            MaterialDialog(activity!!)
                                    .title(text = "权限请求")
                                    .message(text = "该权限用于GPS或网络定位，为更准确的提供天气信息，请在设置中同意请求！")
                                    .show()
                        }
                    }
                }
    }

    private fun initCity(value: String) {

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

                        if (value.HeWeather6 != null &&
                                value.HeWeather6!!.isNotEmpty() &&
                                (value.HeWeather6!![0].status == "ok") &&
                                value.HeWeather6!![0].basic != null &&
                                value.HeWeather6!![0].basic!!.isNotEmpty()) {

                            tv_weather.append(
                                    "\n地区／城市名称：${value.HeWeather6!![0].basic!![0].location}"
                                            + "\n地区／城市ID：${value.HeWeather6!![0].basic!![0].cid}"
                                            + "\n该地区／城市的上级城市：${value.HeWeather6!![0].basic!![0].parent_city}"
                                            + "\n该地区／城市所属行政区域：${value.HeWeather6!![0].basic!![0].admin_area}"
                                            + "\n该地区／城市所属国家名称：${value.HeWeather6!![0].basic!![0].cnty}"
                                            + "\n该地区／城市所在时区：${value.HeWeather6!![0].basic!![0].tz}"
                                            + "\n该地区／城市的属性：${value.HeWeather6!![0].basic!![0].type}")


                            initWeather(value.HeWeather6!![0].basic!![0].cid!!)
                            initForecastWeather(value.HeWeather6!![0].basic!![0].cid!!)
                            initLifestyle(value.HeWeather6!![0].basic!![0].cid!!)

                        }

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

    private fun initWeather(cityID: String) {

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

                        if (value.HeWeather6 != null &&
                                value.HeWeather6!!.isNotEmpty() &&
                                (value.HeWeather6!![0].status == "ok") &&
                                value.HeWeather6!![0].now != null &&
                                value.HeWeather6!![0].basic != null &&
                                value.HeWeather6!![0].update != null ) {

                            tv_weather.append(
                                    "\n当地时间：${value.HeWeather6!![0].update!!.loc}"
                                            + "\nUTC时间：${value.HeWeather6!![0].update!!.utc}"
                                            + "\n体感温度：${value.HeWeather6!![0].now!!.fl}"
                                            + "\n温度：${value.HeWeather6!![0].now!!.tmp}"
                                            + "\n实况天气状况代码：${value.HeWeather6!![0].now!!.cond_code}"
                                            + "\n实况天气状况描述：${value.HeWeather6!![0].now!!.cond_txt}"
                                            + "\n风向360角度：${value.HeWeather6!![0].now!!.wind_deg}"
                                            + "\n风向：${value.HeWeather6!![0].now!!.wind_dir}"
                                            + "\n风力：${value.HeWeather6!![0].now!!.wind_sc}"
                                            + "\n风速：${value.HeWeather6!![0].now!!.wind_spd}"
                                            + "\n相对湿度：${value.HeWeather6!![0].now!!.hum}"
                                            + "\n降水量：${value.HeWeather6!![0].now!!.pcpn}"
                                            + "\n大气压强：${value.HeWeather6!![0].now!!.pres}"
                                            + "\n能见度：${value.HeWeather6!![0].now!!.vis}"
                                            + "\n云量：${value.HeWeather6!![0].now!!.cloud}")

                        }

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

    private fun initForecastWeather(cityID: String) {

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

                        if (value.HeWeather6 != null &&
                                value.HeWeather6!!.isNotEmpty() &&
                                (value.HeWeather6!![0].status == "ok") &&
                                value.HeWeather6!![0].daily_forecast != null &&
                                value.HeWeather6!![0].daily_forecast!!.isNotEmpty() &&
                                value.HeWeather6!![0].basic != null &&
                                value.HeWeather6!![0].update != null ) {

                            value.HeWeather6!![0].daily_forecast!!.forEach {
                                tv_weather.append(
                                        "\n预报日期：${it.date}"
                                                + "\n日出时间：${it.sr}"
                                                + "\n日落时间：${it.ss}"
                                                + "\n月升时间：${it.mr}"
                                                + "\n月落时间：${it.ms}"
                                                + "\n最高温度：${it.tmp_max}"
                                                + "\n最低温度：${it.tmp_min}"
                                                + "\n白天天气状况代码：${it.cond_code_d}"
                                                + "\n晚间天气状况代码：${it.cond_code_n}"
                                                + "\n白天天气状况描述：${it.cond_txt_d}"
                                                + "\n晚间天气状况描述：${it.cond_txt_n}"
                                                + "\n风向360角度：${it.wind_deg}"
                                                + "\n风向：${it.wind_dir}"
                                                + "\n风力：${it.wind_sc}"
                                                + "\n风速：${it.wind_spd}"
                                                + "\n相对湿度：${it.hum}"
                                                + "\n降水量：${it.pcpn}"
                                                + "\n大气压强：${it.pres}"
                                                + "\n能见度：${it.vis}"
                                )
                            }

                        }

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

    private fun initLifestyle(cityID: String) {

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

                        if (value.HeWeather6 != null &&
                                value.HeWeather6!!.isNotEmpty() &&
                                (value.HeWeather6!![0].status == "ok") &&
                                value.HeWeather6!![0].lifestyle != null &&
                                value.HeWeather6!![0].lifestyle!!.isNotEmpty() &&
                                value.HeWeather6!![0].basic != null &&
                                value.HeWeather6!![0].update != null ) {

                            value.HeWeather6!![0].lifestyle!!.forEach {
                                tv_weather.append(
                                        "\n预报日期：${it.date}"
                                                + "\n生活指数简介：${it.brf}"
                                                + "\n生活指数详细描述：${it.txt}"
                                                + "\n生活指数类型：${it.type}"
                                )
                            }

                        }
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
