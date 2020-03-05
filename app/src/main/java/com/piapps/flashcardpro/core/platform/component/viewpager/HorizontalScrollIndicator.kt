package com.piapps.flashcardpro.core.platform.component.viewpager

import android.content.Context
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import com.kent.layouts.dip
import com.piapps.flashcardpro.R

/**
 * Created by abduaziz on 9/24/18.
 */

class HorizontalScrollIndicator(context: Context) : LinearLayout(context) {

    val indicator = AppCompatImageView(context)
    val lp = LayoutParams(0, dip(2)).apply {
        weight = 1F
    }

    init {
        weightSum = 2f
        indicator.layoutParams = lp
        indicator.setImageResource(R.drawable.tab_indicator)
        addView(indicator)
    }
}