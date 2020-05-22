package com.piapps.flashcardpro.features.editor.adapter.cells

import android.content.Context
import android.view.Gravity
import android.widget.FrameLayout
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.kent.layouts.viewgroup.lparams
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.appTheme

/**
 * Created by abduaziz on 2019-10-26 at 19:33.
 */

class LabelUI {

    companion object {
        val tvId = 44
    }

    fun createView(ctx: Context) = with(ctx) {
        frameLayout {
            lparams(wrapContent, dip(32))
            horizontalPadding = dip(4)

            textView {
                id = tvId
                layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent).apply {
                    gravity = Gravity.CENTER_VERTICAL
                }
                setBackgroundResource(R.drawable.tag)
                gravity = Gravity.CENTER
                textSize = 12F
                textColorResource = R.color.white
                horizontalPadding = dip(4)
                verticalPadding = dip(2)
            }
        }
    }

}