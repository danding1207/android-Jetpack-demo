package com.msc.someweather.http.bean

class AirNow {


    var HeWeather6: List<HeWeather6Bean>? = null

    class HeWeather6Bean {
        /**
         * air_now_city : {"aqi":"19","co":"0","main":"","no2":"34","o3":"31","pm10":"18","pm25":"8","pub_time":"2017-11-07 22:00","qlty":"优","so2":"2"}
         * air_now_station : [{"air_sta":"万寿西宫","aqi":"19","asid":"CNA1001","co":"0.4","lat":"39.8673","lon":"116.366","main":"-","no2":"37","o3":"29","pm10":"13","pm25":"5","pub_time":"2017-11-07 22:00","qlty":"优","so2":"3"},{"air_sta":"定陵","aqi":"21","asid":"CNA1002","co":"0.4","lat":"40.2865","lon":"116.17","main":"-","no2":"7","o3":"66","pm10":"6","pm25":"1","pub_time":"2017-11-07 22:00","qlty":"优","so2":"3"},{"air_sta":"东四","aqi":"16","asid":"CNA1003","co":"0.4","lat":"39.9522","lon":"116.434","main":"-","no2":"28","o3":"36","pm10":"16","pm25":"10","pub_time":"2017-11-07 22:00","qlty":"优","so2":"2"},{"air_sta":"天坛","aqi":"22","asid":"CNA1004","co":"0.4","lat":"39.8745","lon":"116.434","main":"-","no2":"38","o3":"29","pm10":"22","pm25":"8","pub_time":"2017-11-07 22:00","qlty":"优","so2":"1"},{"air_sta":"农展馆","aqi":"28","asid":"CNA1005","co":"0.6","lat":"39.9716","lon":"116.473","main":"-","no2":"56","o3":"15","pm10":"20","pm25":"12","pub_time":"2017-11-07 22:00","qlty":"优","so2":"2"},{"air_sta":"官园","aqi":"25","asid":"CNA1006","co":"0.4","lat":"39.9425","lon":"116.361","main":"-","no2":"50","o3":"13","pm10":"15","pm25":"10","pub_time":"2017-11-07 22:00","qlty":"优","so2":"1"},{"air_sta":"海淀区万柳","aqi":"31","asid":"CNA1007","co":"0.4","lat":"39.9934","lon":"116.315","main":"-","no2":"61","o3":"6","pm10":"28","pm25":"14","pub_time":"2017-11-07 22:00","qlty":"优","so2":"3"},{"air_sta":"顺义新城","aqi":"16","asid":"CNA1008","co":"0.4","lat":"40.1438","lon":"116.72","main":"-","no2":"12","o3":"51","pm10":"16","pm25":"6","pub_time":"2017-11-07 22:00","qlty":"优","so2":"4"},{"air_sta":"怀柔镇","aqi":"15","asid":"CNA1009","co":"0.3","lat":"40.3937","lon":"116.644","main":"-","no2":"21","o3":"48","pm10":"12","pm25":"5","pub_time":"2017-11-07 22:00","qlty":"优","so2":"3"},{"air_sta":"昌平镇","aqi":"15","asid":"CNA1010","co":"0.5","lat":"40.1952","lon":"116.23","main":"-","no2":"20","o3":"48","pm10":"7","pm25":"3","pub_time":"2017-11-07 22:00","qlty":"优","so2":"2"},{"air_sta":"奥体中心","aqi":"24","asid":"CNA1011","co":"0.4","lat":"40.0031","lon":"116.407","main":"-","no2":"48","o3":"21","pm10":"15","pm25":"9","pub_time":"2017-11-07 22:00","qlty":"优","so2":"3"},{"air_sta":"古城","aqi":"32","asid":"CNA1012","co":"0.4","lat":"39.9279","lon":"116.225","main":"-","no2":"36","o3":"20","pm10":"32","pm25":"3","pub_time":"2017-11-07 22:00","qlty":"优","so2":"1"}]
         * basic : {"cid":"CN101010100","location":"北京","parent_city":"北京","admin_area":"北京","cnty":"中国","lat":"39.90498734","lon":"116.40528870","tz":"+8.0"}
         * status : ok
         * update : {"loc":"2017-11-07 22:46","utc":"2017-11-07 14:46"}
         */

        var air_now_city: AirNowCityBean? = null
        var basic: BasicBean? = null
        var status: String? = null
        var type: String = "air"
        var update: UpdateBean? = null
        var air_now_station: List<AirNowStationBean>? = null

        class AirNowCityBean {
            /**
             * aqi : 19
             * co : 0
             * main :
             * no2 : 34
             * o3 : 31
             * pm10 : 18
             * pm25 : 8
             * pub_time : 2017-11-07 22:00
             * qlty : 优
             * so2 : 2
             */

            var aqi: String? = null
            var co: String? = null
            var main: String? = null
            var no2: String? = null
            var o3: String? = null
            var pm10: String? = null
            var pm25: String? = null
            var pub_time: String? = null
            var qlty: String? = null
            var so2: String? = null
        }

        class BasicBean {
            /**
             * cid : CN101010100
             * location : 北京
             * parent_city : 北京
             * admin_area : 北京
             * cnty : 中国
             * lat : 39.90498734
             * lon : 116.40528870
             * tz : +8.0
             */

            var cid: String? = null
            var location: String? = null
            var parent_city: String? = null
            var admin_area: String? = null
            var cnty: String? = null
            var lat: String? = null
            var lon: String? = null
            var tz: String? = null
        }

        class UpdateBean {
            /**
             * loc : 2017-11-07 22:46
             * utc : 2017-11-07 14:46
             */

            var loc: String? = null
            var utc: String? = null
        }

        class AirNowStationBean {
            /**
             * air_sta : 万寿西宫
             * aqi : 19
             * asid : CNA1001
             * co : 0.4
             * lat : 39.8673
             * lon : 116.366
             * main : -
             * no2 : 37
             * o3 : 29
             * pm10 : 13
             * pm25 : 5
             * pub_time : 2017-11-07 22:00
             * qlty : 优
             * so2 : 3
             */

            var air_sta: String? = null
            var aqi: String? = null
            var asid: String? = null
            var co: String? = null
            var lat: String? = null
            var lon: String? = null
            var main: String? = null
            var no2: String? = null
            var o3: String? = null
            var pm10: String? = null
            var pm25: String? = null
            var pub_time: String? = null
            var qlty: String? = null
            var so2: String? = null
        }
    }
}
