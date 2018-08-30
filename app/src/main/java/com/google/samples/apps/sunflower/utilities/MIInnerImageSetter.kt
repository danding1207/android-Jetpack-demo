package com.google.samples.apps.sunflower.utilities

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.tmall.wireless.tangram.util.IInnerImageSetter

class MIInnerImageSetter : IInnerImageSetter {
    override fun <IMAGE : ImageView> doLoadImageUrl(view: IMAGE, url: String?) {
        Glide.with(view.context).load(url).into(view)
    }
}