package com.piapps.flashcardpro.features.labels

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import com.kent.layouts.*
import com.kent.layouts.viewgroup.horizontalLayout
import com.kent.layouts.viewgroup.lparams
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.appTheme
import com.piapps.flashcardpro.core.extension.getLocalizedString

/**
 * Created by abduaziz on 2019-09-26 at 12:34.
 */

class LabelEditUI {

    companion object {
        val tvId = 14
        val cbId = 15

    }

    fun createView(ctx: Context) = with(ctx) {
        horizontalLayout {
            lparams(matchParent, dip(48))
            setRippleEffect()

            textView {
                id = tvId
                layoutParams = LinearLayout.LayoutParams(0, dip(48)).apply {
                    weight = 1F
                }
                gravity = Gravity.CENTER_VERTICAL
                horizontalPadding = dip(16)
                text = ctx.getLocalizedString(R.string.important)
                textColorResource = ctx.appTheme().colorPrimaryText
                textSize = 14F
                ellipsize(1)
            }

            checkBox {
                id = cbId
                layoutParams = LinearLayout.LayoutParams(dip(48), dip(48))
                gravity = Gravity.CENTER_VERTICAL
                isClickable = false
            }
        }
    }
}