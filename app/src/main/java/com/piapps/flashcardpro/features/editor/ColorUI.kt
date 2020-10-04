package com.piapps.flashcardpro.features.editor

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.GridLayoutManager
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorWheelRendererBuilder
import com.flask.colorpicker.slider.AlphaSlider
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.kent.layouts.viewgroup.lparams
import com.kent.layouts.viewgroup.verticalLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.alphaSlider
import com.piapps.flashcardpro.core.extension.colorWheel
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.extension.lightnessSlider

/**
 * Created by abduaziz on 2019-10-06 at 19:51.
 */

fun ColorFragment.UI(): View {
    return ctx.frameLayout {
        lparams(matchParent, matchParent)

        verticalLayout {
            layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent).apply {
                gravity = Gravity.BOTTOM
            }
            backgroundColorResource = theme.colorBackground
            gravity = Gravity.CENTER

            tv = textView {
                layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
                horizontalPadding = dip(16)
                topPadding = dip(16)
                text = ctx.getLocalizedString(R.string.color)
                textSize = 14F
                textColorResource = theme.colorPrimaryText
            }

            colorPicker = colorWheel {
                layoutParams = LinearLayout.LayoutParams(matchParent, dip(256))
                setDensity(8)
                setRenderer(ColorWheelRendererBuilder.getRenderer(ColorPickerView.WHEEL_TYPE.CIRCLE))
            }

            lightness = lightnessSlider {
                layoutParams = LinearLayout.LayoutParams(matchParent, dip(48)).apply {
                    leftMargin = dip(16)
                    rightMargin = dip(16)
                }
            }
        }
    }
}