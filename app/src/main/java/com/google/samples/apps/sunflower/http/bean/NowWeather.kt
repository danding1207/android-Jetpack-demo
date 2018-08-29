//package com.google.samples.apps.sunflower.http.bean
//
//class NowWeather {
//
//
//    var results: List<ResultsBean>? = null
//
//    class ResultsBean {
//        /**
//         * location : {"id":"C23NB62W20TF","name":"西雅图","country":"US","timezone":"America/Los_Angeles","timezone_offset":"-07:00"}
//         * now : {"text":"多云","code":"4","temperature":"14","feels_like":"14","pressure":"1018","humidity":"76","visibility":"16.09","wind_direction":"西北","wind_direction_degree":"340","wind_speed":"8.05","wind_scale":"2","clouds":"90","dew_point":"-12"}
//         * last_update : 2015-09-25T22:45:00-07:00
//         */
//
//        var location: LocationBean? = null
//        var now: NowBean? = null
//        var last_update: String? = null
//
//        class LocationBean {
//            /**
//             * id : C23NB62W20TF
//             * name : 西雅图
//             * country : US
//             * timezone : America/Los_Angeles
//             * timezone_offset : -07:00
//             */
//
//            var id: String? = null
//            var name: String? = null
//            var country: String? = null
//            var timezone: String? = null
//            var timezone_offset: String? = null
//
//            override fun toString(): String {
//                return "LocationBean(id=$id, name=$name, country=$country, timezone=$timezone, timezone_offset=$timezone_offset)"
//            }
//        }
//
//        class NowBean {
//            /**
//             * text : 多云
//             * code : 4
//             * temperature : 14
//             * feels_like : 14
//             * pressure : 1018
//             * humidity : 76
//             * visibility : 16.09
//             * wind_direction : 西北
//             * wind_direction_degree : 340
//             * wind_speed : 8.05
//             * wind_scale : 2
//             * clouds : 90
//             * dew_point : -12
//             */
//
//            var text: String? = null
//            var code: String? = null
//            var temperature: String? = null
//            var feels_like: String? = null
//            var pressure: String? = null
//            var humidity: String? = null
//            var visibility: String? = null
//            var wind_direction: String? = null
//            var wind_direction_degree: String? = null
//            var wind_speed: String? = null
//            var wind_scale: String? = null
//            var clouds: String? = null
//            var dew_point: String? = null
//
//            override fun toString(): String {
//                return "NowBean(text=$text, code=$code, temperature=$temperature, feels_like=$feels_like, pressure=$pressure, humidity=$humidity, visibility=$visibility, wind_direction=$wind_direction, wind_direction_degree=$wind_direction_degree, wind_speed=$wind_speed, wind_scale=$wind_scale, clouds=$clouds, dew_point=$dew_point)"
//            }
//
//        }
//
//        override fun toString(): String {
//            return "ResultsBean(location=${location.toString()}, now=${now.toString()}, last_update=$last_update)"
//        }
//
//
//    }
//
//    override fun toString(): String {
//        val result =  StringBuilder()
//        if (results!=null) {
//            result.append("size=${results!!.size}  {")
//            results!!.forEach {
//                it-> result.append("[$it],")
//            }
//            result.append("}")
//        }
//        return result.toString()
//    }
//
//
//
//}
