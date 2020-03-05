package com.piapps.flashcardpro.features.labels

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.kent.layouts.viewgroup.lparams
import com.kent.layouts.viewgroup.verticalLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.getLocalizedString

/**
 * Created by abduaziz on 2019-10-03 at 21:39.
 */

fun AddLabelFragment.UI(): View {
    return ctx.frameLayout {
        lparams(matchParent, matchParent)

        cardView {
            layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent).apply {
                topMargin = dip(100)
                leftMargin = dip(56)
                rightMargin = dip(56)
            }
            radius = dip(8).toFloat()
            setCardBackgroundColor(ContextCompat.getColor(ctx, theme.white))

            verticalLayout {
                layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent)

                textView {
                    setStyle(ctx, android.R.style.TextAppearance_DeviceDefault_Widget_ActionBar_Title)
                    layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                        margin = dip(16)
                    }
                    gravity = Gravity.CENTER_VERTICAL
                    text = ctx.getLocalizedString(R.string.label_name)
                    textColorResource = theme.colorPrimaryText
                }

                textView {
                    layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                        leftMargin = dip(16)
                        rightMargin = dip(16)
                    }
                    textSize = 12F
                    textColorResource = theme.colorSecondaryText
                    text = ctx.getLocalizedString(R.string.label_name_example)
                }

                et = editText {
                    layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent).apply {
                        leftMargin = dip(16)
                        rightMargin = dip(16)
                    }
                    textColorResource = theme.colorPrimaryText
                    maxLines = 1
                }

                tvOk = textView {
                    layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                        gravity = Gravity.END
                    }
                    setRippleEffectBorderless()
                    verticalPadding = dip(16)
                    horizontalPadding = dip(32)
                    textSize = 14F
                    textColorResource = theme.colorAccent
                    makeBold()
                    text = ctx.getLocalizedString(R.string.ok)
                }
            }
        }
    }
}