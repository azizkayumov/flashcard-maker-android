package com.piapps.flashcardpro.core.platform.component.bottom

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.kent.layouts.*
import com.kent.layouts.viewgroup.horizontalLayout
import com.kent.layouts.viewgroup.lparams
import com.piapps.flashcardpro.core.extension.appTheme

class BottomMenuItemUI {
    companion object {
        var ivId = 32
        var tvId = 980
    }

    fun createView(ctx: Context): View {
        return ctx.horizontalLayout {
            lparams(matchParent, wrapContent)
            setRippleEffect()
            imageView {
                id = ivId
                layoutParams = LinearLayout.LayoutParams(dip(56), dip(56)).apply {
                    topPadding = dip(16)
                    leftPadding = dip(16)
                    bottomPadding = dip(16)
                }
                setIconColor(ctx, ctx.appTheme().colorIconActive)
            }
            textView {
                layoutParams = LinearLayout.LayoutParams(matchParent, dip(56)).apply {
                    gravity = Gravity.CENTER_VERTICAL
                    leftPadding = dip(16)
                }
                gravity = Gravity.CENTER_VERTICAL
                textColorResource = ctx.appTheme().colorPrimaryText
                textSize = 16f
                id = tvId
            }
        }
    }
}