package com.msc.someweather.http.bean

class Lifestyle {

    var type: String? = null
    var desc: String? = null
    var image: Int = 0

    constructor(type: String?, desc: String?, image: Int) {
        this.type = type
        this.desc = desc
        this.image = image
    }
}
