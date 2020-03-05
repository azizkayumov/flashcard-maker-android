package com.piapps.flashcardpro.features.files.controller

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.kent.layouts.*
import com.kent.layouts.viewgroup.horizontalLayout
import com.kent.layouts.viewgroup.lparams
import com.kent.layouts.viewgroup.verticalLayout
import com.piapps.flashcardpro.core.extension.appTheme

class FilesItemUI {
    companion object {
        var ivIconId = 1
        var tvNameId = 2
        var tvDescriptionId = 3
    }

    fun createView(ctx: Context): View = with(ctx) {
        horizontalLayout {
            lparams(matchParent, wrapContent).apply {
                rightPadding = dip(12)
            }
            setBackgroundResource(ctx.appTheme().colorBackground)
            setRippleEffect()

            imageView {
                layoutParams = LinearLayout.LayoutParams(dip(56), dip(56)).apply {
                    padding = dip(12)
                }
                id = ivIconId
                //setIconColor(ctx, ctx.appTheme().colorAccent)
            }

            verticalLayout {
                layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                    gravity = Gravity.CENTER_VERTICAL
                }
                textView {
                    layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent)
                    textColorResource = ctx.appTheme().colorPrimaryText
                    id = tvNameId
                    maxLines = 1
                    ellipsize = TextUtils.TruncateAt.END
                }
                textView {
                    layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent)
                    textColorResource = ctx.appTheme().colorSecondaryText
                    id = tvDescriptionId
                    maxLines = 1
                    ellipsize = TextUtils.TruncateAt.END
                }
            }
        }
    }
}