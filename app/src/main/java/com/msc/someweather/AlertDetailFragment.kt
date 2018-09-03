/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.msc.someweather

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.msc.someweather.http.bean.CaiYunWeather.ResultBean.AlertBean.AlertContentBean
import kotlinx.android.synthetic.main.fragment_alert_detail.*

class AlertDetailFragment : Fragment() {

    private lateinit var alertContent: AlertContentBean

    companion object {
        fun newInstance(param: AlertContentBean): AlertDetailFragment {
            val fragment = AlertDetailFragment()
            val args = Bundle()
            args.putString("AlertContentBean", Gson().toJson(param))
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        if (arguments != null) {
            val alertContentString = arguments!!.getString("AlertContentBean")
            alertContent = Gson().fromJson(alertContentString, AlertContentBean::class.java)
        }

        return inflater.inflate(R.layout.fragment_alert_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var title = ""

        if (alertContent.title != null &&
                alertContent.title!!.contains("台风")) {
            title = "台风"
        } else if (alertContent.title != null &&
                alertContent.title!!.contains("暴雨")) {
            title = "暴雨"
        } else if (alertContent.title != null &&
                alertContent.title!!.contains("暴雪")) {
            title = "暴雪"
        } else if (alertContent.title != null &&
                alertContent.title!!.contains("寒潮")) {
            title = "寒潮"
        } else if (alertContent.title != null &&
                alertContent.title!!.contains("大风")) {
            title = "大风"
        } else if (alertContent.title != null &&
                alertContent.title!!.contains("高温")) {
            title = "高温"
        } else if (alertContent.title != null &&
                alertContent.title!!.contains("雷电")) {
            title = "雷电"
        } else if (alertContent.title != null &&
                alertContent.title!!.contains("霜冻")) {
            title = "霜冻"
        } else if (alertContent.title != null &&
                alertContent.title!!.contains("大雾")) {
            title = "大雾"
        } else if (alertContent.title != null &&
                alertContent.title!!.contains("道路结冰")) {
            title = "道路结冰"
        } else if (alertContent.title != null &&
                alertContent.title!!.contains("冰雹")) {
            title = "冰雹"
        } else if (alertContent.title != null &&
                alertContent.title!!.contains("干旱")) {
            title = "干旱"
        } else if (alertContent.title != null &&
                alertContent.title!!.contains("霾")) {
            title = "霾"
        }


        var levelColor = Color.WHITE
        var levelText = ""

        if (alertContent.title != null &&
                alertContent.title!!.contains("蓝色")) {
            levelColor = ContextCompat.getColor(activity!!, R.color.blue)
            levelText = "蓝色"
        } else if (alertContent.title != null &&
                alertContent.title!!.contains("黄色")) {
            levelColor = ContextCompat.getColor(activity!!, R.color.yellow)
            levelText = "黄色"
        } else if (alertContent.title != null &&
                alertContent.title!!.contains("橙色")) {
            levelColor = ContextCompat.getColor(activity!!, R.color.orange)
            levelText = "橙色"
        } else if (alertContent.title != null &&
                alertContent.title!!.contains("红色")) {
            levelColor = ContextCompat.getColor(activity!!, R.color.red)
            levelText = "红色"
        }

        floatingActionButton.backgroundTintList = ColorStateList.valueOf(levelColor)

        tv_title.setTextColor(levelColor)
        tv_title.text = "$title${levelText}预警"

        tv_description.text = alertContent.description

    }

}
