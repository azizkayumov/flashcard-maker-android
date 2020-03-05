package com.piapps.flashcardpro.features.dialog

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

fun DialogFragment.UI(): View {
    return ctx.frameLayout {
        lparams(matchParent, matchParent)

        cardView {
            layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent).apply {
                gravity = Gravity.CENTER
                leftMargin = dip(56)
                rightMargin = dip(56)
            }
            radius = dip(8).toFloat()
            setCardBackgroundColor(ContextCompat.getColor(ctx, theme.white))

            verticalLayout {
                layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent)

                iv = imageView {
                    layoutParams = LinearLayout.LayoutParams(dip(56), dip(56)).apply {
                        gravity = Gravity.CENTER_HORIZONTAL
                        margin = dip(16)
                    }
                    setBackgroundResource(R.drawable.circle_gray)
                    padding = dip(16)
                    setImageResource(R.drawable.ic_check)
                    setIconColor(ctx, theme.colorAccent)
                }

                tvMessage = textView {
                    layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent).apply {
                        leftMargin = dip(16)
                        rightMargin = dip(16)
                        bottomMargin = dip(8)
                    }
                    gravity = Gravity.CENTER_HORIZONTAL
                    textSize = 14F
                    textColorResource = theme.colorPrimaryText
                    text = ctx.getLocalizedString(R.string.set_name_example)
                }

                tvOk = textView {
                    layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                        gravity = Gravity.END
                    }
                    setRippleEffect()
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
