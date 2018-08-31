package com.msc.someweather.http

import com.msc.someweather.http.bean.*
import io.reactivex.Observable

object DataRepository {

//    //可以操作Observable来筛选网络或者是本地数据
//    val followDataRepository: Observable<FollowData>
//        get() {
//
//            val observableForGetFollowDataRepositoryFromNetWork = ApiClient
//                    .eyepetizerDataService.followData
//
//            return ApiClient
//                    .eyepetizerDataService.followData
//        }


    //可以操作Observable来筛选网络或者是本地数据
    fun searchLocation(url: String): Observable<WeatherLocation> {
        return ApiClient.dataService.searchLocation(url)
    }

    //可以操作Observable来筛选网络或者是本地数据
    fun getNowWeather(url: String): Observable<NowWeather> {
        return ApiClient.dataService.getNowWeather(url)
    }

    //可以操作Observable来筛选网络或者是本地数据
    fun getForecastWeather(url: String): Observable<Forecast> {
        return ApiClient.dataService.getForecastWeather(url)
    }

    //可以操作Observable来筛选网络或者是本地数据
    fun getLifestyle(url: String): Observable<Lifestyle> {
        return ApiClient.dataService.getLifestyle(url)
    }

    //可以操作Observable来筛选网络或者是本地数据
    fun getAirNow(url: String): Observable<AirNow> {
        return ApiClient.dataService.getAirNow(url)
    }

    //可以操作Observable来筛选网络或者是本地数据
    fun getCaiYunWeather(url: String): Observable<CaiYunWeather> {
        return ApiClient.dataService.getCaiYunWeather(url)
    }

//    //可以操作Observable来筛选网络或者是本地数据
//    fun getMoreRecDataRepository(nextPageUrl: String): Observable<CommonData> {
//        return ApiClient
//                .eyepetizerDataService.getMoreRecData(nextPageUrl)
//    }
//
//    //可以操作Observable来筛选网络或者是本地数据
//    fun getFeedDataRepository(udid: String, vc: String,
//                              vn: String, deviceModel: String, first_channel: String,
//                              last_channel: String, system_version_code: String): Observable<FeedData> {
//        return ApiClient
//                .eyepetizerDataService.getFeedData(udid, vc,
//                vn, deviceModel, first_channel,
//                last_channel, system_version_code)
//    }
//
//    //可以操作Observable来筛选网络或者是本地数据
//    fun getMoreFeedDataRepository(nextPageUrl: String): Observable<FeedData> {
//        return ApiClient
//                .eyepetizerDataService.getMoreFeedData(nextPageUrl)
//    }
//
//    //可以操作Observable来筛选网络或者是本地数据
//    fun getDiscoveryDataRepository(udid: String, vc: String,
//                                   vn: String, deviceModel: String, first_channel: String,
//                                   last_channel: String, system_version_code: String): Observable<DiscoveryData> {
//        return ApiClient
//                .eyepetizerDataService.getDiscoveryData(udid, vc,
//                vn, deviceModel, first_channel,
//                last_channel, system_version_code)
//    }
//
//    //可以操作Observable来筛选网络或者是本地数据
//    fun getMoreDiscoveryDataRepository(nextPageUrl: String): Observable<DiscoveryData> {
//        return ApiClient
//                .eyepetizerDataService.getMoreDiscoveryData(nextPageUrl)
//    }
//
//
//    //可以操作Observable来筛选网络或者是本地数据
//    fun getMessagesDataRepository(udid: String, vc: String,
//                                  vn: String, deviceModel: String, first_channel: String,
//                                  last_channel: String, system_version_code: String): Observable<MessagesData> {
//        return ApiClient
//                .eyepetizerDataService.getMessagesData(udid, vc,
//                vn, deviceModel, first_channel,
//                last_channel, system_version_code)
//    }
//
//    //可以操作Observable来筛选网络或者是本地数据
//    fun getMoreMessagesDataRepository(nextPageUrl: String): Observable<MessagesData> {
//        return ApiClient
//                .eyepetizerDataService.getMoreMessagesData(nextPageUrl)
//    }
//
//
//    //可以操作Observable来筛选网络或者是本地数据
//    fun getConfigsDataRepository(model: String, udid: String, vc: String,
//                                 vn: String, deviceModel: String, first_channel: String,
//                                 last_channel: String, system_version_code: String): Observable<ConfigsData> {
//        return ApiClient
//                .eyepetizerDataService.getConfigsData(model, udid, vc,
//                vn, deviceModel, first_channel,
//                last_channel, system_version_code)
//    }
//
//
//    //可以操作Observable来筛选网络或者是本地数据
//    fun getVideoRelatedDataRepository(id: String, udid: String, vc: String,
//                                      vn: String, deviceModel: String, first_channel: String,
//                                      last_channel: String, system_version_code: String): Observable<CommonData> {
//        return ApiClient
//                .eyepetizerDataService.getVideoRelatedData(id, udid, vc,
//                vn, deviceModel, first_channel,
//                last_channel, system_version_code)
//    }
//
//    //可以操作Observable来筛选网络或者是本地数据
//    fun getSearchDataRepository(query: String, udid: String, vc: String,
//                                vn: String, deviceModel: String, first_channel: String,
//                                last_channel: String, system_version_code: String): Observable<CommonData> {
//        return ApiClient
//                .eyepetizerDataService.getSearchData(query, udid, vc,
//                vn, deviceModel, first_channel,
//                last_channel, system_version_code)
//    }
//
//    //可以操作Observable来筛选网络或者是本地数据
//    fun getMoreSearchDataRepository(nextPageUrl: String): Observable<CommonData> {
//        return ApiClient
//                .eyepetizerDataService.getMoreSearchData(nextPageUrl)
//    }
//
//    //可以操作Observable来筛选网络或者是本地数据
//    fun getSearchHotsDataRepository(udid: String, vc: String,
//                                    vn: String, deviceModel: String, first_channel: String,
//                                    last_channel: String, system_version_code: String): Observable<List<SearchHotsData>> {
//
//        return ApiClient
//                .eyepetizerDataService.getSearchHotsData(udid, vc,
//                vn, deviceModel, first_channel,
//                last_channel, system_version_code)
//                .map(Function<List<String>, List<SearchHotsData>> { list ->
//                    val vale = ArrayList<SearchHotsData>()
//                    list.forEach { it ->
//                        vale.add(SearchHotsData("nethots", "热搜关键词", it, false))
//                    }
//                    return@Function vale
//                })
//
//    }


}
