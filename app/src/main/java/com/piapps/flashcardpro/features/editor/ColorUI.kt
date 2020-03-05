package com.piapps.flashcardpro.features.editor

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.kent.layouts.viewgroup.lparams
import com.kent.layouts.viewgroup.verticalLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.getLocalizedString

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
            backgroundColorResource = theme.colorPrimary

            tv = textView {
                layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
                horizontalPadding = dip(16)
                topPadding = dip(16)
                text = ctx.getLocalizedString(R.string.color)
                textSize = 14F
                textColorResource = theme.colorPrimaryText
            }

            rv = recyclerView {
                layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
                padding = dip(8)
                layoutManager = GridLayoutManager(ctx, 5)
                itemAnimator = null
                clipToPadding = false
                adapter = colorAdapter
            }
        }
    }
}