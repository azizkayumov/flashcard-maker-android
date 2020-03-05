package com.piapps.flashcardpro.core.platform.component

/**
 * Created by abduaziz on 6/9/18.
 */

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.piapps.flashcardpro.R
import de.hdodenhof.circleimageview.CircleImageView

class CircleProfileView(context: Context) : RelativeLayout(context) {

    val circleImageView: CircleImageView
    val textView: TextView

    companion object {
        var colors = arrayOf(
            R.color.c1, R.color.c2, R.color.c3, R.color.c4, R.color.c5,
            R.color.c6, R.color.c7, R.color.c8, R.color.c9, R.color.c10,
            R.color.c11, R.color.c12, R.color.c13, R.color.c14
        )

        fun color(name: String?): Int {
            if (name != null && !name.isBlank())
                return colors[name[0].toInt() % colors.size]
            else
                return R.color.c1
        }
    }

    init {
        circleImageView = CircleImageView(context)
        var params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        circleImageView.setImageResource(R.color.c1)
        circleImageView.layoutParams = params
        addView(circleImageView)

        textView = TextView(context)
        textView.text = ""
        textView.setTypeface(null, Typeface.BOLD)
        textView.textSize = 18f
        textView.setTextColor(Color.WHITE)
        val textParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        textParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
        addView(textView, textParams)
    }

    fun load(photo: String?, title: String?) {
        circleImageView.setImageResource(color(title))
        textView.text = getAbbr(title)
        if (!photo.isNullOrBlank()) {
            Glide.with(context.applicationContext)
                .load(photo)
                .dontAnimate()
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        circleImageView.setImageResource(color(title))
                        textView.text = getAbbr(title)
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        circleImageView.setImageDrawable(resource)
                        textView.text = ""
                        return true
                    }
                })
                .into(circleImageView)
        }
        invalidate()
        requestLayout()
    }

    fun image(bitmap: Bitmap?) {
        circleImageView.setImageBitmap(bitmap)
        textView.setText("")
        invalidate()
        requestLayout()
    }

    fun image(resource: Int) {
        circleImageView.setImageResource(resource)
        textView.setText("")
        invalidate()
        requestLayout()
    }

    fun abbr(name: String?) {
        circleImageView.setImageResource(color(name))
        textView.text = getAbbr(name)
        invalidate()
        requestLayout()
    }

    fun getAbbr(name: String?): String {
        var result = ""
        var afterSpace = true
        if (name != null && !name.isBlank())
            for (i in 0 until name.length) {
                if (name[i].equals(' '))
                    afterSpace = true
                else {
                    if (afterSpace) {
                        result += name[i]
                        afterSpace = false
                    }
                }
                if (result.length >= 2)
                    break
            }
        else {
            return ""
        }
        return result.toUpperCase()
    }

    fun color(name: String?): Int {
        if (name != null && !name.isBlank())
            return colors[name[0].toInt() % colors.size]
        else
            return R.color.c1
    }

}