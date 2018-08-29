//package com.google.samples.apps.sunflower.http.bean
//
//class Location {
//
//
//    var results: List<ResultsBean>? = null
//
//    class ResultsBean {
//        /**
//         * id : WX4EQ2XJD7V2
//         * name : 海淀
//         * country : CN
//         * path : 海淀,北京,中国
//         * timezone : Asia/Shanghai
//         * timezone_offset : +08:00
//         */
//
//        var id: String? = null
//        var name: String? = null
//        var country: String? = null
//        var path: String? = null
//        var timezone: String? = null
//        var timezone_offset: String? = null
//
//        override fun toString(): String {
//            return "ResultsBean(id=$id, name=$name, country=$country, path=$path, timezone=$timezone, timezone_offset=$timezone_offset)"
//        }
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
//}
