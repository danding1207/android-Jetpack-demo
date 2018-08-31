package com.msc.someweather.http.bean

class NowWeather {


    var HeWeather6: List<HeWeather6Bean>? = null

    class HeWeather6Bean {
        /**
         * basic : {"cid":"CN101010100","location":"北京","parent_city":"北京","admin_area":"北京","cnty":"中国","lat":"39.90498734","lon":"116.40528870","tz":"8.0"}
         * now : {"cond_code":"101","cond_txt":"多云","fl":"16","hum":"73","pcpn":"0","pres":"1017","tmp":"14","vis":"1","wind_deg":"11","wind_dir":"北风","wind_sc":"微风","wind_spd":"6"}
         * status : ok
         * update : {"loc":"2017-10-26 17:29","utc":"2017-10-26 09:29"}
         */

        var basic: BasicBean? = null
        var now: NowBean? = null
        var status: String? = null
        var update: UpdateBean? = null

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

        class NowBean {
            /**
             * cond_code : 101
             * cond_txt : 多云
             * fl : 16
             * hum : 73
             * pcpn : 0
             * pres : 1017
             * tmp : 14
             * vis : 1
             * wind_deg : 11
             * wind_dir : 北风
             * wind_sc : 微风
             * wind_spd : 6
             */

            var cond_code: String? = null
            var cond_txt: String? = null
            var fl: String? = null
            var hum: String? = null
            var pcpn: String? = null
            var pres: String? = null
            var tmp: String? = null
            var vis: String? = null
            var wind_deg: String? = null
            var wind_dir: String? = null
            var wind_sc: String? = null
            var wind_spd: String? = null
            val cloud: String? = null

            val type: String = "nowweather"


            override fun toString(): String {
                return "NowBean{" +
                        "\ncond_code='" + cond_code + '\''.toString() +
                        ", \ncond_txt='" + cond_txt + '\''.toString() +
                        ", \nfl='" + fl + '\''.toString() +
                        ", \nhum='" + hum + '\''.toString() +
                        ", \npcpn='" + pcpn + '\''.toString() +
                        ", \npres='" + pres + '\''.toString() +
                        ", \ntmp='" + tmp + '\''.toString() +
                        ", \nvis='" + vis + '\''.toString() +
                        ", \nwind_deg='" + wind_deg + '\''.toString() +
                        ", \nwind_dir='" + wind_dir + '\''.toString() +
                        ", \nwind_sc='" + wind_sc + '\''.toString() +
                        ", \nwind_spd='" + wind_spd + '\''.toString() +
                        '}'.toString()
            }
        }

        class UpdateBean {
            /**
             * loc : 2017-10-26 17:29
             * utc : 2017-10-26 09:29
             */

            var loc: String? = null
            var utc: String? = null
        }
    }
}
