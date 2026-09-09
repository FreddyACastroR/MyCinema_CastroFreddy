package com.example.mycinema

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.widget.ImageView
import androidx.annotation.DrawableRes

fun ImageView.setBlurredImage(@DrawableRes resId: Int, radius: Float = 40f) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        setImageResource(resId)
        setRenderEffect(RenderEffect.createBlurEffect(radius, radius, Shader.TileMode.CLAMP))
        return
    }

    val options = BitmapFactory.Options().apply { inSampleSize = 8 }
    val small = BitmapFactory.decodeResource(resources, resId, options) ?: run {
        setImageResource(resId)
        return
    }
    val tiny = Bitmap.createScaledBitmap(small, maxOf(1, small.width / 4), maxOf(1, small.height / 4), true)
    if (tiny !== small) small.recycle()
    setImageBitmap(tiny)
}
