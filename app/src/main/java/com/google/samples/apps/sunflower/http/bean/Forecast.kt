package com.google.samples.apps.sunflower.http.bean

class Forecast {

    var HeWeather6: List<HeWeather6Bean>? = null

    class HeWeather6Bean {
        /**
         * basic : {"cid":"CN101010100","location":"北京","parent_city":"北京","admin_area":"北京","cnty":"中国","lat":"39.90498734","lon":"116.40528870","tz":"8.0"}
         * daily_forecast : [{"cond_code_d":"103","cond_code_n":"101","cond_txt_d":"晴间多云","cond_txt_n":"多云","date":"2017-10-26","hum":"57","pcpn":"0.0","pop":"0","pres":"1020","tmp_max":"16","tmp_min":"8","uv_index":"3","vis":"16","wind_deg":"0","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"5"},{"cond_code_d":"101","cond_code_n":"501","cond_txt_d":"多云","cond_txt_n":"雾","date":"2017-10-27","hum":"56","pcpn":"0.0","pop":"0","pres":"1018","tmp_max":"18","tmp_min":"9","uv_index":"3","vis":"20","wind_deg":"187","wind_dir":"南风","wind_sc":"微风","wind_spd":"6"},{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2017-10-28","hum":"26","pcpn":"0.0","pop":"0","pres":"1029","tmp_max":"17","tmp_min":"5","uv_index":"2","vis":"20","wind_deg":"2","wind_dir":"北风","wind_sc":"3-4","wind_spd":"19"}]
         * status : ok
         * update : {"loc":"2017-10-26 23:09","utc":"2017-10-26 15:09"}
         */

        var basic: BasicBean? = null
        var status: String? = null
        var update: UpdateBean? = null
        var daily_forecast: List<DailyForecastBean>? = null
        var type: String = "forecast"

        class BasicBean {
            /**
             * cid : CN101010100
             * location : 北京
             * parent_city : 北京
             * admin_area : 北京
             * cnty : 中国
             * lat : 39.90498734
             * lon : 116.40528870
             * tz : 8.0
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
             * loc : 2017-10-26 23:09
             * utc : 2017-10-26 15:09
             */

            var loc: String? = null
            var utc: String? = null
        }

        class DailyForecastBean {
            /**
             * cond_code_d : 103
             * cond_code_n : 101
             * cond_txt_d : 晴间多云
             * cond_txt_n : 多云
             * date : 2017-10-26
             * hum : 57
             * pcpn : 0.0
             * pop : 0
             * pres : 1020
             * tmp_max : 16
             * tmp_min : 8
             * uv_index : 3
             * vis : 16
             * wind_deg : 0
             * wind_dir : 无持续风向
             * wind_sc : 微风
             * wind_spd : 5
             */
            var sr: String? = null
            var ss: String? = null
            var mr: String? = null
            var ms: String? = null
            var cond_code_d: String? = null
            var cond_code_n: String? = null
            var cond_txt_d: String? = null
            var cond_txt_n: String? = null
            var date: String? = null
            var hum: String? = null
            var pcpn: String? = null
            var pop: String? = null
            var pres: String? = null
            var tmp_max: String? = null
            var tmp_min: String? = null
            var uv_index: String? = null
            var vis: String? = null
            var wind_deg: String? = null
            var wind_dir: String? = null
            var wind_sc: String? = null
            var wind_spd: String? = null

            override fun toString(): String {
                return "DailyForecastBean(cond_code_d=$cond_code_d, cond_code_n=$cond_code_n, cond_txt_d=$cond_txt_d, cond_txt_n=$cond_txt_n, date=$date, hum=$hum, pcpn=$pcpn, pop=$pop, pres=$pres, tmp_max=$tmp_max, tmp_min=$tmp_min, uv_index=$uv_index, vis=$vis, wind_deg=$wind_deg, wind_dir=$wind_dir, wind_sc=$wind_sc, wind_spd=$wind_spd)"
            }

        }
    }
}
