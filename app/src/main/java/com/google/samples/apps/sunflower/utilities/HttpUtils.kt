package com.google.samples.apps.sunflower.utilities

import java.util.HashMap

object HttpUtils {

    fun getUrl(url: String, params: HashMap<String, String>?): String {
        var url = url
        // 添加url参数
        if (params != null) {
            val it = params.keys.iterator()
            var sb: StringBuffer? = null
            while (it.hasNext()) {
                val key = it.next()
                val value = params[key]
                if (sb == null) {
                    sb = StringBuffer()
                    sb.append("?")
                } else {
                    sb.append("&")
                }
                sb.append(key)
                sb.append("=")
                sb.append(value)
            }
            url += sb!!.toString()
        }
        return url
    }

}
