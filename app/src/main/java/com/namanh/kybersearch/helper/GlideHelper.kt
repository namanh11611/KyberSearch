package com.namanh.kybersearch.helper

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.namanh.kybersearch.R
import com.namanh.kybersearch.utils.GlideApp
import com.namanh.kybersearch.utils.LogUtil

object GlideHelper {
    fun loadImage(context: Context?, view: ImageView?, urlImage: String?) {
        if (context == null || view == null || urlImage == null || urlImage.trim { it <= ' ' }
                .isEmpty()) return
        if (urlImage.endsWith(".svg")) {
            loadSvgImage(context, view, urlImage)
        } else {
            GlideApp.with(context)
                .load(urlImage)
                .error(R.drawable.ic_launcher_foreground)
                .into(view)
        }
    }

    private fun loadSvgImage(context: Context, view: ImageView, urlImage: String) {
//        LogUtil.d("loadSvgImage")
        val requestBuilder: RequestBuilder<PictureDrawable> = GlideApp.with(context)
            .`as`(PictureDrawable::class.java)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .transition(withCrossFade())
            .listener(SvgSoftwareLayerSetter())
        val uri = Uri.parse(urlImage)
        requestBuilder.load(uri).into(view)
    }
}