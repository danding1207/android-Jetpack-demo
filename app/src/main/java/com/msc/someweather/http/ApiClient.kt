package com.msc.someweather.http

import com.msc.someweather.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by dxx on 2017/11/8.
 */

object ApiClient {

    /**
     * 获取指定数据类型
     * @return
     */
    val dataService: DataService
        get() {
            return initService(ApiConstants.baseUrl,
                    DataService::class.java)
        }

    /**单例retrofit */
    private var retrofitInstance: Retrofit? = null

    /**单例OkHttpClient */
    private var okHttpClientInstance: OkHttpClient? = null

    /**
     * 获得想要的 retrofit service
     * @param baseUrl  数据请求url
     * @param clazz    想要的 retrofit service 接口，Retrofit会代理生成对应的实体类
     * @param <T>      api service
     * @return
    </T> */
    private fun <T> initService(baseUrl: String, clazz: Class<T>): T {
        return getRetrofitInstance(baseUrl).create(clazz)
    }

    private fun getRetrofitInstance(baseUrl: String): Retrofit {
        if (retrofitInstance == null) {
            synchronized(ApiClient::class.java) {
                if (retrofitInstance == null) {
                    retrofitInstance = Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(getOkHttpClientInstance())
                            .build()
                }
            }
        }
        return retrofitInstance!!
    }

    private fun getOkHttpClientInstance(): OkHttpClient {
        if (okHttpClientInstance == null) {
            synchronized(ApiClient::class.java) {
                if (okHttpClientInstance == null) {
                    val builder = OkHttpClient().newBuilder()
                    if (BuildConfig.DEBUG) {
                        val httpLoggingInterceptor = HttpLoggingInterceptor()
                        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                        builder.addInterceptor(httpLoggingInterceptor)
                    }
                    okHttpClientInstance = builder.build()
                }
            }
        }
        return okHttpClientInstance!!
    }

}
