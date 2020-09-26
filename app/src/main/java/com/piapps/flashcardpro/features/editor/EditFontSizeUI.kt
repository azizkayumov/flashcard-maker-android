package com.piapps.flashcardpro.features.editor

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.kent.layouts.viewgroup.horizontalLayout
import com.kent.layouts.viewgroup.lparams
import com.kent.layouts.viewgroup.verticalLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.getLocalizedString

/**
 * Created by abduaziz on 9/26/20 at 13:02.
 */

fun EditFontSizeFragment.UI(): View {
    return ctx.frameLayout {
        lparams(matchParent, matchParent)

        verticalLayout {
            layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent).apply {
                gravity = Gravity.BOTTOM
            }
            backgroundColorResource = theme.colorPrimary
            padding = dip(16)

            horizontalLayout {
                layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)

                textView {
                    layoutParams = LinearLayout.LayoutParams(0, wrapContent).apply {
                        weight = 1F
                    }
                    text = ctx.getLocalizedString(R.string.edit_card_text_size)
                    textSize = 14F
                    textColorResource = theme.colorPrimaryText
                }

                tvCurFontSize = textView {
                    layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent)
                    textSize = 14F
                    textColorResource = theme.colorPrimaryText
                }
            }

            seekBarFont = seekBar {
                layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent).apply {
                    topMargin = dip(16)
                    bottomMargin = dip(16)
                }
                max = 56
                progress = 14
            }

            chbAll = checkBox {
                layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
                text = ctx.getLocalizedString(R.string.set_the_font_size_for_all)
                textColorResource = theme.colorPrimaryText
            }
        }
    }
}