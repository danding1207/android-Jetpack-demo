package com.google.samples.apps.sunflower.utilities

import java.util.HashMap

object HttpUtils {

    fun getUrl(url: String, params: HashMap<String, String>?): String {
        val sb = StringBuffer(url)
        sb.append("?")
        // 添加url参数
        if (params != null) {
            val it = params.keys.iterator()
            while (it.hasNext()) {
                val key = it.next()
                val value = params[key]
                sb.append("&")
                sb.append(key)
                sb.append("=")
                sb.append(value)
            }
        }
        return sb.toString()
    }

}
