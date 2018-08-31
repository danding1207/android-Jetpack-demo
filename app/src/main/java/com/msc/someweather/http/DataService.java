package com.msc.someweather.http;

import com.msc.someweather.http.bean.AirNow;
import com.msc.someweather.http.bean.CaiYunWeather;
import com.msc.someweather.http.bean.Forecast;
import com.msc.someweather.http.bean.Lifestyle;
import com.msc.someweather.http.bean.NowWeather;
import com.msc.someweather.http.bean.WeatherLocation;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface DataService {


//    https://search.heweather.com/find?parameters
    @GET
    Observable<WeatherLocation> searchLocation(@Url String url);

//    https://free-api.heweather.com/s6/weather/now?parameters
    @GET
    Observable<NowWeather> getNowWeather(@Url String url);

//    https://free-api.heweather.com/s6/weather/forecast?parameters
    @GET
    Observable<Forecast> getForecastWeather(@Url String url);


    @GET
    Observable<Lifestyle> getLifestyle(@Url String url);


//    https://free-api.heweather.com/s6/air/now?parameters
    @GET
    Observable<AirNow> getAirNow(@Url String url);

//    http://api.caiyunapp.com/v2/Y2FpeXVuIGFuZHJpb2QgYXBp/116.276329,39.933383/weather?lang=zh_CN&span=16&begin=1535558400&hourlysteps=384&alert=true&tzshift=28800&version=4.0.1&device_id=867601030981778

    @GET
    Observable<CaiYunWeather> getCaiYunWeather(@Url String url);



//    http://baobab.kaiyanapp.com/api/v5/index/tab/feed?
// udid=5ab5bd3e87e04215bf7820e58576aa192784ca51&
// vc=341&
// vn=3.19&
// deviceModel=MI%203W&
// first_channel=eyepetizer_xiaomi_market&
// last_channel=eyepetizer_xiaomi_market&
// system_version_code=23

//    @GET("v5/index/tab/feed")
//    Observable<FeedData> getFeedData(@Query("udid") String udid,
//                                     @Query("vc") String vc,
//                                     @Query("vn") String vn,
//                                     @Query("deviceModel") String deviceModel,
//                                     @Query("first_channel") String first_channel,
//                                     @Query("last_channel") String last_channel,
//                                     @Query("system_version_code") String system_version_code);
//
//    @GET
//    Observable<FeedData> getMoreFeedData(@Url String url);
//
//
//    //http://baobab.kaiyanapp.com/api/v5/index/tab/discovery?
//    // udid=5ab5bd3e87e04215bf7820e58576aa192784ca51&
//    // vc=341&
//    // vn=3.19&
//    // deviceModel=MI%203W&
//    // first_channel=eyepetizer_xiaomi_market&
//    // last_channel=eyepetizer_xiaomi_market&
//    // system_version_code=23
//    @GET("v5/index/tab/discovery")
//    Observable<DiscoveryData> getDiscoveryData(@Query("udid") String udid,
//                                               @Query("vc") String vc,
//                                               @Query("vn") String vn,
//                                               @Query("deviceModel") String deviceModel,
//                                               @Query("first_channel") String first_channel,
//                                               @Query("last_channel") String last_channel,
//                                               @Query("system_version_code") String system_version_code);
//
//    @GET
//    Observable<DiscoveryData> getMoreDiscoveryData(@Url String url);
//
//
//    //http://baobab.kaiyanapp.com/api/v3/messages?udid=5ab5bd3e87e04215bf7820e58576aa192784ca51&vc=341&vn=3.19&deviceModel=MI%203W&first_channel=eyepetizer_xiaomi_market&last_channel=eyepetizer_xiaomi_market&system_version_code=23
//    @GET("v3/messages")
//    Observable<MessagesData> getMessagesData(@Query("udid") String udid,
//                                             @Query("vc") String vc,
//                                             @Query("vn") String vn,
//                                             @Query("deviceModel") String deviceModel,
//                                             @Query("first_channel") String first_channel,
//                                             @Query("last_channel") String last_channel,
//                                             @Query("system_version_code") String system_version_code);
//
//    @GET
//    Observable<MessagesData> getMoreMessagesData(@Url String url);
//
////    http://baobab.kaiyanapp.com/api/v6/community/tab/follow?udid=5ab5bd3e87e04215bf7820e58576aa192784ca51&vc=341&vn=3.19&deviceModel=MI%203W&first_channel=eyepetizer_xiaomi_market&last_channel=eyepetizer_xiaomi_market&system_version_code=23
//
//
////    http://baobab.kaiyanapp.com/api/v2/notification/time?udid=5ab5bd3e87e04215bf7820e58576aa192784ca51&vc=341&vn=3.19&deviceModel=MI%203W&first_channel=eyepetizer_xiaomi_market&last_channel=eyepetizer_xiaomi_market&system_version_code=23
//
//    @GET("tabs/follow")
//    Observable<FollowData> getFollowData();
//
//
////    http://baobab.kaiyanapp.com/api/v2/configs?model=Android&udid=5ab5bd3e87e04215bf7820e58576aa192784ca51&vc=352&vn=4.0&deviceModel=MI%203W&first_channel=eyepetizer_xiaomi_market&last_channel=eyepetizer_xiaomi_market&system_version_code=23
//
//    @GET("v2/configs")
//    Observable<ConfigsData> getConfigsData(@Query("model") String model,
//                                           @Query("udid") String udid,
//                                           @Query("vc") String vc,
//                                           @Query("vn") String vn,
//                                           @Query("deviceModel") String deviceModel,
//                                           @Query("first_channel") String first_channel,
//                                           @Query("last_channel") String last_channel,
//                                           @Query("system_version_code") String system_version_code);
//
//
////    http://baobab.kaiyanapp.com/api/v4/video/related?id=13408&udid=5ab5bd3e87e04215bf7820e58576aa192784ca51&vc=352&vn=4.0&deviceModel=MI%203W&first_channel=eyepetizer_xiaomi_market&last_channel=eyepetizer_xiaomi_market&system_version_code=23
//    @GET("v4/video/related")
//    Observable<CommonData> getVideoRelatedData(@Query("id") String id,
//                                               @Query("udid") String udid,
//                                               @Query("vc") String vc,
//                                               @Query("vn") String vn,
//                                               @Query("deviceModel") String deviceModel,
//                                               @Query("first_channel") String first_channel,
//                                               @Query("last_channel") String last_channel,
//                                               @Query("system_version_code") String system_version_code);
//
//
////    http://baobab.kaiyanapp.com/api/v3/search?query=%E8%B0%B7%E9%98%BF%E8%8E%AB&udid=5ab5bd3e87e04215bf7820e58576aa192784ca51&vc=352&vn=4.0&deviceModel=MI%203W&first_channel=eyepetizer_xiaomi_market&last_channel=eyepetizer_xiaomi_market&system_version_code=23
//
//    @GET("v3/search")
//    Observable<CommonData> getSearchData(@Query("query") String query,
//                                         @Query("udid") String udid,
//                                         @Query("vc") String vc,
//                                         @Query("vn") String vn,
//                                         @Query("deviceModel") String deviceModel,
//                                         @Query("first_channel") String first_channel,
//                                         @Query("last_channel") String last_channel,
//                                         @Query("system_version_code") String system_version_code);
//    @GET
//    Observable<CommonData> getMoreSearchData(@Url String url);
//
//
////    http://baobab.kaiyanapp.com/api/v3/queries/hot?udid=5ab5bd3e87e04215bf7820e58576aa192784ca51&vc=352&vn=4.0&deviceModel=MI%203W&first_channel=eyepetizer_xiaomi_market&last_channel=eyepetizer_xiaomi_market&system_version_code=23
//
//    @GET("v3/queries/hot")
//    Observable<List<String>> getSearchHotsData(
//            @Query("udid") String udid,
//            @Query("vc") String vc,
//            @Query("vn") String vn,
//            @Query("deviceModel") String deviceModel,
//            @Query("first_channel") String first_channel,
//            @Query("last_channel") String last_channel,
//            @Query("system_version_code") String system_version_code);


}
