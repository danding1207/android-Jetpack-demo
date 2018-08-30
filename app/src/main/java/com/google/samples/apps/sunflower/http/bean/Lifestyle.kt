package com.google.samples.apps.sunflower.http.bean

class Lifestyle {


    var HeWeather6: List<HeWeather6Bean>? = null

    class HeWeather6Bean {
        /**
         * basic : {"cid":"CN101010100","location":"北京","parent_city":"北京","admin_area":"北京","cnty":"中国","lat":"39.90498734","lon":"116.40528870","tz":"8.0"}
         * lifestyle : [{"brf":"舒适","txt":"今天夜间不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。","type":"comf"},{"brf":"较舒适","txt":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。","type":"drsg"},{"brf":"少发","txt":"各项气象条件适宜，无明显降温过程，发生感冒机率较低。","type":"flu"},{"brf":"适宜","txt":"天气较好，赶快投身大自然参与户外运动，尽情感受运动的快乐吧。","type":"sport"},{"brf":"适宜","txt":"天气较好，但丝毫不会影响您出行的心情。温度适宜又有微风相伴，适宜旅游。","type":"trav"},{"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。","type":"uv"},{"brf":"较不宜","txt":"较不宜洗车，未来一天无雨，风力较大，如果执意擦洗汽车，要做好蒙上污垢的心理准备。","type":"cw"},{"brf":"较差","txt":"气象条件较不利于空气污染物稀释、扩散和清除，请适当减少室外活动时间。","type":"air"}]
         * status : ok
         * update : {"loc":"2017-10-26 23:09","utc":"2017-10-26 15:09"}
         */

        var basic: BasicBean? = null
        var status: String? = null
        var update: UpdateBean? = null
        var lifestyle: List<LifestyleBean>? = null
        var type: String = "lifestyle"

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

        class LifestyleBean {
            /**
             * brf : 舒适
             * txt : 今天夜间不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。
             * type : comf
             */

            var date: String? = null
            var brf: String? = null
            var txt: String? = null
            var type: String? = null

            override fun toString(): String {
                return "LifestyleBean(brf=$brf, txt=$txt, type=$type)"
            }

        }
    }
}
