package com.msc.someweather.utilities

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.DrawableCompat

object DrawableUtils {

    fun tintDrawable(drawable: Drawable, colors: ColorStateList): Drawable {
        val wrappedDrawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTintList(wrappedDrawable, colors)
        return wrappedDrawable
    }

    /**
     * 图片旋转
     */
    fun bitmapRotate(resources: Resources, baseDrawable: Drawable, degrees: Int): Drawable {
        val paint = Paint()
        paint.isAntiAlias = true

        val baseBitmap = (baseDrawable as BitmapDrawable).bitmap

        // 创建一个和原图一样大小的图片
        val afterBitmap = Bitmap.createBitmap(baseBitmap.width,
                baseBitmap.height, baseBitmap.config)
        val canvas = Canvas(afterBitmap)
        val matrix = Matrix()
        // 根据原图的中心位置旋转
        matrix.setRotate(degrees.toFloat(), baseBitmap.width / 2f,
                baseBitmap.height / 2f)
        canvas.drawBitmap(baseBitmap, matrix, paint)

        return BitmapDrawable(resources, afterBitmap)
    }


}
